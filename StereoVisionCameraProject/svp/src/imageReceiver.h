#include<ros/ros.h>
#include<opencv4/opencv2/opencv.hpp>
#include<sensor_msgs/Image.h>
#include<stdlib.h>
#include <cv_bridge/cv_bridge.h>


namespace imageReceiver{
    class imageReceiver{
    public:
        cv::Mat convertToMat(const sensor_msgs::ImageConstPtr& input){
            cv::Mat image1;    
            cv_bridge::CvImagePtr imagePointer1_cv;
            try{    
                imagePointer1_cv = cv_bridge::toCvCopy(input, "");
            }
            catch(cv_bridge::Exception& e){
                std::cout << "error in image conversion" << std::endl;
            }
            image1 = imagePointer1_cv->image;
            return image1;
        };
    };
}