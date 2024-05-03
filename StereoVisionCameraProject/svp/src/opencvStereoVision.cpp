#include<opencv4/opencv2/opencv.hpp>
#include<opencv2/calib3d/calib3d.hpp>
#include<ros/ros.h>
#include"imageReceiver.h"
#include<opencv2/core/hal/interface.h>
#include <geometry_msgs/Point.h>
#include <sensor_msgs/PointCloud.h>
#include <sensor_msgs/PointCloud2.h>
#include <geometry_msgs/Point32.h>
#include <pcl/io/pcd_io.h>
#include <pcl/point_types.h>
#include<math.h>

//# define M_PI           3.14159265358979323846  /* pi */
//#include <opencv4/opencv2/cudastereo.hpp>
//#include <opencv2/ximgproc/edge_drawing.hpp>

//#include<gazebo/msgs/model.pb.h>
//Code based on a gazebo simulation
imageReceiver::imageReceiver receiver;
const std::string camera1Topic = "/box/camera1/image_raw";
const std::string camera1Pose = "";
const std::string camera2Topic ="/box2/camera2/image_raw";
const std::string camera2Pose ="";
ros::Publisher pointCloudPub;
cv::Mat image1;
cv::Mat image2;
float fov = 59.988681;
//float fov = 277.19135641132203;
float camerasRelativeDistance = 1.0;
float leftCameraPositionX = -0.5;
float rightCameraPositionX = -0.5;
float leftCameraPositionY = 2;
float rightCameraPositionY = 2;

void assignImage1(const sensor_msgs::ImageConstPtr& input){

image1 = receiver.convertToMat(input);
}
void assignImage2(const sensor_msgs::ImageConstPtr& input){  

image2 = receiver.convertToMat(input);
}
void getTransformationAndRotation(){

}

//Remind me to adjust what get's matched in the matcher when we go do cuda stuff
void calculateDepth(cv::Mat disparityMap){
    float angleDifPerPixel = fov/disparityMap.cols; //This assumes they are equal cameras by having the same pixel range
    std::cout << angleDifPerPixel << std::endl;
    float leftCurrentAngle = 90 - (fov/2) - angleDifPerPixel;
    float rightCurrentAngle;
    int leftPosition;
    int rightPosition;
    float depth;
    sensor_msgs::PointCloud finalResult;
    //I don't remmenber if it's y,x within openCV or x,y
    //To determine you can't see an object you can simply assume if one is outside of the other's range by using their field of view
    for(int u = 0; u < disparityMap.cols; u++){
        for(int v = 0; v < disparityMap.rows; v++){
               leftCurrentAngle += angleDifPerPixel;
               leftPosition = u;
               rightPosition = u + ((float)disparityMap.at<short>(u,v)/16.0f);
               rightCurrentAngle = rightPosition * angleDifPerPixel;
               if (leftCurrentAngle > 91){
                leftCurrentAngle = 180 - leftCurrentAngle;
               }
               if(rightCurrentAngle > 91){
                rightCurrentAngle = 180 -rightCurrentAngle;
               }
               depth = (camerasRelativeDistance*std::sin(leftCurrentAngle) * std::sin(rightCurrentAngle)) / (std::sin(leftCurrentAngle+rightCurrentAngle));
               geometry_msgs::Point32 tempPoint;
                tempPoint.x = u;
                tempPoint.y = v;
                tempPoint.z = depth;
               finalResult.points.push_back(tempPoint);
        }
         leftCurrentAngle = 90 - (fov/2) - angleDifPerPixel;
    }
    finalResult.header.frame_id = "world_base";
    std::cout << finalResult.header.frame_id << std::endl;
    pointCloudPub.publish(finalResult);
    std::cout << "rows: " << disparityMap.rows << " cols: " << disparityMap.cols << std::endl; 
    std::cout<< ((float)disparityMap.at<short>(120,160))/16 << std::endl;
}

float convertToRadiance(float degrees){
    return degrees*0.0174533;
}
float converToDegrees(){
    return 0;
}

//Previously you did not find the x and y within space points but you rather used the image points assuming they were the same
//SPOILER: THEY. ARE. NOT.
void triangulationP2(cv::Mat disparityMap){
    //AngleMagigy -----------------------------------------
    //This assumes both fov's are the same
    float angleDifPerPixelHorizontal = fov/disparityMap.cols;
    float angleDifPerPixelVertical = fov/disparityMap.rows;
    float leftMaxAngle = 90 + (fov/2);
    float rightMaxAngle = 90 + (fov/2); //Alter this if the fov is different between the cameras.
    float leftVerticalAngle = (fov/2);
    float currentVerticalAngle;
    std::cout << leftMaxAngle << std::endl;
    float currentAngleLX;
    float currentAngleRX;
    float currentAngleLY;
    float currentAngleRY;
    //----------------------------------------------------------------
    //Results----------------------------------------
    sensor_msgs::PointCloud finalResult;
    double depth;
    geometry_msgs::Point32 tempPoint;
    //-------------------------------------
    for(int u = 0; u < disparityMap.cols; u++){
        //std::cout << u << std::endl;
        for(int v = 0; v < disparityMap.rows; v++){
            currentAngleLX = leftMaxAngle - (angleDifPerPixelHorizontal*u);
            currentAngleLY = leftMaxAngle - (angleDifPerPixelVertical*v);
            currentVerticalAngle = leftVerticalAngle - (angleDifPerPixelVertical*v);
            currentAngleRX = rightMaxAngle - (angleDifPerPixelHorizontal*( u + ((int)disparityMap.at<uchar>(u,v)/16.0f)));
            if ( (disparityMap.at<uchar>(u,v)/16.0f) > 0 && v == 20 ){
              //  std::cout <<"disp: " << disparityMap.at<uchar>(u,v)/16.0f << std::endl;
            }
            if (u==0){
                //std::cout << "current vertical angle " <<  currentVerticalAngle << std::endl;
            }    
            if (v==1){
                //std::cout << "current horizontal angle " <<  currentAngleLX << " and image2 is " << currentAngleRX  << std::endl;
            }
            if (u == (disparityMap.cols/2) && v == (disparityMap.rows/2)){
            std::cout << "angle of " << currentAngleLX << "as well as angle "<< currentAngleRX << " " <<u << " " <<  ((int)disparityMap.at<uchar>(u,v)/16.0f) << std::endl;
            }
            float addedAngles = currentAngleLX + currentAngleRX;
            //if ( /*addedAngles >= 180 /*||currentAngleLX <= 90 - (fov/2) || currentAngleRX <= 90 - (fov/2) */){
                //Not visible within the cameras therefore the depth cannot be calculated
            //}else{
            //
                if (u == (disparityMap.cols/2) && v == (disparityMap.rows/2)){
                   // std::cout << "passed" << std::endl;
                }
            //
            float addition = currentAngleLX+currentAngleRX;
            //depth = (camerasRelativeDistance* std::sin(convertToRadiance(currentAngleLX)) * std::sin(convertToRadiance(currentAngleRX))) / (std::sin(convertToRadiance(addition)));
             float pixelFocal = (277.19135641132203/0.00375)*1/3*240/240;//(disparityMap.cols * 0.5)/ tan(fov * 0.5 * M_PI/180.0);
             depth = /*pixelFocal*camerasRelativeDistance/  (/*(int)disparityMap.at<uchar>(u,v)/16.0f); */ (277.19135641132203/100)* 1/ (((int)disparityMap.at<uchar>(u,v)/16.0f)*10);
             if (u == (disparityMap.cols/2) && v == (disparityMap.rows/2)){
                std::cout << ((int)disparityMap.at<uchar>(u,v)/*16.0f*/) << std::endl;
                std::cout << "disparity of pixels: " << ((int)disparityMap.at<uchar>(u,v)/16.0f) << " depth?: " <<depth << std::endl;
             }
            //tempPoint.z = (depth * std::tan( convertToRadiance(180 - (currentAngleLX+90))));
            tempPoint.z= (disparityMap.cols - u)* depth/320;//(depth * std::tan( convertToRadiance((currentVerticalAngle))));
            if (tempPoint.z > 0){
                //std::cout << tempPoint.z << std::endl;
            }
            if (u == (disparityMap.cols/2) && v == (disparityMap.rows/2)){
                    //std::cout << currentAngleLX << " " << tempPoint.z << std::endl;
                }
            //std::cout << "up and down calculation leads to: "<< tempPoint.z << std::endl; 
            //Tomorrow you figure this one out 
            //tempPoint.y =/* leftCameraPositionY */+ (depth * std::tan( convertToRadiance(180 - (currentAngleLY+90))));
            tempPoint.y = leftCameraPositionY + (disparityMap.rows - v)* depth/240;
            tempPoint.x = depth;
            if (v == 30){
              //  std::cout << " x is at: " << tempPoint.x << " y is at: " << tempPoint.y << " z is at: " << tempPoint.z << std::endl;
            }
            finalResult.points.push_back(tempPoint);
            }
        //}
    }
    finalResult.header.frame_id = "box";
    std::cout << finalResult.header.frame_id << std::endl;
    pointCloudPub.publish(finalResult);
    std::cout << "rows: " << disparityMap.rows << " cols: " << disparityMap.cols << std::endl; 
    std::cout<< ((float)disparityMap.at<short>(120,160))/16 << std::endl;
}

void pointCLoudMaker(float x, float y,float z){
    pcl::PointCloud<pcl::PointXYZ> result;
}
int main(int argc, char** argv){
    ros::init(argc, argv, "Testing_opencv_stereoBM");
    ros::NodeHandle node;
    ros::Subscriber imageReceiver1 = node.subscribe(camera1Topic, 1,&assignImage1);
    ros::Subscriber imageReceiver2 = node.subscribe(camera2Topic, 1, &assignImage2);
    pointCloudPub = node.advertise<sensor_msgs::PointCloud>("/pointCloud",30);
    //ros::Subscriber modelInfoReceiver = node.subscribe("/gazebo/model_states",60, &getTransformationAndRotation);
    cv::Mat result;
    if (result.empty()){
        std::cout << "result is null" << std::endl;
    }
    cv::Ptr<cv::StereoBM> work = cv::StereoBM::create(16,21);
    //cv::Ptr<cv::StereoSGBM> work = cv::StereoSGBM::create(16, 32, 3, 32, 240, 5, 4, 1, 100, 20 ,true);
    ros::Rate rate(30);
    cv::Mat tempResult;
    cv::Matx33f f;
    bool onlyOnce = false;
    while(ros::ok()){
        ros::spinOnce();
        if ((!image1.empty()) && (!image2.empty())){
            
            cv::Mat temp1;
            cv::Mat temp2;
            cv::Mat temp3;
            std::cout << image1.depth() << std::endl;
            cv::cvtColor(image1,temp1,cv::COLOR_RGB2GRAY);
            cv::cvtColor(image2,temp2,cv::COLOR_RGB2GRAY);
            std::cout << temp1.depth() << std::endl;
            work->compute(temp2,temp1,result);
            cv::Mat test = result.clone();
            test.convertTo(test,CV_8U,1/256.0);
            //retrify...
            //cv::reprojectImageTo3D(test,f,)
            std::cout << result.depth() << std::endl;
            if (result.empty()){
                std::cout << "result is null" << std::endl;
            }
            double minVal;
            double maxVal;
            cv::minMaxLoc(result, &minVal, &maxVal);
            result.convertTo(temp3, 1, 255/(maxVal-minVal));
            //calculateDepth(result);
            triangulationP2(result);
            

            std::cout << result.size() << std::endl;
            
            cv::imshow("Image window", temp1);
            cv::imshow("Image window2", temp2);
            for(int i = 0; i < temp3.rows; i++){
                if(((int)temp3.at<uchar>(temp3.cols/2,i)/16.0) != -240){
                    std::cout << temp3.cols/2 << " " <<  (int)temp3.at<uchar>(temp3.cols/2,i)/16.0  << std::endl;
                    break;
                } 
            }
            cv::imshow("Stereo view", temp3);
            cv::waitKey();

            tempResult = result;
            
        }
        rate.sleep();
    }
    return 0;
}
