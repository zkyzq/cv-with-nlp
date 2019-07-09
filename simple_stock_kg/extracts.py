# -*- coding: utf-8 -*
import os
import csv
from lxml import etree
import io
import sys
reload(sys)
sys.setdefaultencoding('utf8')


def extract(stockpage_dir, executive_csv):
    """Extract executive of the comnpany or stock

    Args:
        stockpage_dir: (str) the directory of stock pages
        executive_csv: (str) the full path of the CSV file to be saved
    """
    # 获取html数据文件
    pages = map(lambda _: os.path.join(stockpage_dir, _),os.dirlist(stockpage_dir))
    pages = filter(lambda _: _.endswith('html'), pages) # 只留下以'html'结尾的文件
    headers = ['name', 'gensder', 'age', 'code', 'jobs'] # 需要读取的内容，也就是表头
    
    # 读取html中的信息并写入executive_csv中
    with io.open(executive_csv, 'wb+') as infile:
        infile_dictWriter = csv.DictWriter(infile, headers)
        
        
    
