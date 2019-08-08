//
//  exposureFusion.cpp
//  learnOpenCV
//
//  Created by tangxi on 2019/8/8.
//  Copyright © 2019年 tangxi. All rights reserved.
//

#include <opencv2/photo.hpp>
#include "opencv2/imgcodecs.hpp"
#include <opencv2/highgui.hpp>
#include <vector>
#include <iostream>
#include <fstream>
using namespace cv;
using namespace std;

// Read Images
void readImages(vector<Mat> &images)
{
    
    int numImages = 16;
    static const char* filenames[] =
    {
        "learnOpenCV/images/memorial0061.jpg",
        "learnOpenCV/images/memorial0062.jpg",
        "learnOpenCV/images/memorial0063.jpg",
        "learnOpenCV/images/memorial0064.jpg",
        "learnOpenCV/images/memorial0065.jpg",
        "learnOpenCV/images/memorial0066.jpg",
        "learnOpenCV/images/memorial0067.jpg",
        "learnOpenCV/images/memorial0068.jpg",
        "learnOpenCV/images/memorial0069.jpg",
        "learnOpenCV/images/memorial0070.jpg",
        "learnOpenCV/images/memorial0071.jpg",
        "learnOpenCV/images/memorial0072.jpg",
        "learnOpenCV/images/memorial0073.jpg",
        "learnOpenCV/images/memorial0074.jpg",
        "learnOpenCV/images/memorial0075.jpg",
        "learnOpenCV/images/memorial0076.jpg"
    };
    
    for(int i=0; i < numImages; i++)
    {
        Mat im = imread(filenames[i]);
        images.push_back(im);
    }
    
}

int main()
{
    // Read images
    cout << "Reading images ... " << endl;
    vector<Mat> images;
    
    bool needsAlignment = true;

    readImages(images);

    // Align input images
    if(needsAlignment)
    {
        cout << "Aligning images ... " << endl;
        Ptr<AlignMTB> alignMTB = createAlignMTB();
        alignMTB->process(images, images);
    }
    else
    {
        cout << "Skipping alignment ... " << endl;
    }
    
    
    // Merge using Exposure Fusion
    cout << "Merging using Exposure Fusion ... " << endl;
    Mat exposureFusion;
    Ptr<MergeMertens> mergeMertens = createMergeMertens();
    mergeMertens->process(images, exposureFusion);
    
    // Save output image
    cout << "Saving output ... exposure-fusion.jpg"<< endl;
    imwrite("exposure-fusion.jpg", exposureFusion * 255);
    
    return EXIT_SUCCESS;
}
