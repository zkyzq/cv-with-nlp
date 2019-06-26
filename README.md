# cv-with-nlp
每天至少随手push一次. by zhangqi.  
### 2019-06-17：  
学习java抽象类，有点难了。by zhangqi.
### 2019-06-18:
java接口，和抽象类有点像，没有彻底搞懂与抽象类在实际使用上有何优势，先写业务代码，周末总结。by zhangqi.
### 2019-06-19:
静态字段和静态方法。类所有成员变量共有的，和c++类似。by zhangqi.
### 2019-06-20:
java包结构。用了这么久的java，才终于知道包结构的作用，就和C++中namespace作用是类似的，为了防止类名冲突。一般采用域名倒写，比如com.alibaba.一级包名.二级包名.类名
### 2019-06-21:
java作用域。java的成员变量叫feild，成员函数叫method。作用域的说明见oopScope.java注释。by zhangqi  
low到爆的shell基础知识及FM(因子分解机)预测部分python实现,顺带回忆下python. by zhikangyi    
（提醒这位同学markdown换行需要在上一行后面敲两次空格）

### 2019-06-23
python 飞机大战，很经典，不得不说同一个功能，python实现起来简单太多了。by zhangqi.

### 2019-06-24
java classpath的设置(最好就是默认当前路径)和jar包的理解(其实就是压缩文件,但压缩前里面目录一定要注意) .  
写了几个二维数组(图像数据)处理的函数，转置，中值滤波，阈值处理。by zhangqi.
### 2019-06-25
java 模块。IDE-free是一种境界，不是说不用ide，而是知道如何用普通的文本编辑器写代码，并且知道如何用系统自带的工具去运行，只是为了工作效率去用ide而已。java代码的编译，执行过程常用指令为：


  javac -d bin src/module-info.java src/com/alibaba/sample/*.java     //编译src目录下所有源码文件，生成的class文件在bin中
  java -cp app.jar:a.jar:b.jar:c.jar com.alibaba.sample.Main  //运行一个java程序
  jar --create --file hello.jar --main-class com.alibaba.sample.Main -C bin .   //打包成jar  
  java -jar hello.jar  //运行jar  
  jmod create --class-path hello.jar hello.jmod  //创建模块文件  
  jlink --module-path hello.jmod --add-modules java.base,java.xml,hello.world --output jre/  //用jlink将模块文件以及用到的其他模块裁    减成最小的jre执行单元      
  jre/bin/java  --module hello.world     //执行jre 

### 2019-06-26
java字符串，java引用类型一定要深入理解。字符串不可变，对字符串进行的操作实际上是返回了一个新的字符串，原字符串是不可变的。注意引用类型的深拷贝和浅拷贝。
