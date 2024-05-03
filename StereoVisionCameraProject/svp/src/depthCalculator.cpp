#include<opencv4/opencv2/opencv.hpp>
#include"imageReceiver.h"
//Example of how to calculate depth from 2 pixels found in the cameras

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


float dephCalculator(cv::Mat firstImage, cv::Mat secondImage){

}


int main(int argc, char** argv){
    ros::init(argc, argv, "Stereo_vision_pipeline");
    ros::NodeHandle node;

    ros::Subscriber image1Subscriber = node.subscribe(camera1Topic,1,&assignImage1);
    ros::Subscriber image2Subscriber = node.subscribe(camera2Topic,2, &assignImage2);

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