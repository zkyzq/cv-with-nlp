# python中的re模块使用总结
## 1.正则表达式
描述了一种字符串匹配模式，用来检查一个串是否含有某种字串/将匹配的字串替换或者从某个串中取出符合某个条件的字串等。    
- 基本示例：\^[a-z0-9_-]{3,15}$  
解释：^为开始标记，表示必须为其后边的为开头，例子中表示必须以a-z,0-9,_或-开头；$为结束标记,表示必须以其前边的结尾，例子中表示必须以a-z，0-9，_或-结尾；[]中为精确匹配的内容，此处为字母，数字，下划线或连接符，注意是“或”的连接关系；{}表示字符长度，此处为3—15个字符的长度
- 一些语法：
\d：可匹配一个数字
\w：可匹配一个数字或字母
\s：匹配一个空格（包括tab）
+：表示其前边的内容至少存在一个字符
*:表示其前边的内容可有任意个字符（包括0个）
- 以下示例摘自廖雪峰教程：
[0-9a-zA-Z\_]可以匹配一个数字、字母或者下划线；
[0-9a-zA-Z\_]+可以匹配至少由一个数字、字母或者下划线组成的字符串，比如'a100'，'0_Z'，'Py3000'等等；
[a-zA-Z\_][0-9a-zA-Z\_]*可以匹配由字母或下划线开头，后接任意个由一个数字、字母或者下划线组成的字符串，也就是Python合法的变量；
[a-zA-Z\_][0-9a-zA-Z\_]{0, 19}更精确地限制了变量的长度是1-20个字符（前面1个字符+后面最多19个字符）。
## 2.re模块
常用功能函数：compile、match、search、findall、finditer、split、sub、subn  
### 2.1 编译re.compile  
- 定义：编译正则表达式模式，返回一个对象的模式。（可以把那些常用的正则表达式编译成正则表达式对象，这样可以提高一点效率。）  
- 格式：re.compile(pattern,flags=0)     
参数解释：pattern: 编译时用的表达式字符串。flags 编译标志位，用于修改正则表达式的匹配方式，如：是否区分大小写(re.I)，多行匹配(re.M)等。
- 示例：
```
>>> import re
>>> ors = "I am a teacher. I am Terry."
>>> coms = re.compile(r'\w*er*\w')
>>> print(coms.findall(ors))
['teacher', 'Terry']
>>> 
>>> 
>>> coms2 = re.compile(r'\w*er\w+')
>>> print(coms2.findall(ors))
['Terry']
>>>
>>>
>>> coms3 = re.compile(r'\w*er+\w')
>>> print(coms3.findall(ors))
['Terry']
>>>  
>>> 
>>> ors = "I am a teacher. I am Terry. era" 
>>> coms = re.compile(r'\w+er')
>>> print(coms.findall(ors))
['teacher', 'Ter']
```
### 2.2 判断是否匹配re.match
- 定义：match()方法判断是否匹配，如果匹配成功，返回一个Match对象，否则返回None。  
- 格式：re.match(pattern, string, flags=0)
- 示例：
```
>>> ors = "com www runcomoob"
>>> re.match('com',ors)
<re.Match object; span=(0, 3), match='com'>
>>> print(re.match('\w+com+\w',ors))
None
```
### 2.3 搜索re.search
- 定义：在字符串内查找模式匹配,只要找到***第一个匹配***然后返回一个Match对象，如果字符串没有匹配，则返回None。
- 格式：re.search(pattern, string, flags=0)
- 示例：  
```
>>> print(re.search('aba','asd aoiusudi abasad sadaba safabasaf'))
<re.Match object; span=(13, 16), match='aba'>
>>> print(re.search('\w+aba','asd aoiusudi abasad sadaba safabasaf'))
<re.Match object; span=(20, 26), match='sadaba'>
```
***注：match和search得到的Match对象具体内容可以通过MatchObject.group()获取，后文详细说明***
### 2.4 分组MatchObject.group()
- 定义：用于子串提取，用()表示的就是要提取的分组（Group）。比如：^(\d{3})-(\d{3,8})$分别定义了两个组，可以直接从匹配的字符串中提取出区号和本地号码。
- 格式：
正则表达式用使用"()"划分组，结果中可以使用group（）来获取不同租的内容，group(0)表示原字符串，group(1)表示第一个分组...以此类推
- 示例1：
```
>>> m = re.match(r'^(\d{3})-(\d{3,8})$','010-122341')
>>> m
<re.Match object; span=(0, 10), match='010-122341'>
>>> m.group(0)
'010-122341'
>>> m.group(1)
'010'
>>> m.group(2)
'122341'
```
- 示例2（其中的"|"就是"或"的意思）：
```
>>> t = '19:05:30'
>>> m = re.match(r'^(0[0-9]|1[0-9]|2[0-3]|[0-9])\:(0[0-9]|1[0-9]|2[0-9]|3[0-9]|4[0-9]|5[0-9]|[0-9])\:(0[0-9]|1[0-9]|2[0-9]|3[0-9]|4[0-9]|5[0-9]|[0-9])$', t)
>>> m.group(0)
'19:05:30'
>>> m.group(1)
'19'
>>> m.group(2)
'05'
>>> m.groups()
('19', '05', '30')
```
### 2.5 遍历匹配re.findall
- 定义：遍历匹配，可以获取字符串中所有匹配的字符串，返回一个列表
- 格式：re.findall(pattern, string, flags=0)
- 示例：
```
>>> p = re.compile(r'\d+')
>>> print(p.findall('o1n2m3k4'))
['1', '2', '3', '4']
>>> p = re.compile(r'\d+\w')
>>> print(p.findall('o1n2m3k4'))
['1n', '2m', '3k']
```
### 2.6 切分字符串re.split
- 定义：按照能够匹配的子串将string分割后返回列表。
- 格式：re.split(pattern, string[, maxsplit])   其中maxsplit用于指定最大分割次数，不指定将全部分割。
- 示例：
```
>>> p = re.compile(r'\t+')
>>> print(re.split(p, 'I        am      a       student .'))
['I', 'am', 'a', 'student', '.']
>>> print(re.split(r'[\s\,]+', 'a,b, c  d'))
['a', 'b', 'c', 'd']
```
### 2.7 子串替换re.sub
- 定义：
匹配子串病替换，最后返回替换完成后的结果
- 格式：
re.sub(pattern, repl, string, count)  
count指替换个数，默认为0，表示每个匹配项都替换。
- 示例：
```
>>> text = "JGood is a handsome boy, he is cool, clever, and so on..."
>>> print(re.sub(r'\s+', '-', text))
JGood-is-a-handsome-boy,-he-is-cool,-clever,-and-so-on...
>>> print(re.sub(r'\s*', '-', text))
-J-G-o-o-d--i-s--a--h-a-n-d-s-o-m-e--b-o-y-,--h-e--i-s--c-o-o-l-,--c-l-e-v-e-r-,--a-n-d--s-o--o-n-.-.-.-
```
***注：re.subn()在替换后返回替换次数***  
***示例：***
```
>>> print(re.sub("g.t","have",'I get A,  I got B ,I gut C'))
I have A,  I have B ,I have C
>>> print(re.subn("g.t","have",'I get A,  I got B ,I gut C'))
('I have A,  I have B ,I have C', 3)
```
### 测试：
```
>>> print('Test: 010-12345')
Test: 010-12345
>>> m = re.match(r'^(\d{3})-(\d{3,8})$', '010-12345')
>>> print(m.group(1), m.group(2))
010 12345
```
