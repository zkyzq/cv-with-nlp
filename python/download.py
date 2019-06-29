__authur__= 'tangxi.zq'
__time__ = '2019-06-29'

import os
os.makedirs('./image/', exist_ok=True)
import pandas 
# IMAGE_URL = "http://asipservice.cn-shanghai.oss.aliyun-inc.com/"
# IMAGE_URL = "http://aesellercrm.alibaba-inc.com/userauth/image.do?fileName="
# IMAGE_URL = "http://aesellercrm.alibaba-inc.com/images/fileReviewForIpay.htm?file="

def urllib_download(image_url, path):
    from urllib.request import urlretrieve
    urlretrieve(image_url, path)     
 
 
import urllib.request
headers = {"User-Agent": "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.86 Safari/537.36"}
def down_pic(url, path):
    try:
        req = urllib.request.Request(url, headers=headers)
        data = urllib.request.urlopen(req).read()
        with open(path, 'wb') as f:
            f.write(data)
            f.close()
            print("download success")
    except Exception as e:
        print(str(e))

f = open("photoshops.txt")
line = f.readline()
line = f.readline()
print(line)
index = 0
while line:
    print(line)
    # image_name = line.split(".")[0].split('_')[-1] + ".jpg"
    image_name_list = line.split(r"/t+")
    print("image_name_list: ",image_name_list)
    image_name = image_name_list[2]
    # print(image_name)
    image_url = image_name
    # print(image_url)
    down_pic(image_url, "photoshops/" + str(index).zfill(4) + "_" + image_name)
    index += 1
    line = f.readline()
    
f.close()
