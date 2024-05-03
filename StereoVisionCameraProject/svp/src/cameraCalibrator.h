#include<opencv4/opencv2/opencv.hpp>

/// Focal lensenses is the distance between the camera center and the image center (principal axis)

//Foward Imaging model. Hold on! Calibration is different from the expected! - So tuesday ya gotta get that fixed

//Delete this later plox
namespace cameraCalibrator{
    class cameraCalibrator{
    private:
        float intrinsicMatrix[3][3];
        float extrinsicTransform[1][3];
        float extrinsicRotation;
    public:
        cameraCalibrator(){
            intrinsicMatrix[1][0] = 0;
            intrinsicMatrix[2][0] = 0;
            intrinsicMatrix[2][1] = 0;
        }
        cameraCalibrator(float screw){
            intrinsicMatrix[0][1] = screw;
            cameraCalibrator();
        }
        //Wrong! FocalV is the focal length * the aspect ration: It indicates distoritions
        cameraCalibrator(float focalH, float focalV){
            intrinsicMatrix[0][0] = focalH;
            intrinsicMatrix[1][1] = focalV;
            cameraCalibrator();
        }
        cameraCalibrator(float focalH, float focalV, float screw){
            intrinsicMatrix[0][1] = screw;
            cameraCalibrator(focalH, focalV);
        }
        void setExtrinsicValues(float x, float y, float z, float angleOfRotation){
            extrinsicTransform[0][0] = x;
            extrinsicTransform[0][1] = y;
            extrinsicTransform[0][2] = z;
            extrinsicRotation = angleOfRotation;
        };
    

    };
}