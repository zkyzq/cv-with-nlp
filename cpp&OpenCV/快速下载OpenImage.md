## 快速下载OpenImagesv4的python脚本  
### 1. install awscli
`
sudo pip3 install awscli
`  
### 2. 下载一些描述openimage分类标签信息和bbox标注信息的文件  
`
wget https://storage.googleapis.com/openimages/2018_04/class-descriptions-boxable.csv 
wget https://storage.googleapis.com/openimages/2018_04/train/train-annotations-bbox.csv
`
### 3.下载你想要的Open Image中的类别  
`
python3 downloadOI.py --classes 'Ice_cream,Cookie' --mode train  
`    
注意： 如果你想要下载的类名中有空格，记得用下划线'_'代替，比如Ice_cream.

### 4. 示例
![下载进度](https://github.com/zhangqizky/cv-with-nlp/blob/master/img/16_45_17__07_21_2019.jpg)
