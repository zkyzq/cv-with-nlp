//
//  colorizedVideo.cpp
//  opencvtest
//
//  Created by tangxi on 2019/7/6.
//  Copyright © 2019年 tangxi. All rights reserved.
//

#include <stdio.h>
#include <iostream>
#include <opencv2/dnn/dnn.hpp>
#include <opencv2/video/video.hpp>
#include <opencv2/highgui/highgui.hpp>

using namespace std;
using namespace cv;
using namespace cv::dnn;


// the 313 ab cluster centers from pts_in_hull.npy (already transposed)
static float hull_pts[] = {
    -90., -90., -90., -90., -90., -80., -80., -80., -80., -80., -80., -80., -80., -70., -70., -70., -70., -70., -70., -70., -70.,
    -70., -70., -60., -60., -60., -60., -60., -60., -60., -60., -60., -60., -60., -60., -50., -50., -50., -50., -50., -50., -50., -50.,
    -50., -50., -50., -50., -50., -50., -40., -40., -40., -40., -40., -40., -40., -40., -40., -40., -40., -40., -40., -40., -40., -30.,
    -30., -30., -30., -30., -30., -30., -30., -30., -30., -30., -30., -30., -30., -30., -30., -20., -20., -20., -20., -20., -20., -20.,
    -20., -20., -20., -20., -20., -20., -20., -20., -20., -10., -10., -10., -10., -10., -10., -10., -10., -10., -10., -10., -10., -10.,
    -10., -10., -10., -10., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 10., 10., 10., 10., 10., 10., 10.,
    10., 10., 10., 10., 10., 10., 10., 10., 10., 10., 10., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20.,
    20., 20., 20., 30., 30., 30., 30., 30., 30., 30., 30., 30., 30., 30., 30., 30., 30., 30., 30., 30., 30., 30., 40., 40., 40., 40.,
    40., 40., 40., 40., 40., 40., 40., 40., 40., 40., 40., 40., 40., 40., 40., 40., 50., 50., 50., 50., 50., 50., 50., 50., 50., 50.,
    50., 50., 50., 50., 50., 50., 50., 50., 50., 60., 60., 60., 60., 60., 60., 60., 60., 60., 60., 60., 60., 60., 60., 60., 60., 60.,
    60., 60., 60., 70., 70., 70., 70., 70., 70., 70., 70., 70., 70., 70., 70., 70., 70., 70., 70., 70., 70., 70., 70., 80., 80., 80.,
    80., 80., 80., 80., 80., 80., 80., 80., 80., 80., 80., 80., 80., 80., 80., 80., 90., 90., 90., 90., 90., 90., 90., 90., 90., 90.,
    90., 90., 90., 90., 90., 90., 90., 90., 90., 100., 100., 100., 100., 100., 100., 100., 100., 100., 100., 50., 60., 70., 80., 90.,
    20., 30., 40., 50., 60., 70., 80., 90., 0., 10., 20., 30., 40., 50., 60., 70., 80., 90., -20., -10., 0., 10., 20., 30., 40., 50.,
    60., 70., 80., 90., -30., -20., -10., 0., 10., 20., 30., 40., 50., 60., 70., 80., 90., 100., -40., -30., -20., -10., 0., 10., 20.,
    30., 40., 50., 60., 70., 80., 90., 100., -50., -40., -30., -20., -10., 0., 10., 20., 30., 40., 50., 60., 70., 80., 90., 100., -50.,
    -40., -30., -20., -10., 0., 10., 20., 30., 40., 50., 60., 70., 80., 90., 100., -60., -50., -40., -30., -20., -10., 0., 10., 20.,
    30., 40., 50., 60., 70., 80., 90., 100., -70., -60., -50., -40., -30., -20., -10., 0., 10., 20., 30., 40., 50., 60., 70., 80., 90.,
    100., -80., -70., -60., -50., -40., -30., -20., -10., 0., 10., 20., 30., 40., 50., 60., 70., 80., 90., -80., -70., -60., -50.,
    -40., -30., -20., -10., 0., 10., 20., 30., 40., 50., 60., 70., 80., 90., -90., -80., -70., -60., -50., -40., -30., -20., -10.,
    0., 10., 20., 30., 40., 50., 60., 70., 80., 90., -100., -90., -80., -70., -60., -50., -40., -30., -20., -10., 0., 10., 20., 30.,
    40., 50., 60., 70., 80., 90., -100., -90., -80., -70., -60., -50., -40., -30., -20., -10., 0., 10., 20., 30., 40., 50., 60., 70.,
    80., -110., -100., -90., -80., -70., -60., -50., -40., -30., -20., -10., 0., 10., 20., 30., 40., 50., 60., 70., 80., -110., -100.,
    -90., -80., -70., -60., -50., -40., -30., -20., -10., 0., 10., 20., 30., 40., 50., 60., 70., 80., -110., -100., -90., -80., -70.,
    -60., -50., -40., -30., -20., -10., 0., 10., 20., 30., 40., 50., 60., 70., -110., -100., -90., -80., -70., -60., -50., -40., -30.,
    -20., -10., 0., 10., 20., 30., 40., 50., 60., 70., -90., -80., -70., -60., -50., -40., -30., -20., -10., 0.
};

void colorizedFrame(Mat frame,int index)
{
    Mat img = frame.clone();
    string protoFile = "./models/colorization_deploy_v2.prototxt";
    string weightsFile = "./models/colorization_release_v2.caffemodel";
    
    double t = (double) getTickCount();
    
    const int w = 224;
    const int h = 224;
    
    Net net = dnn::readNetFromCaffe(protoFile,weightsFile);
    
    int sz[] = {2,313,1,1};
    const Mat pts_in_hull(4,sz,CV_32F,hull_pts);
    Ptr<dnn::Layer> class8_ab = net.getLayer("class8_ab");
    class8_ab->blobs.push_back(pts_in_hull);
    Ptr<dnn::Layer> conv8_313_rh = net.getLayer("conv8_313_rh");
    conv8_313_rh->blobs.push_back(Mat(1,313,CV_32F,Scalar(2.606)));
    
    Mat lab,L,input;
    img.convertTo(img,CV_32F,1.0/255);
    cvtColor(img, lab, COLOR_BGR2Lab);
    extractChannel(lab, L, 0);
    
    resize(L,input,Size(w,h));
    
    input -= 50;
    
    //开始转换
    Mat inputBlob = blobFromImage(input);
    net.setInput(inputBlob);
    Mat res = net.forward();
    
    Size siz(res.size[2],res.size[3]);
    Mat a = Mat(siz,CV_32F,res.ptr(0,0));
    Mat b = Mat(siz,CV_32F,res.ptr(0,1));
    resize(a, a, img.size());
    resize(b, b, img.size());
    
    Mat color,chn[] = {L,a,b};
    merge(chn,3,lab);
    cvtColor(lab, color, COLOR_Lab2BGR);
    
    t = ((double)getTickCount() - t) / getTickFrequency();
    
    cout<< "Time taken :" << t << "secs"<<endl;
    string output_name = to_string(index);
    output_name = output_name + "_colorized.png";
    
    color = color * 255;
    color.convertTo(color, CV_8U);
    imwrite(output_name, color);
    
    cout<< "Colorized image saved as" << output_name <<endl;
    imshow("gray",img);
    imshow("color",color);
    waitKey(0);
}

void colorizedVideo(string video_filename)
{
    VideoCapture cap(video_filename);
    if (!cap.isOpened())
    {
        cerr<< "can not open this video..."<<endl;
    }
    Mat frame;
    int index = 0;
    while (true)
    {
        cap >> frame;
        if(frame.empty())
        {
            break;
        }
        colorizedFrame(frame, index);
    }
}

