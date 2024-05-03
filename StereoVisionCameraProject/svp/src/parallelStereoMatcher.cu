#include<stdio.h>
#include<iostream>
#include<cstdlib>
#include<opencv4/opencv2/opencv.hpp>
#include<pcl/io/pcd_io.h>
#include<pcl/point_types.h>
#include<pcl/conversions.h>

__global__
void test(){
    printf("%d core is saying hi\n", threadIdx.x);
}

__global__ 
void pointCloudGenerator(float *disparity,float *resultX, float *resultY, float *resultZ, int length, float pixelAngleX, float pixelAngleY, int rowLength, float cameraRelativeDistance, float totalAngle, float angleDifPerPixel, float *leftCameraPosition){
    int dimension = blockIdx.x;
    int start = threadIdx.x;
    int currentPixelX;
    int currentPixelY;
    float angleX;
    float angleY;
    float angleXR;
    for(int i = start; i < length; i+=dimension){
        currentPixelY = i/rowLength;
        currentPixelX =  i%rowLength;
        angleX = totalAngle - (angleDifPerPixel*currentPixelX);
        angleY = totalAngle - (angleDifPerPixel*currentPixelY);
        angleXR = totalAngle - (angleDifPerPixel*(currentPixelX + disparity[i]));
        //Calculating angles is gonna be hard....
        if((angleX + angleXR) >= 180){
            break;
        }
        resultZ[i] = (cameraRelativeDistance*std::sin(angleX) * std::sin(angleXR)) / (std::sin(angleX+angleXR));
        resultX[i] = leftCameraPosition[0] + (resultZ[i] / std::tan(angleX));
        resultY[i] = leftCameraPosition[1] + (resultZ[i] / std::tan(angleY));
    }
}
__global__
void findDisparity(float *averagePixels1, float *averagePixels2, float *disparity, int length){
   int dimension = blockDim.x;
   int start = threadIdx.x;
   float similarity;
   float currentSimilarity;
   for(int i = start; i < length; i+=dimension){
        similarity = 100;
        disparity[i] = length -1;
        for(int j = start; j < length; j+=dimension){
             currentSimilarity = std::abs(averagePixels1[i] - averagePixels2[j]);
            if(std::abs(currentSimilarity) < std::abs(similarity)){
                similarity = currentSimilarity;
                disparity[i] = i - j;
            }
         }
   }
}
__global__
void blockMatching(float *pixels, float *averageMap, int length, int firstRow){
    int dimension = blockDim.x;
    int average;
    int start = threadIdx.x;
    for(int i = start; i < length; i+=dimension){
        average = 9;
        averageMap[i] = pixels[i];
        if (i > firstRow){//include the others
            averageMap[i] += pixels[i-dimension];
            if(i%dimension != 1){
                averageMap[i] += pixels[i-dimension+1];
            }else{
                average--;
            }
            if (i%dimension != 0){
                averageMap[i] += pixels[i-dimension-1];
            }else{
                average--;
            }
        }else{
            average-=3;
        }
        if(i%dimension != 1){
            averageMap[i] += pixels[i+1];
        }else{
            average--;
        }
        if (i%dimension != 0){
            averageMap[i] += pixels[i-1];
        }else{
             average--;
        }
        if(i+dimension > length){
            averageMap[i] += pixels[i+dimension];
            if (i%dimension != 0){
                averageMap[i] += pixels[i+dimension-1];
            }else{
                average--;
            }
            if(i%dimension != 1){
                averageMap[i] += pixels[i+dimension+1];
            }else{
                average--;
            }
        }else{
            average--;
        }
        averageMap[i] = averageMap[i] / average;
    }
}   

//Camera Calibration Values
float fov;
float cameraRelativeDistance;
float leftCameraPositionX;
float rightCameraPositionX;
float leftCameraPositionY;
float rightCameraPositionY;
///////////////////////////////////////////////////////////////



//separate the images into the values of the lines, based on the size of the blocks.
//Cuda malloc is used for global memory
//Local memory is used when using to many registers per thread - it's scope is per thread 
//L1 is the individual multiprocessor cache while L2 is the share memory - they are composed by the same bytes 
//- divided by us
//constant memory is good beacuse fast
//Texture memory is read only - but can be accessed by all threats 
//Shared memory is extremely fast used by a block of memory
//registers are fast- declared in a kernel.
//
//Matching is wack! just figure out how ya gonna be able to observe the second image's result in order to match it.
//Makit it one! To avoid memory leakage!!!!
//FIX TRIANGULATION BY MAKING SURE THE VALUES BETWEEN EACH PIXEL CORRESPOND TO THE PIXEL SIZE!!!!
int blockSize = 9;

int main (int argc, char** argv){
    //Extract the images from the input
    cv::Mat image = cv::imread(argv[1], cv::IMREAD_COLOR);
    cv::Mat image2 = cv::imread(argv[2], cv::IMREAD_COLOR);
    //Converts them to grayscale
    cv::cvtColor(image,image,cv::COLOR_RGB2GRAY);
    cv::cvtColor(image2,image2,cv::COLOR_RGB2GRAY);
    //Declaring neeeded memory
    float *imagePixels;
    float *image2Pixels;
    float *imagePixelsInGpu;
    float *image2PixelsInGpu;
    float *averageMap1;
    float *averageMap2;
    float *disparityMap;
    float *resultX;
    float *resultY;
    float *resultZ;
    //Allocating needed memory CPU
    int totalPixels = (image.cols * image.rows);
    imagePixels = (float*)malloc(sizeof(float) * totalPixels);
    image2Pixels = (float*)malloc(sizeof(float) * totalPixels);
    //Allocating needed memory GPU
    cudaMalloc(&imagePixelsInGpu, sizeof(float) * totalPixels);
    cudaMalloc(&image2PixelsInGpu, sizeof(float) * totalPixels);
    cudaMalloc(&averageMap1, sizeof(float) * totalPixels);
    cudaMalloc(&averageMap2, sizeof(float) * totalPixels);
    cudaMalloc(&disparityMap, sizeof(float)* totalPixels);
    cudaMalloc(&resultX, sizeof(float)* totalPixels);
    cudaMalloc(&resultY, sizeof(float)* totalPixels);
    cudaMalloc(&resultZ, sizeof(float)* totalPixels);
    //separating the image into a float array
    int blocksDef = image.cols/blockSize;
    for (int i = 0; i < image.cols; i++){
        for (int j = 0; j  < image.rows; j++){
            imagePixels[i] = (float)image.at<short>(i,j);
            image2Pixels[i] = (float)image2.at<short>(i,j);
        }
    }
    std::cout << 3/5 << std:: endl;
    blockMatching<<<1,image.cols>>>(imagePixelsInGpu, averageMap1, totalPixels, image.rows);
    cudaDeviceSynchronize();
    blockMatching<<<1,image2.cols>>>(image2PixelsInGpu,averageMap2,totalPixels, image2.rows);
    cudaDeviceSynchronize();
    findDisparity(averageMap1,averageMap2,disparityMap,totalPixels);
    cudaDeviceSynchronize();
    //Divde height of the image by numblocks for thread blocks 
    //Divide widgth by blocks for the number of threads.
    //cudaMalloc(*image1AsVector, sizeof(float) *(image.rows * image.cols),);

    test<<<1,1>>>();
    pcl::PointCloud<pcl::_PointXYZ> result;
    pcl::PointXYZ tempPoint;
    for(int i = 0; i < totalPixels; i++){
        tempPoint.x = resultX[i];
        tempPoint.y = resultY[i];
        tempPoint.z = resultZ[i];
        result.points.push_back(tempPoint);
    }

    pcl::PCLPointCloud2 msg;
    pcl::toPCLPointCloud2(result,msg);

    std::cout << result << std::endl;

    cv::imshow(argv[1], image);  
    cv::waitKey();
    free(imagePixels);
    free(image2Pixels);
    cudaFree(imagePixelsInGpu);
    cudaFree(image2PixelsInGpu);
    cudaFree(averageMap1);
    cudaFree(averageMap2);
    cudaFree(disparityMap);
    cudaFree(resultX);
    cudaFree(resultY);
    cudaFree(resultZ);
    return 0;
}

cv::Mat parallelMatcher(cv::Mat imageOne, cv::Mat imageTwo, int blockSize){
    cv::Mat result; 
    cv::Mat imageOneGPU, imageTwoGPU;
    //cudaMalloc(&imageOneGPU,sizeof(imageOne));
    //cudaMalloc(&imageTwoGPU,sizeof(imageTwo));

    return result;
}