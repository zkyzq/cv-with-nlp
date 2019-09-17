import cv2
import time


if __name__=='__main__':
    cap =cv2.VideoCapture(0);
    (major_ver,minor_ver,subminor_ver) = (cv2.__version__).split(".")
    print(cv2.__version__)
    if int(major_ver) < 3:
        fps = cap.get(cv2.cv.CV_CAP_PROP_FPS)
        print("Frames per second using video.get(cv2.cv.CV_CAP_PROP_FPS): {0}".format(fps))
    else:
        fps = cap.get(cv2.CAP_PROP_FPS)
        print("Frames per second using video.get(cv2.CAP_PROP_FPS) : {0}".format(fps))
    num_frames = 120
    print("capture{0}frames".format(num_frames))

    start = time.time()
    for i in range(num_frames):
        ret,frame = cap.read()
    end = time.time()
    seconds =end-start
    print("Time taken {0}seconds".format(seconds))

    fps = num_frames / seconds
    print("Estimate frames per second : {0}".format(fps))
    cap.release()
