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
int main()
{
    string img_filename = "test.jpg";
    colorMap(img_filename);
    return 0;
}
