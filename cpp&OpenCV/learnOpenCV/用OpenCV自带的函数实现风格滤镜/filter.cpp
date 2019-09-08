//
//  npr.cpp
//  learnOpenCV
//
//  Created by tangxi on 2019/9/8.
//  Copyright © 2019年 tangxi. All rights reserved.
//

#include <stdio.h>
#include <opencv2/opencv.hpp>

using namespace cv;
using namespace std;

int main()
{
    Mat img = imread("utils/cow.jpg");
    Mat imout,imout_gray;
    //Edge preserving Filter with two different flags
    edgePreservingFilter(img, imout,RECURS_FILTER);
    imwrite("utils/edgePreservingFilter-1.jpg",imout);
    
    edgePreservingFilter(img, imout,NORMCONV_FILTER);
    imwrite("utils/edgePreservingFilter-2.jpg",imout);
    //Detail enhance filter
    detailEnhance(img, imout);
    imwrite("utils/detailEnhance.jpg",imout);
    
    //Pencil sketch filter
    pencilSketch(img, imout_gray,imout);
    imwrite("utils/pencilSketch.jpg",imout_gray);
    
    stylization(img, imout);
    imwrite("utils/Stylization.jpg",imout);
    
}
