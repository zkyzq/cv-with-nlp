#include<iostream>
#include<opencv2/opencv.hpp>
#include<opencv2/highgui/highgui.hpp>
#include<opencv2/core/core.hpp>
#include<string>

using namespace std;
using namespace cv;

//����ȫ�ֱ�������Ϊ��creatTrackbar�Ĳ�����������Ҫ����ȫ�ֱ���
Mat dst, gray, cimg, img, edges;

int initThresh;
const int maxThresh_circle = 200;
const int maxThresh_lines = 1000;
double p1, p2;
double th1, th2;
vector<Vec3f>circles;
vector<Vec4i>lines;

void onTrackChange(int, void*)
{
	cimg = img.clone();
	dst = img.clone();
	p1 = initThresh;
	p2 = initThresh * 0.4;
	//���´���Ϊ���houghֱ��
	th1 = initThresh;
	th2 = initThresh*0.4;
	Canny(img, edges, th1, th2);
	HoughLinesP(edges, lines, 2, CV_PI / 180, 50, 10, 100);
	for (size_t i = 0; i < lines.size(); i++)
	{
		line(dst, Point(lines[i][0], lines[i][1]), Point(lines[i][2], lines[i][3]), Scalar(0, 255, 0),2);
	}
	imshow("Result Image",dst);
	imshow("Edges", edges);
	//���´���Ϊ���houghԲ
	//HoughCircles(gray, circles, CV_HOUGH_GRADIENT, 1, cimg.rows / 64, p1, p2, 25, 50);
	//imshow("gray", gray);
	////circle��һ�������������Ľṹ��ǰ����ΪԲ�����ģ����һ��ΪԲ�İ뾶
	//for (size_t i = 0; i < circles.size(); i++)
	//{
	//	Point center(cvRound(circles[i][0]), cvRound(circles[i][1]));
	//	int radius = cvRound(circles[i][2]);
	//	circle(cimg, center, radius, Scalar(0, 255, 0), 2);
	//	circle(cimg, center, 2, Scalar(0, 0, 255), 3);
	//}
	//imshow("Image",cimg);
	//imwrite("result.jpg", cimg);
	//Canny(gray, edges, p1, p2);
	//imshow("Edges", edges);
}
int main()
{
	const string filename = "lanes.jpg";
	img = imread(filename);
	dst = img.clone();
	if (img.empty())
	{
		cout << "Error reading image" << filename << endl;
		return -1;
	}
	cvtColor(img, gray, COLOR_BGR2GRAY);
	namedWindow("Edges", 1);
	namedWindow("Result Image", 1);
	initThresh = 500;
	//createTrackbar("Threshold", "Image", &initThresh, maxThresh_circle, onTrackChange);
	createTrackbar("threshold", "Result Image", &initThresh, maxThresh_lines, onTrackChange);
	onTrackChange(initThresh, 0);
	//imshow("Image", img);

	while (true)
	{
		if ((char)waitKey(0) == 27)
		{
			break;
		}
	}
	destroyAllWindows();
	return 0;
}
