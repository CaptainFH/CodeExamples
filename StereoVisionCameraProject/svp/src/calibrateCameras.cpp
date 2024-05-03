//Code originally from Nicolai Nielsen - will be adapted later
#include<iostream>
#include<opencv2/calib3d.hpp>
#include<opencv2/core.hpp>
#include<opencv2/highgui.hpp>
#include<opencv2/imgproc.hpp>



int main(int argc, char** argv){

    std::vector<cv::String> filenames;
    cv::glob("/home/fernando/catkin_ws/src/svp/data_set/left**.jpg",filenames,false);
    cv::Size patternSize(6, 9);
    std::vector<std::vector<cv::Point2f>>q(filenames.size());

    std::vector<std::vector<cv::Point3f>> Q;
    int checkerBoard[2] = {7,10};
    float fieldSize = 2.5000000372529030e-02;
    
    //Defining the world coordinate for 3D points
    std::vector<cv::Point3f> objp;
    for(int i = 1; i< checkerBoard[1]; i++){
        for(int j = 1; j<checkerBoard[0]; j++){
            objp.push_back(cv::Point3f(j*fieldSize,i*fieldSize,0));
        }
    }

    std::vector<cv::Point2f> imgPoint;
    //Detect Feature Points
    std::size_t i = 0;
    for(auto const &f: filenames){
        std::cout << std::string(f) << std::endl;

        //Read in images
        cv::Mat img = cv::imread(filenames[i]);
        cv::Mat gray;

        cv::cvtColor(img, gray, cv::COLOR_RGBA2GRAY);

        bool patternFound = cv::findChessboardCorners(gray, patternSize, q[i], cv::CALIB_CB_ADAPTIVE_THRESH + cv::CALIB_CB_NORMALIZE_IMAGE + cv::CALIB_CB_FAST_CHECK);
        std::cout << patternFound << " checked" << std::endl;
        if(patternFound){
            cv::cornerSubPix(gray, q[i], cv::Size(11,11), cv::Size(-1,-1), cv::TermCriteria(cv::TermCriteria::EPS + cv::TermCriteria::MAX_ITER, 30,0.1));
            Q.push_back(objp); 
        }
        cv::drawChessboardCorners(gray, patternSize, q[i], patternFound);
        cv::imshow("chessboard detection", gray);
        cv::waitKey(0);

        i++;
    }
    std::cout << "all images checked" << std::endl;
    //Intrinsic camera Matrix
    cv::Matx33f K(cv::Matx33f::eye());
    //Distortion coefficients
    cv::Vec<float, 5> k(0,0,0,0,0); 

    std::vector<cv::Mat> rvecs, tvecs;
    std::vector<double> stdIntrinsics, stdExtrinsics, perViewErrors;
    int flags = cv::CALIB_FIX_ASPECT_RATIO + cv::CALIB_FIX_K3 + cv::CALIB_ZERO_TANGENT_DIST + cv::CALIB_FIX_PRINCIPAL_POINT;
    cv::Size frameSize(640, 480);
    
    std::cout << q.size() << " " << q.size() << std::endl;
    
    float error = cv::calibrateCamera(Q, q, frameSize, K, k, rvecs, tvecs, flags);
    std::cout << "Reprojection error = " << error << "\nK =\n"
        << K << "\nk=\n"
        << k << std::endl;

    cv::Mat mapX, mapY;
    cv::initUndistortRectifyMap(K,k,cv::Matx33f::eye(),K, frameSize, CV_32FC1, mapX, mapY);
    for(auto const &f : filenames){
        std::cout << std::string(f) << std::endl;

        cv::Mat img = imread(f, cv::IMREAD_COLOR);
        cv::Mat imgUndistorted;

        cv::remap(img, imgUndistorted, mapX, mapY, cv::INTER_LINEAR);

        cv::imshow("undistorted image", imgUndistorted);
        cv::waitKey(0);
    }
    return 0;

}