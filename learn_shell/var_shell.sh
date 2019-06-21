#!/bin/bash 
#第一行表明采用的shell解释器
echo "Hello world"

#定义变量不加"$"符号
your_name="zkyzq"

#使用已定义过得变量要加"$",加花括号可以明确变量名称范围,推荐
echo $your_name
echo ${your_name}

#删除变量
unset your_name
echo $your_name

#字符串-单引号
#(1)单引号里的字符原样输出,其中的变量无效,转义字符无效
#(2)单引号字符串中不能有单个单引号,但可以有成对单引号,用于拼接
my_name="zkyzq"
str1='this is ${my_name}'
str2='this is '${my_name}''
echo str1:$str1           str2:$str2

#字符串-双引号:双引号里可以有变量,可以转义
str3="this is ${my_name}"
echo str3:$str3
str4="Hello,I'm \"${my_name}\"!"
echo str4:$str4
