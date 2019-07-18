//
//  MultiObjectTracker.cpp
//  learnOpenCV
//
//  Created by tangxi on 2019/7/18.
//  Copyright © 2019年 tangxi. All rights reserved.
//

#include <stdio.h>
#include<opencv2/opencv.hpp>
#include<opencv2/tracking/tracker.hpp>

using namespace cv;
using namespace std;

vector<string> trackerTypes = {"BOOSTING", "MIL", "KCF", "TLD", "MEDIANFLOW", "GOTURN", "MOSSE", "CSRT"};

Ptr<Tracker> createTrackerByName(string trackerType)
{
    Ptr<Tracker> tracker;
    if (trackerType ==  trackerTypes[0])
        tracker = TrackerBoosting::create();
    else if (trackerType == trackerTypes[1])
        tracker = TrackerMIL::create();
    else if (trackerType == trackerTypes[2])
        tracker = TrackerKCF::create();
    else if (trackerType == trackerTypes[3])
        tracker = TrackerTLD::create();
    else if (trackerType == trackerTypes[4])
        tracker = TrackerMedianFlow::create();
    else if (trackerType == trackerTypes[5])
        tracker = TrackerGOTURN::create();
    else if (trackerType == trackerTypes[6])
        tracker = TrackerMOSSE::create();
    else if (trackerType == trackerTypes[7])
        tracker = TrackerCSRT::create();
    else {
        cout << "Incorrect tracker name" << endl;
        cout << "Available trackers are: " << endl;
        for (vector<string>::iterator it = trackerTypes.begin() ; it != trackerTypes.end(); ++it)
            std::cout << " " << *it << endl;
    }
    return tracker;
}

//用随机的颜色来画这些跟踪的框
void getRandomColors(vector<Scalar> &color, int numColors)
{
    RNG rng(0);
    for(int i = 0; i<numColors;i++)
    {
        color.push_back(Scalar(rng.uniform(0,255),rng.uniform(0, 255),rng.uniform(0, 255)));
    }
    
}

int main()
{
    cout<< "Default tracker type is CSRT"<<endl;
    cout<< "Avilible tracker types are:";
    for (vector<string>::iterator it = trackerTypes.begin() ; it != trackerTypes.end(); ++it)
    {
        cout<<*it<<endl;
    }
    string trackerType = "CSRT";
    vector<Rect> bboxes;
    VideoCapture cap("/Users/tangxi/Desktop/run.mp4");
    if(!cap.isOpened())
    {
        cerr<<"cannot open video..."<<endl;
    }
    Mat frame;
    //读取第一帧
    cap>>frame;
    //画框的时候显示不显示交叉线
    bool showCrosshair = false;
    bool fromCenter = true;
    cout << "\n==========================================================\n";
    cout << "OpenCV says press c to cancel objects selection process" << endl;
    cout << "It doesn't work. Press Escape to exit selection process" << endl;
    cout << "\n==========================================================\n";
    //选择感兴趣区域
    cv::selectROIs("MultiTracker", frame, bboxes, showCrosshair, fromCenter);
    
    if(bboxes.size()<1)
        return 0;
    vector<Scalar>colors;
    getRandomColors(colors, bboxes.size());
    
    Ptr<MultiTracker> multiTracker = MultiTracker::create();
    //为每个目标添加跟踪器
    for(int i = 0; i<bboxes.size();i++)
    {
        multiTracker->add(createTrackerByName(trackerType),frame,Rect2d(bboxes[i]));
    }
    
    while(cap.isOpened())
    {
        cap>>frame;
        
        if(frame.empty())
            break;
        //跟踪
        multiTracker->update(frame);
        
        for(size_t i = 0; i<multiTracker->getObjects().size();i++)
        {
            rectangle(frame, multiTracker->getObjects()[i],  colors[i],2,1);
        }
        
        imshow("MultiTracker",frame);
        if(waitKey(1)==27)
            break;
    }
    return 0;
}


