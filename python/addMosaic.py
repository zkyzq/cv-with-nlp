import cv2
import os
import numpy as np
import random
def get_images(path):
    return [os.path.join(path,each) for each in os.listdir(path) if each.endswith('.jpg') or each.endswith('.png')]

def addHeart():
    pass

def do_mosaic(frame, x, y, w, h, neighbor=9):
    """
    马赛克的实现原理是把图像上某个像素点一定范围邻域内的所有点用邻域内左上像素点的颜色代替，这样可以模糊细节，但是可以保留大体的轮廓。
    :param frame: opencv frame
    :param int x :  马赛克左顶点
    :param int y:  马赛克右顶点
    :param int w:  马赛克宽
    :param int h:  马赛克高
    :param int neighbor:  马赛克每一块的宽
    """

    fh, fw = frame.shape[0], frame.shape[1]
    if (y + h > fh) or (x + w > fw):
        return
    for i in range(0, h - neighbor, neighbor):  # 关键点0 减去neightbour 防止溢出
        for j in range(0, w - neighbor, neighbor):
            rect = [j + x, i + y, neighbor, neighbor]
            color = frame[i + y][j + x].tolist()  # 关键点1 tolist
            left_up = (rect[0], rect[1])
            right_down = (rect[0] + neighbor - 1, rect[1] + neighbor - 1)  # 关键点2 减去一个像素
            cv2.rectangle(frame, left_up, right_down, color, -1)
    return frame

def bb_intersection_over_union(boxA, boxB):
    # determine the (x, y)-coordinates of the intersection rectangle
    xA = max(boxA[0], boxB[0])
    yA = max(boxA[1], boxB[1])
    xB = min(boxA[2], boxB[2])
    yB = min(boxA[3], boxB[3])
 
    # compute the area of intersection rectangle
    interArea = max(0, xB - xA + 1) * max(0, yB - yA + 1)
 
    # compute the area of both the prediction and ground-truth
    # rectangles
    boxAArea = (boxA[2] - boxA[0] + 1) * (boxA[3] - boxA[1] + 1)
    boxBArea = (boxB[2] - boxB[0] + 1) * (boxB[3] - boxB[1] + 1)
 
    # compute the intersection over union by taking the intersection
    # area and dividing it by the sum of prediction + ground-truth
    # areas - the interesection area
    iou = interArea / float(boxAArea + boxBArea - interArea)
 
    # return the intersection over union value
    return iou
def read_from_txt(txt_path):
    with open(txt_path) as f:
        for line in f:
            path, x, y, w, h = line.split(" ") 
            x_ = int(x)
            y_ = int(y)
            w_ = int(w)
            h_ = int(h)
            boxA = (x_ , y_ , x_ + w_ , y_ + h_)
            print(boxA)
            imgname = path.split("/")[-1]
            img = cv2.imread("mosaic/"+ imgname)
            print(img.shape)
            h,w,c = img.shape
            height = h- h%128 + 128
            width = w -w%128 + 128
            big_image = np.zeros((height,width,3), np.uint8)
            big_image[0:h,0:w] = img
            index = 0
            for i in range(0,height,128):
                for j in range(0,width,128):
                    boxB = (j, i, j+128, i+128)
                    iou = bb_intersection_over_union(boxA,boxB)
                    print(iou)
                    if iou > 0.7:
                        cv2.imwrite("new_data/mosaic/" + imgname.split(".")[0] + "_" + str(index)+".jpg",img[i:i+128,j:j+128])
                    elif iou < 0.2:
                        cv2.imwrite("new_data/nomosaic/" + imgname.split(".")[0] + "_" + str(index)+".jpg",img[i:i+128,j:j+128])
                    index += 1
def addMosaic(path):
    imagename = path.split("/")[-1]
    label = imagename.split("_")[0]
    img = cv2.imread(path)
    if img is None:
        return
    h,w,c = img.shape
    left_top_x =  random.randint(w//10,w-w//10)
    left_top_y =  random.randint(h//10,h-h//10)
    neighbor = max(9,9 * max( (max(h,w)//2688), max(h,w)//(1242)) )
    print(neighbor)
    height = random.randint(h//20,h-h//20)
    width = random.randint(w//20,w-w//20)
    if left_top_x + width >= w:
        width = w - left_top_x - 5
    if left_top_y + height >= h:
        height = h - left_top_y - 5
    mosaic = do_mosaic(img,left_top_x,left_top_y,width,height,neighbor)
    print("./dataset/train/mosaic/" + path.split("/")[-1])
    with open('mosaic/coordinates.txt', 'a') as fh:
        fh.write('{} {} {} {} {}\n'.format("./mosaic/" + imagename,left_top_x, left_top_y, width, height))  
    cv2.imwrite("./mosaic/" + imagename, mosaic)


def main():
    print("haha")
    image_list = get_images('./dataset/train/origin/')
    image_list.sort()
    print(image_list)
    for each in image_list:
#         addHeart(each)
        addMosaic(each)
    read_from_txt("mosaic/coordinates.txt")
if __name__ == '__main__':
    main()
