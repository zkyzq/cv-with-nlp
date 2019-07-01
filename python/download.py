__authur__= 'tangxi.zq'
__time__ = '2019-06-29'

import os
import pandas as pd
import urllib.request
import csv
headers = {"User-Agent": "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.86 Safari/537.36"}

def urllib_download(image_url, path):
    """[用urlretrieve函数下载]
    
    Arguments:
        image_url {[string]} -- [待下载图像url]
        path {[string]} -- [图片下载后存放路径]
    """
    from urllib.request import urlretrieve
    urlretrieve(image_url, path)     
 
def down_pic(url, path):
    """[用urlopen来把请求的数据写进路径，文件流的形式]
    
    Arguments:
        url {[string]} -- [待下载图像url]
        path {[string]} -- [图像下载后存放路径]
    """
    try:
        req = urllib.request.Request(url, headers=headers)
        data = urllib.request.urlopen(req).read()
        with open(path, 'wb') as f:
            f.write(data)
            f.close()
            print("download success")
    except Exception as e:
        print(str(e))


def main():
    """[下载主函数，读tsv，解析出图片地址和名称，存进本地盘]
    """
    #下载photoshop修改之后的
    with open("photoshops.tsv") as fd:
        rd = csv.reader(fd, delimiter="\t", quotechar='"')
        index = 0
        os.makedirs('./photoshops/', exist_ok=True)
        for row in rd:
            print(row)
            image_name = row[0] + "." +row[3]
            image_url = row[2]
            print(image_name)
            print(image_url)
            down_pic(image_url,"photoshops/" + str(index).zfill(4) + "_" + image_name)
            print("download success.")
            index += 1
    #下载原始图像
    with open("originals.tsv") as fd:
        rd = csv.reader(fd, delimiter="\t", quotechar='"')
        index = 0
        os.makedirs('./originals/', exist_ok=True)
        for row in rd:
            print(row)
            image_name = row[0] + row[3]
            image_url = row[2]
            print(image_name)
            down_pic(image_url,"originals/" + str(index).zfill(4) + "_" + image_name)
            index += 1

if __name__ == '__main__':
    print("start...")
    main()
    print("Done.")
