/**
 * @author tangxi.zq
 * {@summary: java的classpath和jar}
 * 
 * 1. java和cpp一样是编译型语言，源码文件是.java，但编译后的.class文件才是真正可以被JVM执行的字节码，因此，jvm需要知道到哪儿去搜索对应的.class文件
 * 2. 默认classpath是当前目录，可以是一组目录的集合，windows下用分号隔开。在某个路径下找到之后就不会再找。
 * 3. java -cp classpath abc.xyz.Hello       最好的做法是默认路径就为classpath，对于绝大多数够用。
 * 4. jar包就是一个zip文件，目录结构为：
 * 
 * package sample
 *     bin
 *        hong
 *            Person.class
 *        ming
 *            Person.class
 *        mr
 *            jun
 *                Arrays.class
 */


 