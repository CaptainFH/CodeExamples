#include<ros/ros.h>
#include<opencv4/opencv2/opencv.hpp>
#include<sensor_msgs/Image.h>
#include<stdlib.h>
#include <cv_bridge/cv_bridge.h>
#include"imageReceiver.h"

//Fix this test by tomorrow please! 
using namespace std;

imageReceiver::imageReceiver receiver;
const std::string camera1Topic = "/box/camera1/image_raw";
const std::string camera2Topic ="/box2/camera2/image_raw";
cv::Mat image1;
cv::Mat image2;


void assignImage1(const sensor_msgs::ImageConstPtr& input){

image1 = receiver.convertToMat(input);
}

void assignImage2(const sensor_msgs::ImageConstPtr& input){  

image2 = receiver.convertToMat(input);
}

int main (int argc, char** argv){
    ros::init(argc, argv, "Testing_input_gazebo_ver");
    ros::NodeHandle node;
    //cv::namedWindow("image window");
    //cv::namedWindow("image window2");
    ros::Subscriber imageReceiver1 = node.subscribe(camera1Topic, 1,&assignImage1);
    ros::Subscriber imageReceiver2 = node.subscribe(camera2Topic, 1, &assignImage2);
    //ros::spin();
    ros::Rate rate(30);
    while(ros::ok()){
        ros::spinOnce();
        if ((!image1.empty()) && (!image2.empty())){
            cv::Mat temp1 = image1;
            cv::Mat temp2 = image2;
            cv::imshow("Image window", temp1);
            cv::imshow("Image window2", temp2);
            cv::waitKey();
        }
        rate.sleep();
    }
}