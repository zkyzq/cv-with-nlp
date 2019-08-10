////
////  ColorDetect.cpp
////  learnOpenCV
////
////  Created by tangxi on 2019/8/10.
////  Copyright © 2019年 tangxi. All rights reserved.
////
//
//#include <stdio.h>
//#include <opencv2/opencv.hpp>
//
//using namespace cv;
//using namespace std;
//
//Mat img,placeholders;
//
//void onMouse(int, void*)
//{
//
//}
//
//int main(int argc, const char **argv)
//{
//    vector<string> filenames;
//    for (int i =0;i<=10;i++)
//    {
//        string filename = "images/rub0" + to_string(i) + ".jpg";
//        filenames.push_back(filename);
//    }
//    for(int j = 0;j<filenames.size();j++)
//    {
//        img = imread(filenames[j]);
//
//        Size rsize(400,400);
//        resize(img,img,rsize);
//        if(img.empty())
//        {
//            return -1;
//        }
////        Vec3b bgrPixel(img.at<Vec3b>())
//        Mat3b hsv,lab,ycb;
//        cvtColor(img, hsv, COLOR_BGR2HSV);
//        cvtColor(img, lab, COLOR_BGR2Lab);
//        cvtColor(img, ycb, COLOR_BGR2YCrCb);
//
//        imshow("img",img);
//        imshow("hsv",hsv);
//        imshow("lab",lab);
//        imshow("ycb",ycb);
//        waitKey(0);
//    }
//    return 0;
//}


#include "opencv2/opencv.hpp"
#include <iostream>
#include <cstring>

using namespace cv;
using namespace std;

//指示键盘是否有案件的全局标示，在回调函数里面更新
bool show = false;


// 滑动条的回调函数
void onTrackbarActivity(int pos, void* userdata){
    // 更新show这个全局标示，开始显示
    show = true;
    return;
}


int main(int argc, char **argv)
{
    int image_number = 0;
    int nImages = 10;
    //如果命令行有输入，以命令行的输入为准
    if(argc > 1)
        nImages = atoi(argv[1]);
    char filename[20];
    sprintf(filename,"images/rub%02d.jpg",image_number%nImages);
    
    Mat original = imread(filename);
    
    // 将图像resize成相同的大小
    int resizeHeight = 250;
    int resizeWidth = 250;
    Size rsize(resizeHeight,resizeWidth);
    resize(original, original, rsize);
    
    // 显示窗口的初始化位置，在整个屏幕(50,50)的地方
    int initialX = 50;
    int    initialY = 50;
    // 创建窗口
    namedWindow("P-> Previous, N-> Next", WINDOW_AUTOSIZE);
    namedWindow("SelectBGR", WINDOW_AUTOSIZE);
    namedWindow("SelectHSV", WINDOW_AUTOSIZE);
    namedWindow("SelectYCB", WINDOW_AUTOSIZE);
    namedWindow("SelectLAB", WINDOW_AUTOSIZE);
    
    // 将其他几个窗口y平行移到各自的位置
    moveWindow("P-> Previous, N-> Next", initialX, initialY);
    moveWindow("SelectBGR", initialX + 1 * (resizeWidth + 5), initialY);
    moveWindow("SelectHSV", initialX + 2 * (resizeWidth + 5), initialY);
    moveWindow("SelectYCB", initialX + 3 * (resizeWidth + 5), initialY);
    moveWindow("SelectLAB", initialX + 4 * (resizeWidth + 5), initialY);
    
    // 创建滑动条 for YCB
    createTrackbar("CrMin", "SelectYCB", 0, 255, onTrackbarActivity);
    createTrackbar("CrMax", "SelectYCB", 0, 255, onTrackbarActivity);
    createTrackbar("CbMin", "SelectYCB", 0, 255, onTrackbarActivity);
    createTrackbar("CbMax", "SelectYCB", 0, 255, onTrackbarActivity);
    createTrackbar("YMin", "SelectYCB", 0, 255, onTrackbarActivity);
    createTrackbar("YMax", "SelectYCB", 0, 255, onTrackbarActivity);
    
    // 创建滑动条forHSV
    createTrackbar("HMin", "SelectHSV", 0, 180, onTrackbarActivity);
    createTrackbar("HMax", "SelectHSV", 0, 180, onTrackbarActivity);
    createTrackbar("SMin", "SelectHSV", 0, 255, onTrackbarActivity);
    createTrackbar("SMax", "SelectHSV", 0, 255, onTrackbarActivity);
    createTrackbar("VMin", "SelectHSV", 0, 255, onTrackbarActivity);
    createTrackbar("VMax", "SelectHSV", 0, 255, onTrackbarActivity);
    // 创建滑动条forRGB
    createTrackbar("BMin", "SelectBGR", 0, 255, onTrackbarActivity);
    createTrackbar("BMax", "SelectBGR", 0, 255, onTrackbarActivity);
    createTrackbar("GMin", "SelectBGR", 0, 255, onTrackbarActivity);
    createTrackbar("GMax", "SelectBGR", 0, 255, onTrackbarActivity);
    createTrackbar("RMin", "SelectBGR", 0, 255, onTrackbarActivity);
    createTrackbar("RMax", "SelectBGR", 0, 255, onTrackbarActivity);
    
    // 创建滑动条forLAB
    createTrackbar("LMin", "SelectLAB", 0, 255, onTrackbarActivity);
    createTrackbar("LMax", "SelectLAB", 0, 255, onTrackbarActivity);
    createTrackbar("AMin", "SelectLAB", 0, 255, onTrackbarActivity);
    createTrackbar("AMax", "SelectLAB", 0, 255, onTrackbarActivity);
    createTrackbar("BMin", "SelectLAB", 0, 255, onTrackbarActivity);
    createTrackbar("BMax", "SelectLAB", 0, 255, onTrackbarActivity);
    
    // 所有窗口初始化显示第一张照片
    imshow("SelectHSV", original);
    imshow("SelectYCB", original);
    imshow("SelectLAB", original);
    imshow("SelectBGR", original);
    
    // 定义一些局部变量，每个颜色空间有六个int变量，两个scalar变量,这些值初始都是0，所以点击屏幕之后就会所有的显示黑色
    int BMin, GMin, RMin;
    int BMax, GMax, RMax;
    Scalar minBGR, maxBGR;
    
    int HMin, SMin, VMin;
    int HMax, SMax, VMax;
    Scalar minHSV, maxHSV;
    
    int LMin, aMin, bMin;
    int LMax, aMax, bMax;
    Scalar minLab, maxLab;
    
    int YMin, CrMin, CbMin;
    int YMax, CrMax, CbMax;
    Scalar minYCrCb, maxYCrCb;
    
    Mat imageBGR, imageHSV, imageLab, imageYCrCb;
    Mat maskBGR, maskHSV, maskLab, maskYCrCb;
    Mat resultBGR, resultHSV, resultLab, resultYCrCb;
    
    char k;
    while (1)
    {
        imshow("P-> Previous, N-> Next", original);
        k = waitKey(1) & 0xFF;
        
        //等待案件，显示下一个图片或者上一个图片
        if (k =='n')
        {
            image_number++;
            sprintf(filename,"images/rub%02d.jpg",image_number%nImages);
            original = imread(filename);
            resize(original,original,rsize);
            show = true;
        }

        else if (k =='p')
        {
            image_number--;
            if(image_number < 0)
            {
                image_number = 0;
            }
            sprintf(filename,"images/rub%02d.jpg",image_number%nImages);
            original = imread(filename);
            resize(original,original,rsize);
            show = true;
        }
        
        // 当按下esc键的时候退出
        if (k == 27){
            break;
        }
        
        if (show)
        {
            show = false;
            // 获取目前相应滑动条的值
            BMin = getTrackbarPos("BMin", "SelectBGR");
            GMin = getTrackbarPos("GMin", "SelectBGR");
            RMin = getTrackbarPos("RMin", "SelectBGR");
            
            BMax = getTrackbarPos("BMax", "SelectBGR");
            GMax = getTrackbarPos("GMax", "SelectBGR");
            RMax = getTrackbarPos("RMax", "SelectBGR");
            
            minBGR = Scalar(BMin, GMin, RMin);
            maxBGR = Scalar(BMax, GMax, RMax);
            
            HMin = getTrackbarPos("HMin", "SelectHSV");
            SMin = getTrackbarPos("SMin", "SelectHSV");
            VMin = getTrackbarPos("VMin", "SelectHSV");
            
            HMax = getTrackbarPos("HMax", "SelectHSV");
            SMax = getTrackbarPos("SMax", "SelectHSV");
            VMax = getTrackbarPos("VMax", "SelectHSV");
            
            minHSV = Scalar(HMin, SMin, VMin);
            maxHSV = Scalar(HMax, SMax, VMax);
            
            LMin = getTrackbarPos("LMin", "SelectLAB");
            aMin = getTrackbarPos("AMin", "SelectLAB");
            bMin = getTrackbarPos("BMin", "SelectLAB");
            
            LMax = getTrackbarPos("LMax", "SelectLAB");
            aMax = getTrackbarPos("AMax", "SelectLAB");
            bMax = getTrackbarPos("BMax", "SelectLAB");
            
            minLab = Scalar(LMin, aMin, bMin);
            maxLab = Scalar(LMax, aMax, bMax);
            
            YMin = getTrackbarPos("YMin", "SelectYCB");
            CrMin = getTrackbarPos("CrMin", "SelectYCB");
            CbMin = getTrackbarPos("CbMin", "SelectYCB");
            
            YMax = getTrackbarPos("YMax", "SelectYCB");
            CrMax = getTrackbarPos("CrMax", "SelectYCB");
            CbMax = getTrackbarPos("CbMax", "SelectYCB");
            
            minYCrCb = Scalar(YMin, CrMin, CbMin);
            maxYCrCb = Scalar(YMax, CrMax, CbMax);
            
            // 转换到其他三个空间
            original.copyTo(imageBGR);
            cvtColor(original, imageHSV, COLOR_BGR2HSV);
            cvtColor(original, imageYCrCb, COLOR_BGR2YCrCb);
            cvtColor(original, imageLab, COLOR_BGR2Lab);
            
            // 利用inrange函数来分割颜色，得到掩模图像，再和原图像相与，得到只有某个颜色段的图像
            inRange(imageBGR, minBGR, maxBGR, maskBGR);
            resultBGR = Mat::zeros(original.rows, original.cols, CV_8UC3);
            bitwise_and(original, original,resultBGR, maskBGR);
            cout<<"minBGR:"<<minBGR<<endl;
            cout<<"maxBGR:"<<maxBGR<<endl;
            

            
            inRange(imageHSV, minHSV, maxHSV, maskHSV);
            resultHSV = Mat::zeros(original.rows, original.cols, CV_8UC3);
            bitwise_and(original, original, resultHSV, maskHSV);
            cout<<"minHSV:"<<minHSV<<endl;
            cout<<"maxHSV:"<<maxHSV<<endl;
            
            inRange(imageYCrCb, minYCrCb, maxYCrCb, maskYCrCb);
            resultYCrCb = Mat::zeros(original.rows, original.cols, CV_8UC3);
            bitwise_and(original, original, resultYCrCb, maskYCrCb);
            cout<<"minYCrCb:"<<minYCrCb<<endl;
            cout<<"maxYCrCb:"<<maxYCrCb<<endl;
            
            inRange(imageLab, minLab, maxLab, maskLab);
            resultLab = Mat::zeros(original.rows, original.cols, CV_8UC3);
            bitwise_and(original, original, resultLab, maskLab);
            cout<<"minLab:"<<minLab<<endl;
            cout<<"maxLab:"<<maxLab<<endl;
            
            // 显示结果
            imshow("SelectBGR", resultBGR);
            imshow("SelectYCB", resultYCrCb);
            imshow("SelectLAB", resultLab);
            imshow("SelectHSV", resultHSV);
        }
    }
    destroyAllWindows();
    return 0;
}
