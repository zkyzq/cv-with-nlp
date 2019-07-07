# python知识点
**---------------2019.07.04--------------**
## 路径拼接函数os.path.join()
- 功能：
1、链接两个或多个路径名组件，中间会自动加上'\' 
2、如果有一个组件是一个绝对路径（即前边有'/'），则在它之前的所有组件均会被舍弃  
3、如果最后一个组件为空，则生成的路径以一个’/’分隔符结尾  
-示例：
```
>>> import os
>>> path1 = 'home'
>>> path2 = 'work'
>>> path3 = 'test'
>>> print(os.path.join(path1,path2,path3))
home\work\test

>>> path1 = 'home'
>>> path2 = '/work'
>>> path3 = 'test'
>>> print(os.path.join(path1,path2,path3))
/work\test # 因为work是绝对路径，所以home被忽略

>>> path1 = '/home'
>>> path2 = 'work'
>>> path3 = ''
>>> print(os.path.join(path1,path2,path3))
/home\work\
```
## os.listdir() 
- 功能：用于返回指定的文件夹包含的文件或文件夹的名字的列表。这个列表以字母顺序。 它不包括 '.' 和'..' 即使它在文件夹中。
- 示例：
```
>>> import os
>>> import sys
>>> dirs = os.listdir("/home/zhikangyi001/My_stock_kg/")
>>> print(dirs)
['.git', 'LICENSE', 'README.md', 'build_csv.py', 'data', 'design.png', 'extract.py', 'img', 'import.report', 'import.sh', 'requirements.txt', 'result.txt', 'stock.py']
```

**---------------2019.07.05--------------**
## map()
- 功能：将一个序列的元素依次输入作为参数的某个函数，得到一个列表或迭代器  
***注：python2.x返回列表，python3.x返回迭代器***
- 格式：map(function, iterable)  
- 示例：
```
# python2.7
>>> def square(x):
...     return x ** 2
... 
>>> map(square, [1,2,3,4,5])
[1, 4, 9, 16, 25]
# 使用lambda函数
>>> map(lambda x, y : x + y, [1,2,3,4], [1,2,3,4])
[2, 4, 6, 8]
```
- ***踩坑：！！！原因并未搞清楚！！！***
```
for k,v in zip(List, map(function, List)):
    del(List[0])
# 后果：假设List长度为10，最后进循环5次就结束了
```
自己测试的case：
```
>>> List = [1,2,3,4,5,6]
>>> resList = zip(List,map(square, List))
>>> print(resList)
[(1, 1), (2, 4), (3, 9), (4, 16), (5, 25), (6, 36)]
>>> for k,v in resList:
...     del(resList[0])
...     print(resList, len(resList))
... 
([(2, 4), (3, 9), (4, 16), (5, 25), (6, 36)], 5)
([(3, 9), (4, 16), (5, 25), (6, 36)], 4)
([(4, 16), (5, 25), (6, 36)], 3)
```
# filter()
- 功能：过滤掉不符合条件的元素，返回由符合条件元素组成的新列表  
***注：python2.x返回列表，python3.x返回迭代器***
- 格式：filter(function, iterable)
- 示例：
```
>>> def isodd(x):
...     return x % 2 == 1
... 
>>> def isodd(x):
...     return x % 2 == 1
... 
>>> filter(isodd, [1,2,3,4,5,6])
[1, 3, 5]
```
# Python赋值、浅复制、深复制
根据网上资料...其实python中可以认为是没有赋值的，仅存在引用的情况。可分为两种情况：1、数字和字符串；2、字典和列表
## 1 数字和字符串
对于数字和字符串，赋值、浅复制和深复制没有区别，都只是引用，相当于起了个别名，并未发生内存地址上的变化。
### 1.1 赋值
**“=”连接的赋值形式**和**取值相同**(这种情况应该不算赋值，但此处一起记录)的数字、字符串变量都会指向相同的内存地址。
- 示例：
```
>>> n1 = 232
>>> n2 = n1
>>> id(n1)
1760304816
>>> id(n2)
1760304816
>>> n3 = 232
>>> id(n3)
1760304816
```
***注：字符串示例情况类似。***  
***++==特殊注意==++：当不同变量取相同的小数时，其地址是不同的，需后续进一步研究***
### 1.2 浅复制
使用copy库中的copy()函数
- 示例：
```
>>> import copy
>>> a = "Abc"
>>> b = copy.copy(a)
>>> id(a)
2272643600712
>>> id(b)
2272643600712
```
### 1.3 深复制
使用copy库中的deepcopy()函数
- 示例：
```
>>> n1 = 123
>>> n2 = copy.deepcopy(n1)
>>> id(n1)
1760301328
>>> id(n2)
1760301328
```
综上，对于相同的数字和字符串变量，所有变量都是引用，无论赋值、浅复制还是深复制都会指向相同的存储地址。若将变量重新复制，则会指向一个新的内存地址。  
![image](https://images2018.cnblogs.com/blog/1307747/201804/1307747-20180409142513906-81843973.png)  
（图片摘自https://www.cnblogs.com/doublexi/p/8745792.html）
## 2 列表和字典等
对于字典和列表，三种复制方式效果不同。
### 2.1 赋值
情况和数字与字符串情况相同，都仅仅是取了一个别名（即引用）
- 示例：
```
# list2=list1，则list1和list2指向的内存地址相同
>>> list1 = [1,2,3]
>>> list2 = list1
>>> id(list1)
2272643607240
>>> id(list2)
2272643607240

# 通过list2改变某元素值，list1也相应改变
>>> list2[0] = 4
>>> list1
[4, 2, 3]
>>> list2
[4, 2, 3]

# 赋给list2另外一个列表，注意这里和上种情况不同，这里相当于把list2指向了另一个列表，而list1是不变的
>>> list2 = [2,3,4,5]
>>> list1
[4, 2, 3]
```
![image](https://images2018.cnblogs.com/blog/1307747/201804/1307747-20180409143916437-1877441324.png)  
（图片摘自https://www.cnblogs.com/doublexi/p/8745792.html）  
### 2.2 浅复制
copy.copy()函数实现，最高层会重新建立，但是下边的几层仍然指向原变量指向的内存地址。
- 示例：
```
>>> n1 = {"k1": "wu", "k2": 123, "k3": ["alex", 678]} # n1为一个字典，键值'k1''k2'对应的时字符串和数值，'k3'对应了一个列表
>>> n3 = copy.copy(n1)  # 浅拷贝
>>> print(id(n1))
2272637266568
>>> print(id(n3))
2272643606920 # 可以看到最高层的内存地址是不同的，相当于把最高层重新分配了一个内存地址

>>> print(id(n1["k3"]))
2272643586376
>>> print(id(n3["k3"]))
2272643586376 # k3对应的地址相同，说明后边层都是对应相同的地址的，见示意图
```
![image](https://images2018.cnblogs.com/blog/1307747/201804/1307747-20180409145414483-599873973.png)  
（图片摘自https://www.cnblogs.com/doublexi/p/8745792.html）
### 2.3 深复制
copy.deepcopy(),除了的最底层，其他的都会指向新的内存，最底层元素比如数值和字符串仍然指向相同地址
- 示例：
```
>>> n1 = {"k1": "wu", "k2": 123, "k3": ["alex", 678]}
>>> n4 = copy.deepcopy(n1)  # 深拷贝
>>> print(id(n1))
2272637266568
>>> print(id(n4))
2272643586248 # 第一层内存不同

>>> print(id(n1["k3"]))
2272643595720
>>> print(id(n4["k3"]))
2272643598216 # 第二层内存也不同

>>> id(n1["k2"])
1760301328
>>> id(n4["k2"])
1760301328 # 第二层k2对应的因为是字符串和数值所以是相同的
```
![image](https://images2018.cnblogs.com/blog/1307747/201804/1307747-20180409145414483-599873973.png)
（图片摘自https://www.cnblogs.com/doublexi/p/8745792.html）
## 3 其他实例
```
>>> import copy
>>> dict1 = {'user':'test', 'num':[1,2,3]}
>>> dict2 = dict1 # 直接复制，相当于引用
>>> dict3 = dict1.copy() # 浅复制
>>> dict4 = copy.deepcopy(dict1) # 深复制

# 查看内存地址异同
>>> id(dict1)
139960238410280
>>> id(dict2)
139960238410280 # 内存地址相同
>>> id(dict3) 
139960238412136 # 内存地址不同
>>> id(dict4)
139960238414376 # 内存地址不同

# 修改原字典查看相应改变情况
>>> dict1['user'] = 'change'
>>> dict1['num'].remove(1)
>>> print(dict1)
{'num': [2, 3], 'user': 'change'}

>>> print(dict2) # 相同改变
{'num': [2, 3], 'user': 'change'}

>>> print(dict3) # ？？？？暂时没搞懂
{'num': [2, 3], 'user': 'test'}

>>> print(dict4) # 毫无改变
{'num': [1, 2, 3], 'user': 'test'}
>>> 
```
## csv库
（https://www.jianshu.com/p/51211fcdf4b8）  
CSV (Comma Separated Values)，即逗号分隔值，是一种常用的文本格式，用来存储表格数据，包括数字或者字符。  
- 四个主要类：reader、writer、DictReader和DictWriter
- 写数据示例：
```
#-*- coding: utf-8 -*
import csv

# 通过 writer类写入数据
# 待写入的数据 注意到两个列表的元素个数不一样
test_writer_data_1 = ['Tom', 'Cody', 'Zack']
test_writer_data_2 = ['Mike', 'Bill']

# 创建并打开文件
with open('test_writer.csv', 'w', newline='', encoding='utf-8') as csvfile:
    # 获得 writer对象 delimiter是分隔符 默认为 ","
    writer = csv.writer(csvfile, delimiter=' ')
    # 调用 writer的 writerow方法将 test_writer_data写入 test_writer.csv文件
    writer.writerow(test_writer_data_1)
    writer.writerow(test_writer_data_2)
    # 也可以换成列表形式：writer.writerows([test_writer_data_1, test_writer_data_2])，注意这里是writerows，比之前多一个“s”


# 通过 DictWriter类写入数据
# 待写入的数据 注意到待写入的数据类型为 dict 且第二个字典没有 lastname
test_dict_writer_data_1 = {'firstname': 'Tom', 'lastname': 'Loya'}
test_dict_writer_data_2 = {'firstname': 'Tom', 'lastname': 'Loya'}

# 创建并打开文件
with open('test_dict_writer.csv', 'w', newline='', encoding='utf-8') as csvfile:
    # 设置表头
    fieldnames=['firstname','lastname', 'thirdname'] 
    # 获得 DictWriter对象 delimiter是分隔符 默认为 "," 表头为 'firstname' 'lastname'
    # 字典中的key一定要都被包含在fieldnames中，即fieldnames >= keys
    dict_writer = csv.DictWriter(csvfile, delimiter=' ', fieldnames=fieldnames)
    # 第一次写入数据先写入表头
    dict_writer.writeheader()
    # 调用 DictWriter的 writerow方法将 test_dict_writer_data写入 test_dict_writer.csv文件
    dict_writer.writerow(test_dict_writer_data_1)
    dict_writer.writerow(test_dict_writer_data_2)
    # 也可以换成列表形式：writer.writerows([test_dict_writer_data_1, test_dict_writer_data_2])
```

- 读数据示例：
```
# reader读文件示例
with open('test_writer.csv', 'r', encoding='utf-8') as csvfile:
    # 获得 reader对象 delimiter是分隔符 默认为 ","
    reader = csv.reader(csvfile, delimiter=' ')
    for row in reader:
        print(row)

# DictReader读文件示例
with open('test_dict_writer.csv', 'r', encoding='utf-8') as csvfile:
    # 获得 DictReaderer对象 delimiter是分隔符 默认为 ","
    dict_reader = csv.DictReader(csvfile, delimiter=' ')
    for row in dict_reader:
        print(row)
```
- 结果：
```
PS E:\Python_learning> python .\csv_lib_test.py
['Tom', 'Cody', 'Zack']
['Mike', 'Bill']
{'lastname': 'Loya', 'thirdname': '', 'firstname': 'Tom'}
{'lastname': 'Loya', 'thirdname': '', 'firstname': 'Tom'}
```
***注意：***  
***（1）writer的文件要用reader，DictWriter写入的文件要用DictReader***  
***（2）读文件的时候记得要设置对分隔符delimiter***
