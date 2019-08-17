__authur__ == "zhangqizky"
__time__ = "20190817"

import scipy.spatial import distance
import imutils import face_utils
import imutils
import dlib
import cv2



def eye_aspect_ratio(eye):
    '''
        计算眼睛部位的横纵比
    '''
    A = distance.euclidean(eye[1],eye[5])
    B = distance.euclidean(eye[2],eye[4])
    C = distance.euclidean(eye[0],eye[3])
    return (A+B)/(2.0*C)

def main():
    thresh = 0.25
    frame_check = 15

    detect = dlib.get_frontal_face_detector()
    #检测人脸之后是返回什么类型的数据
    predict = dlib.shape_predictor("Drowsiness_Detection_fork\shape_predictor_68_face_landmarks.dat")
    #左眼和右眼在脸部68特征点中的起始和终止位置
    (lStart,lEnd) = face_utils.FACIAL_LANDMARKS_68_IDXS["left_eye"]
    (rStart,rEnd) = face_utils.FACIAL_LANDMARKS_68_IDXS["right_eye"]

    cap = cv2.VideoCapture(0)
    flag = 0
    while True:
        ret,frame = cap.read()
        frame = imutils.resize(frame,width=450)
        gray = cv2.cvtColor(frame,cv2.COLOR_BGR2GRAY)
        #检测人脸
        subjects = detect(gray)
        for subject in subjects:
            #计算出脸部的特征点
            shape = predict(gray,subject)
            #将人脸特征点转为np.array
            shape = face_utils.shape_to_np(shape)
            #分别获取左眼和右眼
            leftEye = shape[lStart:lEnd]
            rightEye = shape[rStart:rEnd]
            rightEAR = eye_aspect_ratio(rightEye)
            leftEAR = eye_aspect_ratio(leftEye)
            ear = (leftEAR+rightEAR)/2
            leftEyeHull = cv2.convexHull(leftEye)
            rightEyeHull = cv2.convexHull(rightEye)
            cv2.drawContours(frame,[leftEyeHull],-1,(0,255,0),1)
            cv2.drawContours(frame,[rightEyeHull],-1,(255,0,0),1)
            if ear < thresh:
                flag+=1
                if flag > frame_check:
                    cv2.putText(frame,"ALERT,Eye is closing!",(10,30),cv2.FONT_HERSHEY_SIMPLEX,0.7,(0,0,255),2)
            else:
                flag = 0
        cv2.imshow("Frame",frame)
        if cv2.waitKey(10) &0xFF == ord("q"):
            break

    cv2.destoryAllWindows()
    cap.stop()

if __name__ == '__main__':
    main()
