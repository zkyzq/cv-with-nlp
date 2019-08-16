#-----------------------------------------------------
#@authur = zhangqizky
#@time   = 20190816
#-----------------------------------------------------

import cv2
import  os
import argparse
from collections import deque

def main():
    cap  = cv2.VideoCapture(0)
    //用于存手指点
    pts = deque(maxlen = 64)

    //用于分割手部区域，肤色的颜色分量在hsv空间的范围
    lower_green = np.array([110,50,50])
    upper_green = np.array([130,255,255])

    while True:
        ret,img = cap.read()
        //转HSV空间
        hsv = cv2.cvtColor(img,cv2.COLOR_BGR2HSV)
        kernel = np.ones((5,5),np.uint8)
        // 分割得到手部区域mask
        mask = cv2.inRange(hsv,lower_green,upper_green)
        //腐蚀，腐蚀的力度比较大，可以去除鼓励区域的噪声点
        mask = cv2.erode(mask,kernel,iterations = 2)
        //形态学开操作：先腐蚀再膨胀，可以去除小白点
        mask = cv2.morphologyEx(mask,cv2.MORPH_OPEN,kernel)
        //先膨胀再腐蚀，可以去除小黑点，即填充小洞
        mask = cv2.morphologyEx(mask,cv2.MORPH_CLOSE,kernel)
        //膨胀：修复边界，经过腐蚀的联通区域边界内缩，通过此操作恢复
        mask = cv2.dilate(mask,kernel,iterations = 1)
        //得到手部区域的掩膜
        res = cv2.bitewise_and(img,img,mask=mask)
        //在手部区域mask上寻找轮廓，cnt是代表找到的轮廓的个数
        cnts,heir = cv2.findContours(mask.copy(),cv2.RETR_EXTERNAL,cv2.CHAIN_APPROX_SIMPLE[-2:])

        center = None
        //如果找到轮廓
        if len(cnts) > 0:
            //算轮廓的面积,并得到最大的轮廓
            c = max(cnts,key=cv2.contourArea)
            //计算最大轮廓的最小内接圆
            ((x,y),radius) = cv2.minEnclosingCircle(c)
            //算这个区域的矩
            M = cv2.moments(c)
            //矩中心
            center = (int(M["m10"] / M["m00"]),int(M["m01"]/ M["m00"]))

            if radius > 5:
                cv2.circle(img,(int(x),int(y)),int(radius),(0,255,255),2)
                cv2.circle(img,center,5,(0,0,5),-1)
            //把这个手部的中心push到队列中去
            pts.appendleft(center)
            //用于跟踪手部中心的轨迹，必须有两帧存在手，才会去画这个痕迹
            for i in xrange(1,len(pts)):

                if pts[i-1] is None or pts[i] is None:
                    continue
                //画两帧之间手部中心的连接线，这个线的粗细与当前的帧数有关
                thick = int(np.sqrt(len(pts) / float(i+1))*2.5)
                cv2.line(img,pts[i-1],pts[i],(0,0,255),thick)

            cv2.imshow("Frame",img)
            cv2.imshow("mask",mask)
            cv2.imshow("res",res)

            k = cv2.waitKey(30) & 0xFF
            if k ==32:
                break

    cv2.release()
    cv2.destoryAllWindows()

    if __name__ == '__main__':
        main()
        

