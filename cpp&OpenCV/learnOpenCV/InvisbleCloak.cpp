//
//  InvisibleCloak.cpp
//  learnOpenCV
//
//  Created by tangxi on 2019/7/19.
//  Copyright © 2019年 tangxi. All rights reserved.
//

#include <stdio.h>
#include<opencv2/opencv.hpp>
#include<opencv2/imgproc/imgproc.hpp>
#include<opencv2/highgui/highgui.hpp>
#include<iostream>

using namespace std;
using namespace cv;

int main()
{
    VideoCapture cap("/Users/tangxi/Downloads/Video/Input.mp4");
    Mat background;
    if (!cap.isOpened())
    {
        return 0;
    }
    //必须是静止的背景，因此可以先取背景帧
    for (int i = 0;i<60;i++)
    {
        cap >> background;
    }
    
    while(1)
    {
        Mat frame;
        
        cap>>frame;
        
        if(frame.empty())
        {
            break;
        }
        Mat hsv;
        //转到hsv空间
        cvtColor(frame,hsv,COLOR_BGR2HSV);
        
        Mat mask1,mask2;
        //hsv空间红色像素的范围：H的值有两个范围：(0,10)和(170,180)
        cv::inRange(hsv, Scalar(0,120,70),Scalar(10,255,255),mask1);
        cv::inRange(hsv, Scalar(170,120,70),Scalar(180,255,255),mask2);
        
        //此处的加相当于或运算，因为会自带溢出即为255的处理
        mask1 = mask1 + mask2;
        
        Mat kernel = Mat::ones(3,3,CV_32F);
        //形态学开运算：先腐蚀再膨胀，可以清除一些小的亮东西。闭运算：清除小的黑点
        morphologyEx(mask1, mask1, MORPH_OPEN, kernel);
        //腐蚀运算，清除一些孤立点
        morphologyEx(mask1, mask1, MORPH_DILATE, kernel);
        //mask2=mask1的反，则代表mask2上不是红色的区域都是白色
        bitwise_not(mask1,mask2);
        Mat res1,res2,final_output;
        //裁剪当前帧的res1 = frame &frame & mask2，取非红色区域的其他区域
        bitwise_and(frame,frame,res1,mask2);
        //裁剪出背景帧的res1 = background & background & mask1，只取红色区域对应的区域
        bitwise_and(background,background,res2,mask1);
        
        //加权相加，两个权值都是1
        addWeighted(res1, 1, res2, 1, 0, final_output);
        
        imshow("Magic!!!",final_output);
        
        char c = waitKey(25);
        if(c == 27)
            break;
    }
    return 0;
}
