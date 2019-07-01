# python中的re模块使用总结
## 1.正则表达式
	描述了一种字符串匹配模式，用来检查一个串是否含有某种字串/将匹配的字串替换或者从某个串中取出符合某个条件的字串等。    
	- 基本示例：^[a-z0-9_-]{3,15}$  
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
### 2.1 编译compile  
	编译正则表达式模式，返回一个对象的模式。（可以把那些常用的正则表达式编译成正则表达式对象，这样可以提高一点效率。）  
	格式：re.compile(pattern,flags=0)     
	参数解释：pattern: 编译时用的表达式字符串。flags 编译标志位，用于修改正则表达式的匹配方式，如：是否区分大小写(re.I)，多行匹配(re.M)等。  
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
### 2.2 判断是否匹配match
	match()方法判断是否匹配，如果匹配成功，返回一个Match对象，否则返回None。  
	格式：re.match(pattern, string, flags=0)
```
>>> ors = "com www runcomoob"
>>> re.match('com',ors)
<re.Match object; span=(0, 3), match='com'>
>>> print(re.match('\w+com+\w',ors))
None
```
	提前测试：
```
>>> print('Test: 010-12345')
Test: 010-12345
>>> m = re.match(r'^(\d{3})-(\d{3,8})$', '010-12345')
>>> print(m.group(1), m.group(2))
010 12345
```
未完待续。。。 

	

