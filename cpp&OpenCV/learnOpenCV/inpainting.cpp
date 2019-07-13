#include "opencv2/imgcodecs.hpp"
#include "opencv2/highgui.hpp"
#include "opencv2/imgproc.hpp"
#include "opencv2/photo.hpp"

#include <iostream>

using namespace cv;
using namespace std;

// Declare Mat objects for original image and mask for inpainting
Mat img, inpaintMask;
// Mat object for result output
Mat res;
Point prevPt(-1,-1);

// onMouse function for Mouse Handling
// Used to draw regions required to inpaint
static void onMouse( int event, int x, int y, int flags, void* )
{
    if( event == EVENT_LBUTTONUP || !(flags & EVENT_FLAG_LBUTTON) )
        prevPt = Point(-1,-1);
    else if( event == EVENT_LBUTTONDOWN )
        prevPt = Point(x,y);
    else if( event == EVENT_MOUSEMOVE && (flags & EVENT_FLAG_LBUTTON) )
    {
        Point pt(x,y);
        if( prevPt.x < 0 )
            prevPt = pt;
        line( inpaintMask, prevPt, pt, Scalar::all(255), 5, 8, 0 );
        line( img, prevPt, pt, Scalar::all(255), 5, 8, 0 );
        prevPt = pt;
        imshow("image", img);
        imshow("image: mask", inpaintMask);
    }
}


int main( int argc, char** argv )
{
    cout << "Usage: ./inpaint <image_path>" << endl;
    cout << "Keys: " << endl;
    cout << "t - inpaint using FMM" << endl;
    cout << "n - inpaint using NS technique" << endl;
    cout << "r - reset the inpainting mask" << endl;
    cout << "ESC - exit" << endl;
    
    string filename;
    if(argc > 1)
        filename = argv[1];
    else
        filename = "/Users/tangxi/cpp/learnOpenCV/sample.jpeg";
    
    // 彩色模式读入
    img = imread(filename, IMREAD_COLOR);
    Mat img_mask;
    // 无论何时一定记得检查图像是否读入正确
    if(img.empty())
    {
        cout << "Failed to load image: " << filename << endl;
        return 0;
    }
    
    namedWindow("image", WINDOW_AUTOSIZE);
    
    // 深拷贝原始图像
    img_mask = img.clone();
    // 给inpaitingMask初始化为零
    inpaintMask = Mat::zeros(img_mask.size(), CV_8U);
    
    // 显示原始图像和inpaintingMask
    imshow("image", img);
    setMouseCallback( "image", onMouse, NULL);
    
    for(;;)
    {
        char c = (char)waitKey();
        if (c == 't') {
            // 使用Alexendra Telea提出的FMM算法
            inpaint(img, inpaintMask, res, 3, INPAINT_TELEA);
            imshow("Inpaint Output using FMM", res);
        }
        if (c == 'n') {
            // 使用Bertalmio et. al.提出的NS算法
            inpaint(img, inpaintMask, res, 3, INPAINT_NS);
            imshow("Inpaint Output using NS Technique", res);
        }
        //重置选取的inpaiting位置
        if (c == 'r')
        {
            inpaintMask = Scalar::all(0);
            img_mask.copyTo(img);
            imshow("image", inpaintMask);
            imshow("image",img_mask);
        }
        if ( c == 27 )
            break;
    }
    return 0;
}
