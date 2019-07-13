# -*- coding: utf-8 -*
import os
import csv
from lxml import etree
import io
import sys
# reload(sys)
# sys.setdefaultencoding('utf8')
############################# 异常抛出机制使用 ##############################

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
        infile_dictWriter.writeheader()
        
        for page in pages:
            file_name = page.split(r'/')[-1]
            code = file_name.split(r'.')[0]
            executives = []
            with io.open(page, 'rb') as file_page:
                content = file_page.read()
                html = etree.HTML(content)
                divs = html.xpath('//div[@id="ml_001"]//div[contains(@class, "person_table")]')
                
                for div in divs:
                    item = {}
                    item['name'] = div.xpath('.//thead/tr/td/h3/a/text()')[0].replace(',', '-')
                    item['jobs'] = div.xpath('.//thead/tr[1]/td[2]/text()')[0].replace(',', '/')
                    gender_age_education = div.xpath('.//thead/tr[2]/td[1]/text()')[0].split()
                    
                    # 提取性别
                    try:
                        item['gender'] = gender_age_education[0]
                        if item['gender'] not in ['男','女']:
                            item['gender'] = 'NULL'
                    except IndexError as e:
                        item['gender'] = 'NULL'
                        print('Get Gender Error')
                
                    try:
                        item['age'] = gender_age_education[1].strip('岁')
                        try:
                            item['age'] = int(item['age'])
                        except ValueError as e:
                            item['age'] = '-1'
                            print('Get Age Error-1')
                    except IndexError as e:
                        print('Get Age Error-2')
                    item['code'] = code
                    executives.append(item)
            infile_dictWriter.writerows(executives)

def main():
    stockpage_dir = './data/stockpage'
    executive_csv = './data/executives.csv'
    extract(stockpage_dir, executive_csv)

if  __name__ == "__main__":
    main()
