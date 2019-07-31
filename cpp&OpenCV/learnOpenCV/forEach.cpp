//
//  main.cpp
//  learnOpenCV
//
//  Created by tangxi on 2019/7/29.
//  Copyright © 2019年 tangxi. All rights reserved.
//

#include <stdio.h>
#include <opencv2/opencv.hpp>
#include <opencv2/core/core.hpp>
#include <opencv2/imgproc/imgproc.hpp>

using namespace std;
using namespace cv;


typedef Point3_<uint8_t> Pixel;

void tic(double &t)
{
    t = (double)getTickCount();
}

double toc(double &t)
{
    return ((double)getTickCount() -t )/getTickFrequency();
}

void complicatedThreshold(Pixel &pixel)
{
    if(pow(double(pixel.x) / 10,2.5)>100)
    {
        pixel.x = 255;
        pixel.y = 255;
        pixel.z = 255;
    }
    else
    {
        pixel.x = 0;
        pixel.y = 0;
        pixel.z = 0;
    }
}
//并行执行带函数对象
struct Operator
{
    void operator ()(Pixel &pixel,const int *position) const
    {
        complicatedThreshold(pixel);
    }
};


int main()
{
    Mat image = imread("/Users/tangxi/cpp/learnOpenCV/utils/butterfly.jpg");
    //放大三十倍
    resize(image,image,Size(),30,30);
    
    cout<<"Image Size" <<image.size()<<endl;
    int numTrials = 5;
    cout<<"Number of trials:"<<numTrials<<endl;
    
    Mat image1 = image.clone();
    Mat image2 = image.clone();
    Mat image3 = image.clone();
    
    double t;
    //第一种像素遍历方式：最简单的像素访问方式:Native pixel access
    tic(t);
    for(int n = 0 ; n < numTrials ; n++)
    {
        for(int r = 0;r<image.rows;r++)
        {
            for(int c = 0 ; c < image.cols ; c++)
            {
                Pixel pixel = image.at<Pixel>(r,c);
                complicatedThreshold(pixel);
            }
        }
    }
    cout<<"Native way:"<<toc(t)<<endl;
    
    //第二种像素遍历方式：指针，必须保证图像的存储是连续的
    tic(t);
    cout<<image1.isContinuous()<<endl;
    for(int n = 0;n<numTrials;n++)
    {
        Pixel *pixel = image.ptr<Pixel>(0,0);
        
        const Pixel *endPixel = pixel + image1.cols * image1.rows;
        
        for(;pixel!=endPixel;++pixel)
        {
            complicatedThreshold(*pixel);
        }
    }
    cout<<"Pointer Arithmetic:"<< toc(t) <<endl;
    
    //第三种方式:forEach
    tic(t);
    for(int n = 0;n<numTrials;n++)
    {
        image2.forEach<Pixel>(Operator());
    }
    cout<<"for Each:"<<toc(t)<<endl;
    
    //第四种方式
#if __cplusplus >= 201103L || (__cplusplus < 200000 && __cplusplus > 199711L)
    tic(t);
    for(int n = 0;n<numTrials;n++)
    {
        image3.forEach<Pixel>(
        [](Pixel &pixel, const int*position) -> void
                              {
                                  complicatedThreshold(pixel);
                              }
                              );
    }
    cout<< "for Each C++ 11:"<<toc(t)<<endl;
#endif
    waitKey(0);
    return 0;
    
}
