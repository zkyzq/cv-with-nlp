
#include <stdio.h>
#include <opencv2/opencv.hpp>

using namespace cv;
using namespace std;

vector<string>filenames = {"K0.png","S0.png","S1.png","S2.png","S3.png","S4.png","S5.png"};
int main()
{
    bool showLogTransformedMoments = true;
    for(int i = 0;i<filenames.size();i++)
    {
        string filename ="utils/" + filenames[i];
        Mat im = imread(filename,IMREAD_GRAYSCALE);
        cout<<im.size()<<endl;
        threshold(im,im,128,255,THRESH_BINARY);
        Moments moment = moments(im,false);
        double huMoments[7];
        HuMoments(moment, huMoments);
        cout<<filename<<":";
        for(int i = 0;i<7;i++)
        {
            if(showLogTransformedMoments)
            {
                cout<<-1*copysign(1.0, huMoments[i]*log10(abs(huMoments[i])))<<" ";
            }
            else
            {
                cout<<huMoments[i]<<" ";
            }
        }
        cout<<endl;
    }
    
    Mat im1 = imread("utils/S0.png",0);
    Mat im2 = imread("utils/S1.png",0);
    Mat im3 = imread("utils/S2.png",0);
    
    double m1 = matchShapes(im1, im1, CONTOURS_MATCH_I2, 0);
    double m2 = matchShapes(im1, im2, CONTOURS_MATCH_I2, 0);
    double m3 = matchShapes(im1, im3, CONTOURS_MATCH_I2, 0);
    
    cout << "Shape Distances Between " << endl << "-------------------------" << endl;
    cout << "S0.png and S0.png : " << m1 << endl;
    cout << "S0.png and S1.png : " << m2 << endl;
    cout << "S0.png and S4.png : " << m3 << endl;
}
