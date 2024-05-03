#include<ros/ros.h>
#include<opencv4/opencv2/opencv.hpp>
#include<stdlib.h>
#include<sensor_msgs/Image.h>
/*
    This code will run a stereo vision pipeline over a gazebo 
    simulation by using the model positions provided 
    by /gazebo/model_states
*/
const std::string camera1Topic = "a";
const std::string camera2Topic = "b";
cv::Mat camera1Image;
cv::Mat camera2Image;

void assignImage(const sensor_msgs::Image receivedImage){

}

int main (int argc, char** argv){
    ros::init(argc, argv, "Stereo_vision_pipeline");
    ros::NodeHandle node;
    ros::AsyncSpinner spinner(1);
    spinner.start();

    //Camera calibration

    /*Stereo calculation using OpenCV's StereoBM - 
        The loop should start here - with an option to calibrate
         a camera if a message is received from a specific topic.
         So essentually a .....state machine! That's it!
            //+- What it should do is calculate the similar pixels and matching them and 
                THEN calculate the distance between the pixels and the cameras to create our values 
        
    */
    ros::Subscriber camera1 =  node.subscribe(camera1Topic,1, &assignImage);
    ros:: Rate rate(30);

    //Convertion to point cloud


}