import cv2
import numpy as np 
from PIL import Image 
import matplotlib
matplotlib.use("TkAgg")
from matplotlib import pyplot as plt

def autocorr(npmat):
    result = np.correlate(npmat, npmat, mode='full')
    return result[result.size/2:]

def main():
    img = cv2.imread("/Users/tangxi/Desktop/embed.jpg")
    print(img.shape)
    npmat = np.array(img)
    res = autocorr(npmat)
    img_res = Image.fromarray(res)
    img_res.save("res.jpg")

if __name__ == '__main__':
    main() 
