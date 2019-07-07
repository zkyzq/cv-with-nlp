#include <iostream>
#include <opencv2/opencv.hpp>
#include <opencv2/core/core.hpp>
#include <opencv2/imgproc/imgproc.hpp>
using namespace std;
using namespace cv;

string colormap_name(int id)
{
    switch(id)
    {
        case COLORMAP_AUTUMN :
            return "COLORMAP_AUTUMN";
        case COLORMAP_BONE :
            return "COLORMAP_BONE";
        case COLORMAP_JET :
            return "COLORMAP_JET";
        case COLORMAP_WINTER :
            return "COLORMAP_WINTER";
        case COLORMAP_RAINBOW :
            return "COLORMAP_RAINBOW";
        case COLORMAP_OCEAN :
            return "COLORMAP_OCEAN";
        case COLORMAP_SUMMER:
            return "COLORMAP_SUMMER";
        case COLORMAP_SPRING :
            return "COLORMAP_SPRING";
        case COLORMAP_COOL :
            return "COLORMAP_COOL";
        case COLORMAP_HSV :
            return "COLORMAP_HSV";
        case COLORMAP_PINK :
            return "COLORMAP_PINK";
        case COLORMAP_HOT :
            return "COLORMAP_HOT";
            
    }
    
    return "NONE";
}

void colorMap(string img_name)
{
    Mat img = imread(img_name);
    Mat img_out = Mat::zeros(600,800,CV_8UC3);
    for (int i = 0;i < 4; i++)
    {
        for(int j = 0 ;j <3;j++ )
        {
            int k = i+j*4;
            Mat img_color = img_out(Rect(i*200,j*200,200,200));
            applyColorMap(img,img_color,k);
            putText(img_color, colormap_name(k), Point(30,180), 3, 0.5, Scalar::all(255));
        }
    }
    imshow("Presucolor",img_out);
    waitKey(0);
}
#include "opencv2/highgui/highgui.hpp"
#include "opencv2/imgproc/imgproc.hpp"
#include <iostream>
#include <cstdlib>

using namespace cv;
using namespace std;

int main() {
  string image_path = "test.jpg"; // 输入图像路径

  // 定义变量
  Mat src, gray, blur_image, threshold_output;

  // 读取输入图像
  src = imread(image_path, 1);
  
 // 转为灰度空间
  cvtColor(src, gray, COLOR_BGR2GRAY);
  
  // 模糊操作
  blur(gray, blur_image, Size(3, 3));
  
  // 阈值
  threshold(gray, threshold_output, 200, 255, THRESH_BINARY);
  
  // 显示
  namedWindow("Source", WINDOW_AUTOSIZE);
  imshow("Source", src);

  //凸包计算
  Mat src_copy = src.clone();
  
  // 找轮廓，每个轮廓是一系列的点，有很多轮廓
  vector< vector<Point> > contours;
  vector<Vec4i> hierarchy;
  
  // 找轮廓
  findContours(threshold_output, contours, hierarchy, RETR_TREE, 
      CHAIN_APPROX_SIMPLE, Point(0, 0));
  
  // 定义凸包向量
  vector< vector<Point> > hull(contours.size());

  // 对每个轮廓判断其是否为凸包
  for(int i = 0; i < contours.size(); i++)
    convexHull(Mat(contours[i]), hull[i], false);
  
  // 定义一个全黑的图像
  Mat drawing = Mat::zeros(threshold_output.size(), CV_8UC3);
  
  //画轮廓和凸包
  for(int i = 0; i < contours.size(); i++) {
    Scalar color_contours = Scalar(0, 255, 0); // color for contours : blue
    Scalar color = Scalar(255, 255, 255); // color for convex hull : white
    drawContours(drawing, contours, i, color_contours, 2, 8, vector<Vec4i>(), 0, Point());
    drawContours(drawing, hull, i, color, 2, 8, vector<Vec4i>(), 0, Point());
  }

  namedWindow("Output", WINDOW_AUTOSIZE);
  imshow("Output", drawing);

  waitKey(0);
  return 0;
}
