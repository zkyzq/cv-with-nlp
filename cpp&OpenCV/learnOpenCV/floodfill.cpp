#include<iostream>
#include<opencv2/opencv.hpp>

using namespace std;
using namespace cv;

int main()
{
	//灰度读取图片
	Mat im_in = imread("F:/LearnOpenCV/nickel.jpg",-1);
	Mat im_th;
	//将灰度值小于220的设为255.大于220的设为0
	threshold(im_in, im_th, 220, 255, THRESH_BINARY_INV);

	//从点(0,0)开始填充
	Mat im_floodfill = im_th.clone();
	floodFill(im_floodfill, cv::Point(0, 0), Scalar(255));
	// 将填充的图像取反
	Mat im_floodfill_inv;
	bitwise_not(im_floodfill,im_floodfill_inv);

	//将两个图像相或得到前景
	Mat im_out = (im_th | im_floodfill_inv);

	//显示图像
	imshow("Threshold Image",im_th);
	imshow("Floodfill Image",im_floodfill);
	imshow("Inverted Floodfilled Image",im_floodfill_inv);
	imshow("Foreground",im_out);
	waitKey(0);
	return 0;
}
