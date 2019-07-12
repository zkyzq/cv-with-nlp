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
### 2019-06-27
java特有的stringbuilder类，可以提高java中string运算的速度。另外，今天开始学习SQL！今天的成果：安装MySQL。计划一周入门。
### 2019-06-28
java stringjoiner类。
### 2019-06-29  
今日code：下载图像！！PS：我发现我还可以直接在网页上编辑文件，并不会从电脑传到github哈哈哈哈哈，又可以愉快的push了
### 2019-06-30
python人脸检测dlib库，学习资料链接：https://face-recognition.readthedocs.io/en/latest/readme.html    
git、vim常用基本操作总结，FM反向传播代码完善。by zhikangyi  

### 2019-07-01
java包装类型   
正则表达式及re部分函数 by zhikangyi
### 2019-07-02
JavaBean，private属性+public读写方法
### 2019-07-03
java 枚举类，枚举类也是类，只是将其能定义的实例数目固定了，实例还是可以定义很多字段
### 2019-07-04
Java BigInteger，和Integer等一样都是继承自Number类，所以有很多共同的实例方法
### 2019-07-05
Java BigDecimal 浮点数的精确表示
### 2019-07-06
OpenCV自动给图片上色算法
### 2019-07-07
OpenCV计算凸包和轮廓
### 2019-07-08
java工具类
### 2019-07-09  
java异常处理
### 2019-07-10
java 异常捕获
### 2019-07-11
java异常抛出：  
- 当某个方法抛出了异常时，如果当前方法没有捕获异常，异常就会被抛到上层调用方法，直到遇到某个try ... catch被捕获为止  
- 调用printStackTrace()可以打印异常的传播栈，对于调试非常有用  
- 捕获异常并再次抛出新的异常时，应该持有原始异常信息；  
- 通常不要在finally中抛出异常。如果在finally中抛出异常，应该原始异常加入到原有异常中。调用方可通过Throwable.getSuppressed()获取所有添加的Suppressed Exception
### 2019-07-12
java自定义异常，一定先从RuntimeException派生出BaseException，再从BaseException派生出各种其他异常
