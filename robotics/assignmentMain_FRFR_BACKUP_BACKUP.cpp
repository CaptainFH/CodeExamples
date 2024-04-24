#include <ros/ros.h>
#include <geometry_msgs/Twist.h>
#include <stdlib.h>
#include <opencv_apps/MomentArrayStamped.h>
#include <ctime>
#include <image_transport/image_transport.h>
#include <sensor_msgs/LaserScan.h>
#include <sensor_msgs/PointCloud.h>
#include <actionlib/client/simple_action_client.h>
#include <control_msgs/FollowJointTrajectoryAction.h>
#include <string>
#include <ros/ros.h>
#include <moveit/move_group_interface/move_group_interface.h>
#include <moveit/planning_scene_interface/planning_scene_interface.h>
#include <moveit_msgs/MoveItErrorCodes.h>
#include <geometry_msgs/Pose.h>
#include <geometry_msgs/Point.h>
#include <geometry_msgs/Quaternion.h>
#include <tf2_ros/transform_listener.h>
#include <geometry_msgs/TransformStamped.h>
#include <laser_geometry/laser_geometry.h>
#include <geometry_msgs/Transform.h>
#include <cmath>
#include<dynamic_reconfigure/Reconfigure.h>
#include<dynamic_reconfigure/Config.h>
#include<dynamic_reconfigure/IntParameter.h>

	
//Calculating pathway
double totalLeft;
double totalRight;
double totalMiddle;
//Averages 
double averageLeft;
double averageRight;
double averageMiddle;
//Joint Controller variables
bool highCubeLowCube; //highCube is set to true while low is set to false.
//Boolean to initialize the controllers on only once
bool isHeadControllerOn;
bool isTorsoControllerOn;
bool isArmControllerOn; 
/////////////////////////////////
//Head
typedef actionlib::SimpleActionClient<control_msgs::FollowJointTrajectoryAction> head_control_client;
float increassingHeadMovement = 0.0;
int maxArea = 1750;
bool stopApproaching = false;
//Torse
typedef actionlib::SimpleActionClient<control_msgs::FollowJointTrajectoryAction> torso_control_client;
float increassingTorsoMovement = 0.0;
//Arm

//Cube
geometry_msgs::TransformStamped cubeVals;
//CylinderPosition
geometry_msgs::Transform cylinderEdge;
geometry_msgs::Transform cylinderMiddle;
int closestPointID;
int biggestGapID;
sensor_msgs::PointCloud pointClouseMessages;
//Colour found
float objectFound_x;
float objectFound_y;
float objectArea;
//Laser scan values
sensor_msgs::LaserScan val;
//RGB Sensor
ros::ServiceClient rgb_service;
int rgbVals[][6] = {{255,90,45,0,45,0},{30,0,255,90,30,0}};
std::string rgbNames[6] ={"r_limit_max","r_limit_min","g_limit_max","g_limit_min","b_limit_max","b_limit_min"}; 
bool runItOnce = false;

//State
static int scenarios = 1;
//Miscs
bool leftAndRightLimitors;
double movementAhead;	
void detect(const sensor_msgs:: LaserScan &val1){
	val = val1;
	totalLeft = 0;
	totalMiddle = 0;
	totalRight = 0;
	//original k was 305- 285
	int j = 285;
	//Original k was 600 - 620.
	int k = 540;
	int index = 0;
	int indexj = 0;
	int indexk = 0;
	//ORIGINAL VALS- 60 - 90
	for(int i = 50; i < 120; i++){
		if(!(std::isinf(val.ranges[i]))){
			totalLeft+= val.ranges[i];
			index++;
		}
		//Original val was 10
		 if((!(std::isinf(val.ranges[j]))) && indexj < 50 ){
		 	totalMiddle+= val.ranges[j];
		 	indexj++;
		 }
		 if(!(std::isinf(val.ranges[k]))){
		 	totalRight+= val.ranges[k];
		 	indexk++;
		 }
		 j++;
		 k--;
	}
	
	averageLeft = totalLeft / index;
	averageMiddle = totalMiddle / indexj;
	averageRight = totalRight / indexk;

}

void colourdetector(const opencv_apps::MomentArrayStamped &msg){
	if((!(msg.moments.size() == 0)) && (scenarios == 1 || scenarios == 7)){
		scenarios = 2;
	}
	if((!(msg.moments.size() == 0))){
		objectFound_x = msg.moments[0].center.x; 
		objectFound_y = msg.moments[0].center.y;
		objectArea = msg.moments[0].area ;

	}else {
		objectFound_x = -1; 
		objectFound_y = -1;
		objectArea = -1;
	}
	/* else if (scenarios == 2 && msg.moments.size() == 0 ){
		std::cout << "Lost the cube" << std::endl;
		scenarios = 6;
	}*/
}
float turnToObject(){
	if(objectFound_x < 330){
		return 0.1;
	}else if (objectFound_x > 310){
		return -0.1;
	}else{
		return 0;
	}
}
float fastTurnToObject(){
	if(objectFound_x < 330){
		return 0.4;
	} else if (objectFound_x > 310){
		return -0.4;
	} else{
		return 0;
	}
}

void headToCube(head_control_client *head_client){
	//ROS_INFO("Waiting for head controller...");
	//head_control_client head_client("/head_controller/follow_joint_trajectory", true);
	//head_client.waitForServer(); //will wait for infisnite time
	if (250 < objectFound_y){
	control_msgs::FollowJointTrajectoryGoal head_goal;
	head_goal.trajectory.joint_names.push_back("head_tilt_joint");
	head_goal.trajectory.joint_names.push_back("head_pan_joint");
	head_goal.trajectory.points.resize(1);
	head_goal.trajectory.points[0].positions.resize(2);
	//isHeadControllerOn = true;
	head_goal.trajectory.points[0].time_from_start = ros::Duration(1.0);
	head_goal.trajectory.points[0].positions[0] = (increassingHeadMovement+= 0.025);
	head_goal.trajectory.points[0].positions[1] = (0);
		
	std::cout << head_goal.trajectory.points[0].positions[0] << std::endl;
	//head_goal.trajectory.header.stamp = ros::Time::now() + ros::Duration(0.01);
	head_client -> sendGoal(head_goal);
	head_client -> waitForResult(ros::Duration(1.0));
	}
}

void torsoToCube(torso_control_client *torso_client){
  	if (350 > objectFound_y){
  	control_msgs::FollowJointTrajectoryGoal height_goal;
  	height_goal.trajectory.joint_names.push_back("torso_lift_joint");
  	height_goal.trajectory.points.resize(1);
  	height_goal.trajectory.points[0].positions.resize(1);
  	height_goal.trajectory.points[0].velocities.resize(1);
  	height_goal.trajectory.points[0].velocities[0] = 0.0;
	height_goal.trajectory.points[0].positions[0] = (increassingTorsoMovement+= 0.1);
  	torso_client -> sendGoal(height_goal);
  	torso_client -> waitForResult(ros::Duration(1.0));
  	}

}
void updateTransform(){
	tf2_ros::Buffer tfBuffer;
	tf2_ros::TransformListener tfListener(tfBuffer);
	try{
	//target_object should be the frame name defined from your broadcast
		cubeVals = tfBuffer.lookupTransform("base_link",
		"target_object",
		ros::Time(0));
		//0.235
	}
	catch (tf2::TransformException &ex) {
		ROS_WARN("%s",ex.what());
		ros::Duration(1.0).sleep();
	}
}
void calculatingCylinders(){
	/*
		float closestPoint = 100.0;
		float biggestGap = 0;
		//finding the center
		for(int i = 1; i < 660; i++){
			if ((std::abs(val.ranges[i-1] - val.ranges[i])) > biggestGap){
				if (val.ranges[i-1] < val.ranges[i]){
				biggestGapID =  (i-1);
				}else {
					biggestGapID = i;
				}
				biggestGap = (std::abs(val.ranges[i-1] - val.ranges[i]));
			}
			if(closestPoint > val.ranges[i]){
				val.ranges[i] = closestPoint;
				closestPointID = i;
			}				
		}
		////////////////////////////////
		//Translate messages to point clouse
		laser_geometry::LaserProjection converter;
		converter.projectLaser(val, pointClouseMessages);
		//Send this points to the tree
		cylinderMiddle.translation.x = pointClouseMessages.points[closestPointID].x;
		cylinderMiddle.translation.y = pointClouseMessages.points[closestPointID].y;
		cylinderMiddle.translation.z = pointClouseMessages.points[closestPointID].z;
		cylinderEdge.translation.x = pointClouseMessages.points[biggestGapID].x;
		cylinderEdge.translation.y = pointClouseMessages.points[biggestGapID].y;
		cylinderEdge.translation.z = pointClouseMessages.points[biggestGapID].z;
		tf2::Quaternion q;
		q.setRPY(0, 0, 0);
		cylinderMiddle.rotation.x = q.x();
		cylinderMiddle.rotation.y = q.y();
		cylinderMiddle.rotation.z = q.z();
		cylinderMiddle.rotation.w = q.w();
		cylinderEdge.rotation.x = q.x();
		cylinderEdge.rotation.y = q.y();
		cylinderEdge.rotation.z = q.z();
		cylinderEdge.rotation.w = q.w();
	*/
}
void createCylinder(){
		

	moveit::planning_interface::PlanningSceneInterface planning_scene_interface;
	moveit::planning_interface::MoveGroupInterface move_group("arm");
	std::vector<moveit_msgs::CollisionObject> cylinder_Object;
	updateTransform();
	//calculatingCylinders();
	
	cylinder_Object[0].primitives[0].type = cylinder_Object[0].primitives[0].CYLINDER;
	cylinder_Object[0].id = "cylinder";
	cylinder_Object[0].header.frame_id = "laser_link";
	cylinder_Object[0].primitives[0].dimensions.resize(2);
	cylinder_Object[0].primitives[0].dimensions[0] = cubeVals.transform.translation.z - (cubeVals.transform.translation.z/2);
	cylinder_Object[0].primitives[0].dimensions[1] = std::abs(cylinderEdge.translation.y - cylinderMiddle.translation.y); //CheckIf this is Y
	
	cylinder_Object[0].primitive_poses.resize(1);
	cylinder_Object[0].primitive_poses[0].position.x = cylinderMiddle.translation.x;
	cylinder_Object[0].primitive_poses[0].position.y = cylinderMiddle.translation.y;
	cylinder_Object[0].primitive_poses[0].position.z = cylinder_Object[0].primitives[0].dimensions[0]/2; 
	
	cylinder_Object[0].operation = cylinder_Object[0].ADD;
	
	planning_scene_interface.applyCollisionObjects(cylinder_Object);
	
}
//0 is red - 1 is green
void changeColourSensor(int neededColour){
	dynamic_reconfigure::ReconfigureRequest srv_req;
	dynamic_reconfigure::IntParameter int_param;
		for(int i = 0; i < 6; i++){
		int_param.name = rgbNames[i];
		int_param.value = rgbVals[neededColour][i];
		srv_req.config.ints.push_back(int_param);
	}
	dynamic_reconfigure::ReconfigureResponse srv_resp;
	rgb_service.call(srv_req, srv_resp);
	ROS_INFO_STREAM("Response received" << srv_resp);
}
void turnAroundAndReset(int neededColour,head_control_client *head_client ,torso_control_client *torso_client){
//Head Reset
	control_msgs::FollowJointTrajectoryGoal head_goal;
	head_goal.trajectory.joint_names.push_back("head_tilt_joint");
	head_goal.trajectory.joint_names.push_back("head_pan_joint");
	ROS_INFO("...connected");
	head_goal.trajectory.points.resize(1);
	head_goal.trajectory.points[0].positions.resize(2);
	isHeadControllerOn = true;
	head_goal.trajectory.points[0].time_from_start = ros::Duration(1.0);
	head_goal.trajectory.points[0].positions[0] = (0);
	head_client -> sendGoal(head_goal);
	
//Torso Reset
  	control_msgs::FollowJointTrajectoryGoal height_goal;
  	height_goal.trajectory.joint_names.push_back("torso_lift_joint");
  	height_goal.trajectory.points.resize(1);
  	height_goal.trajectory.points[0].positions.resize(1);
  	height_goal.trajectory.points[0].velocities.resize(1);
  	height_goal.trajectory.points[0].velocities[0] = 0.0;
	height_goal.trajectory.points[0].positions[0] = 0.0;
  	torso_client -> sendGoal(height_goal);
//Colour Reset
	changeColourSensor(neededColour);
	
	
}	

int main(int argc, char **argv){
	ros::init(argc, argv, "Driving_towards_greenner");
	ros::NodeHandle node;
	ros:: AsyncSpinner spinner(1);
	spinner.start();
	scenarios = 1;
	//Setting up publishers and subscribers
	ros::Publisher velPub = node.advertise<geometry_msgs::Twist>("/cmd_vel", 30);
	ros::Subscriber proximityScan =  node.subscribe("/base_scan", 1000, &detect);
	ros::Subscriber colourFounder = node.subscribe("/contour_moments/moments", 1000, &colourdetector);
	image_transport::ImageTransport it(node);
	//Setting up the services
	rgb_service = node.serviceClient<dynamic_reconfigure::Reconfigure>("/rgb_color_filter/set_parameters");
	rgb_service.waitForExistence();
	//Setting Up the controllers
	//Head
	ROS_INFO("Waiting for head controller...");
	head_control_client head_client("/head_controller/follow_joint_trajectory", true);
	head_client.waitForServer(); //will wait for infisnite time
	//Torso
	ROS_INFO("Waiting for torso controller...");
	torso_control_client torso_client("torso_controller/follow_joint_trajectory", true);
  	torso_client.waitForServer();	
  	ROS_INFO("...connected");
	
	//
	//image_transport::Subscriber depth_sub =
	//it.subscribe("/head_camera/depth_registered/image_raw", 1, imageCb); 
	changeColourSensor(0);
	geometry_msgs::Twist Mmsg;
	ros:: Rate rate(60);
	while(ros::ok()){
	ros::spinOnce();
		switch(scenarios){
			case 1:
			//Moving accross the path
			Mmsg.linear.x =  averageMiddle* 0.8;
			Mmsg.angular.z = (averageLeft - averageRight)* 1.5;
			velPub.publish(Mmsg);
			break;
			case 2:
			//Moving to the cube
			Mmsg.linear.x =  averageMiddle* 0.2;
			Mmsg.angular.z = turnToObject();
			velPub.publish(Mmsg);
				if(/*objectArea > 570 &&*/ objectFound_y > 350){
					highCubeLowCube = false;
					scenarios = 3;
				}else if (objectArea > 2000){
					highCubeLowCube = true;
					scenarios = 3;
			}
			break;
			case 3:
			//ALligning to the cube
			if(!highCubeLowCube){
			//Alligning to the low cube
				if ( 310 <= objectFound_x && objectFound_x <= 330){
					if (!(objectArea > maxArea)){
					Mmsg.linear.x = averageMiddle*0.2;	  
					} else{
					Mmsg.linear.x = 0;
					}
					Mmsg.angular.z = turnToObject();
					velPub.publish(Mmsg);
					if(230 <= objectFound_y && objectFound_y <= 250 &&  objectArea > maxArea){
						std::cout << "On scenario 4 with x: " << objectFound_x << " y: "<< objectFound_y << " with an area value of " << objectArea << std::endl;
						scenarios = 4;
						break;
					}
				headToCube(&head_client);
				}else{
					Mmsg.linear.x = 0;
					Mmsg.angular.z = turnToObject();
					velPub.publish(Mmsg);
				}
			} else{
			//alligning to the highCube
				if (!(objectArea > maxArea)){
					Mmsg.linear.x = averageMiddle*0.2;	  
				} else{
					Mmsg.linear.x = 0;
				}
				Mmsg.angular.z = fastTurnToObject();
				velPub.publish(Mmsg);
				torsoToCube(&torso_client);
				if(340 <= objectFound_y && objectFound_y <= 360 &&  objectArea > maxArea){
					highCubeLowCube = false;
				}
			}
			break;
			case 4:
			//scenarios = 5;
			//pick up the cube
			//Create the the objects
			
			//Arm positions
			break;
			case 5:
			
			if (!runItOnce){
			turnAroundAndReset(1, &head_client, &torso_client);
			runItOnce = true;
			}
			if (objectArea == -1){
				Mmsg.linear.x = 0;
				Mmsg.angular.z = 0.8;
			}else{
				if (!(objectArea > 10000)){
					Mmsg.linear.x = averageMiddle*0.2;	  
				} else{
					Mmsg.linear.x = 0;
				}
					Mmsg.angular.z = turnToObject();
					if(230 <= objectFound_x && objectFound_x <= 250){
						std::cout << "Scenario 6 reached" << std::endl;
						turnAroundAndReset(0, &head_client, &torso_client);
						runItOnce = false;
						scenarios = 6;
					}
			}
			velPub.publish(Mmsg);
			//Drive towards the bin	
			break;
			case 6:
			scenarios = 7;
			//drop The Cube
			break;
			case 7:
			//Look for a new cube	
			Mmsg.linear.x = 0.0;
			Mmsg.angular.z = 0.5;
			
			velPub.publish(Mmsg);
			break;
			case 8:
			//Stop, its over??
			break;
			default:
			std::cout << "code broke somewhere" << std::endl;
		}
		rate.sleep();
	}
	
	return 0;
}
