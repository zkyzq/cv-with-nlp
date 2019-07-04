# shell命令知识点
## 管道符 |
- 定义：将前一个命令得到的结果传递给下一个
- 格式：command1 | command2（把第一个命令command 1执行的结果作为command2的输入传给command 2）
- 示例：
```
[zhikangyi001@xxxxx ~]$ echo "This is a test" | awk '{print $0}'
This is a test
```
## xargs
- 功能：
和管道符一起使用，有些命令不支持管道符，就需要使用| xargs实现参数传递；可以将之前的输出结果合成一行，或者加上-n nums(例如-n3，每行就会有3个输出)

- 示例1：
```
[work@xxxxx]$ hive -e 'show partitions tablename'| awk -F "=" '{print $2}' |sort -r | head -2   
OK  
Time taken: 1.377 seconds
20190702
20190701

[work@xxxxx]$ hive -e 'show partitions tablename'| awk -F "=" '{print $2}' |sort -r | head -2 | xargs  
OK  
Time taken: 1.344 seconds
20190702 20190701
```
- 示例2：
```
[work@xxxxx]$ hive -e 'show partitions tablename'| awk -F "=" '{print $2}' |sort -r | head -10 |xargs -n3  
OK  
Time taken: 1.533 seconds
20190702000000 20190701000000 20190630000000
20190629000000 20190628000000 20190627000000
20190626000000 20190625000000 20190624000000
20190623000000
```
## eval
## if中的“-e，-d，-f”
文件表达式
-e filename 如果 filename存在，则为真  
-d filename 如果 filename为目录，则为真   
-f filename 如果 filename为常规文件，则为真  
-L filename 如果 filename为符号链接，则为真  
-r filename 如果 filename可读，则为真   
-w filename 如果 filename可写，则为真   
-x filename 如果 filename可执行，则为真  
-s filename 如果文件长度不为0，则为真  
-h filename 如果文件是软链接，则为真  
filename1 -nt filename2 如果 filename1比 filename2新，则为真。  
filename1 -ot filename2 如果 filename1比 filename2旧，则为真。  
## sed -i '1i\' dir
- 参数说明：
-i 参数说明更改直接写入原文件中
1i:表示在前边原文档前加一行
```
sed -i '1i\A_id\tA_name' $BASIC_DATA_FOLDER/dir_$DAY
# 将A_id A_name加到后边文件最前边一行
```
## $(cd \`dirname $0\`;pwd)
- 功能：获取当前目录，只能在脚本里写，不能直接写在命令行中
- 说明：
dirname $0 :取得当前执行脚本文件的父目录  
cd \`dirname $0` :进入这个目录  
pwd :显示当前目录（cd执行后的）
- 示例：
```
[work@xxxxx]$ sh test.sh 
/home/work
[work@xxxxx]$ pwd
/home/work/test
```
## $@表示所有
- 示例1：
```
# test1：不加@
t_name_list=("A" "B" "C")
for t_name in ${t_name_list}
do
    echo $t_name
done
```
结果：
```
[work@xxxxx]$ sh test.sh 
A
```
- 示例2：
```
# test2：加@
t_name_list=("A" "B" "C")
for t_name in ${t_name_list[@]}
do
    echo $t_name
done
```
结果：
```
[work@xxxxx]$ sh test.sh 
A
B
C
```
