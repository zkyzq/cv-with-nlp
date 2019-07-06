//
//  main.cpp
//  ZREO: JPEG Grid Detection based on the Number of DCT Zeros
//and its Application to Automatic and Localized Forgery Detection

//  论文网址：http://openaccess.thecvf.com/content_CVPRW_2019/papers/Media%20Forensics/Nikoukhah_JPEG_Grid_Detection_based_on_the_Number_of_DCT_Zeros_CVPRW_2019_paper.pdf
//
//  Created by tangxi on 2019/7/4.
//  Copyright © 2019年 tangxi. All rights reserved.
//

#include <iostream>
#include <opencv2/opencv.hpp>
#include <opencv2/core/core.hpp>
#include <opencv2/imgproc/imgproc.hpp>

using namespace std;
using namespace cv;


# define PI 3.1415926

//计算网格投票
Mat compute_grid_votes_per_pixel(Mat double_img,int X,int Y,string filename)
{
    Mat votes = Mat(X,Y,CV_32S);
    Mat zeros = Mat(X,Y,CV_32S);
    double cos_t[8][8];
    for(int k = 0;k <8 ;k++)
    {
        for(int l = 0;l<8;l++)
        {
            cos_t[k][l] = cos( (2.0 * k + 1.0) * l * PI / 16.0 );
        }
    }
    for(int x = 0; x< X-7 ; x++)
    {
        for(int y = 0 ;y< Y -7 ;y++)
        {
            //每一个像素都有对应的一个最多的0的个数
            int z = 0;
            for(int i = 0 ; i < 8 ; i++)
            {
                for(int j = 0 ; j < 8 ; j++)
                {
                    if( i > 0 || j > 0)
                    {
                        double dct_ij = 0.0;
                        for(int xx = 0; xx < 8 ; xx++)
                        {
                            for(int yy = 0; yy < 8 ; yy++)
                            {
                                dct_ij += double_img.at<double>(xx,yy) * cos_t[xx][i] * cos_t[yy][j];
                            }
                        }
                        dct_ij *= 0.25 * ( i==0 ? 1.0/sqrt(2.0) : 1.0 )* ( j==0 ? 1.0/sqrt(2.0) : 1.0 );
                        
                        if(abs(dct_ij) < 0.5) ++z;
                    }
                }
            }
            
            for (int xx = x; xx < x + 8;xx++)
            {
                for(int yy = y ; yy < y; yy++)
                {
                    if(z == zeros.at<int>(xx,yy))
                    {
                        votes.at<int>(xx,yy) = -1;
                    }
                    
                    if(z > zeros.at<int>(xx,yy))
                    {
                        zeros.at<int>(xx,yy) = z;
                        votes.at<int>(xx,yy) = (x % 8) + (y % 8) * 8;
                    }
                }
            }
        }
    }
    string output_filename = filename + "votes.png";
    imwrite(output_filename,votes);
    return votes;
}

#define TABSIZE 100000
double log_nfa(int n, int k, double p, double logNT)
{
    static double inv[TABSIZE];   /* table to keep computed inverse values */
    double tolerance = 0.1;       /* an error of 10% in the result is accepted */
    double log1term,term,bin_term,mult_term,bin_tail,err;
    double p_term = p / (1.0-p);
    int i;
    
    if( n<0 || k<0 || k>n || p<0.0 || p>1.0 )
        throw ("wrong n, k or p values in nfa()");
    
    if( n==0 || k==0 )
        return logNT;
    if( n==k )
        return logNT + (double)n * log10(p);
    
    log1term = lgamma((double)n+1.0) - lgamma((double)k+1.0)- lgamma((double)(n-k)+1.0) + (double)k * log(p) + (double)(n-k) * log(1.0-p);
    
    term = exp(log1term);
    if( term == 0.0 )                        /* the first term is almost zero */
    {
        if( (double)k > (double)n * p )      /* at begining or end of the tail? */
            return log1term / M_LN10 + logNT;  /* end: use just the first term */
        else
            return logNT;                      /* begin: the tail is roughly 1 */
    }
    
    bin_tail = term;
    for(i=k+1;i<=n;i++)
    {
        bin_term = (double)(n-i+1) * ( i<TABSIZE ?
                                      (inv[i] ? inv[i] : (inv[i]=1.0/(double)i)) : 1.0/(double)i );
        mult_term = bin_term * p_term;
        term *= mult_term;
        bin_tail += term;
        if(bin_term<1.0)
        {
            /* when bin_term<1 then mult_term_j<mult_term_i for j>i.
             then, the error on the binomial tail when truncated at
             the i term can be bounded by a geometric serie of form
             term_i * sum mult_term_i^j.                            */
            err = term * ( ( 1.0 - pow( mult_term, (double)(n-i+1) ) ) /
                          (1.0-mult_term) - 1.0 );
            
            /* one wants an error at most of tolerance*final_result, or:
             tolerance * abs(-log10(bin_tail)-logNT).
             now, the error that can be accepted on bin_tail is
             given by tolerance*final_result divided by the derivative
             of -log10(x) when x=bin_tail. that is:
             tolerance * abs(-log10(bin_tail)-logNT) / (1/bin_tail)
             finally, we truncate the tail if the error is less than:
             tolerance * abs(-log10(bin_tail)-logNT) * bin_tail        */
            if( err < tolerance * fabs(-log10(bin_tail)-logNT) * bin_tail ) break;
        }
    }
    return log10(bin_tail) + logNT;
}

int detect_main_grid(Mat votes, int X, int Y)
{
    double logNT = log10(64.0) + 1.5 * log10(X) + 1.5 * log10(Y);
    int grid_votes[64] = {0};
    
    int max_votes = 0;
    int most_voted_grid = -1;
    double p = 1.0 / 64.0;
    double lnfa;
    
    for(int x = 0; x < X; x++)
    {
        for(int y = 0; y< Y; y++)
        {
            if(votes.at<int>(x,y) >= 0 && votes.at<int>(x,y) < 64)
            {
                int grid = votes.at<int>(x,y);
                ++grid_votes[grid];
                if(grid_votes[grid] > max_votes)
                {
                    max_votes =  grid_votes[grid];
                    most_voted_grid = grid;
                }
            }
        }
    }
    int n = X * Y/64;
    int k = grid_votes[most_voted_grid] / 64;
    lnfa = log_nfa(n,k,p,logNT);
    
    if( lnfa < 0.0 )
    {
        printf("main grid: #%d [%d %d] log(nfa) = %g\n", most_voted_grid,
               most_voted_grid % 8, most_voted_grid / 8, lnfa);
        return most_voted_grid;
    }
    
    /* main grid not found */
    printf("no overall JPEG grid found\n");
    return -1;
}

void detect_forgery(Mat votes, int X, int Y, int main_grid, string filename)
{
    double logNT = log10(64.0) + 1.5*log10(X) + 1.5 *log10(Y);
    double p = 1.0 / 64.0;
    Mat forgery = Mat(X,Y,CV_32S);
    Mat forgery_d = Mat(X,Y,CV_32S);
    Mat forgery_e = Mat(X,Y,CV_32S);
    
    Mat used = Mat(X,Y,CV_32S);
    Mat reg_x = Mat(X,Y,CV_32S);
    Mat reg_y = Mat(X,Y,CV_32S);
    
    int W = 12;
    int min_size = ceil(64.0 * logNT / log10(64.0));
    
    for(int x = 0; x < X ; x++)
    {
        for(int y = 0;y< Y ; y++)
        {
            if(used.at<int>(x,y) ==  false && votes.at<int>(x,y) != main_grid && votes.at<int>(x,y) >= 0)
            {
                int reg_size = 0;
                int grid = votes.at<int>(x,y);
                int x0 = x;
                int y0 = y;
                int x1 = x;
                int y1 = y;
                used.at<int>(x,y) = true;
                reg_x.data[reg_size] = x;
                reg_y.data[reg_size] = y;
                ++reg_size;
                
                for(int i = 0;i<reg_size;i++)
                {
                    for(int xx = reg_x.data[i]-W ; xx<=reg_x.data[i]+W ; xx++)
                    {
                        for(int yy = reg_y.data[i]-W ; yy<=reg_y.data[i]+W ; yy++)
                        {
                            if(used.at<int>(xx,yy) == false && votes.at<int>(xx,yy) == grid)
                            {
                                used.at<int>(xx,yy) = true;
                                reg_x.data[reg_size] = xx;
                                reg_y.data[reg_size] = yy;
                                ++reg_size;
                                if(xx < x0) x0 = xx;
                                if(yy < y0) y0 = yy;
                                if(xx < x1) x1 = xx;
                                if(yy < y1) y1 = yy;
                            }
                        }
                    }
                }
                //计算最小size区域的NFA
                if(reg_size >= min_size)
                {
                    int N = MAX(x1 - x0 +1, y1 - y0 + 1);
                    int n = N * N / 64;
                    int k = reg_size / 64;
                    double lnfa = log_nfa(n,k,p,logNT);
                    if(lnfa < 0.0)
                    {
                        printf("forgery found: %d %d - %d %d [%dx%d] ",
                               x0,y0,x1,y1,x1-x0+1,y1-y0+1);//分别为区域左上角和右下角坐标，以及区域长和宽
                        printf("grid: #%d [%d %d] ", grid, grid % 8, grid / 8);
                        printf("n %d k %d log(nfa) = %g\n",n,k,lnfa);
                        
                        for(int i = 0; i<reg_size;i++)
                        {
                            forgery.at<int>(reg_x.data[i],reg_y.data[i]) = 255;
                        }
                    }
                }
                
                ///* morphologic closing of forgery mask */
                for(int x = W ; x< X - W;x++)
                {
                    for(int y = W ; y<Y-W;y++)
                    {
                        if(forgery.at<int>(x,y) != 0)
                        {
                            for(int xx = x- W; xx <= x + W; xx++)
                            {
                                for(int yy = y - W; yy <= y + W;yy++)
                                {
                                    forgery_e.at<int>(xx,yy) = forgery_d.at<int>(xx,yy) = 255;
                                }
                            }
                        }
                    }
                }
                
                for(int x = W ; x< X - W;x++)
                {
                    for(int y = W ; y<Y-W;y++)
                    {
                        if(forgery.at<int>(x,y) == 0)
                        {
                            for(int xx = x- W; xx <= x + W; xx++)
                            {
                                for(int yy = y - W; yy <= y + W;yy++)
                                {
                                    forgery_e.at<int>(xx,yy) =  0;
                                }
                            }
                        }
                    }
                }
                
                
            }
        }
    }
    
}
int main(int argc, const char * argv[])
{
    // insert code here...
    string path ="/Users/tangxi/Desktop/code_check/0046_4cf95672-edcd-4a85-aba0-854d4e3029bb.jpg";
    Mat img = imread(path);
    Mat YCrCb;
    //转到YCrCb空间
    cvtColor(img, YCrCb, COLOR_BGR2YCrCb);
    //只取Y通道
    std::vector<Mat> channels;
    split(YCrCb, channels);
    Mat Y = channels[0];
    
    //detect grid
    int rows = Y.rows;
    int cols = Y.cols;
    string filename = path.substr(path.find("00"), path.length()-1);
    //计算投票
    Mat votes = compute_grid_votes_per_pixel(Y, cols, rows, filename);
    //计算jpeg网格
    int main_grid = detect_main_grid(votes, cols, rows);
    //检测篡改区域
    detect_forgery(votes, cols, rows, main_grid, filename);
    
    std::cout<< img.size()<<endl;
//    imshow("test",img);
//    waitKey(0);
    std::cout << "Hello, World!\n";
    
    return 0;
}
