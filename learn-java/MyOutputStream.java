import java.io.*;

/**
 * @author     zhangqizky
 * @apiNote    标准输出流
 * OutputStream是所有输出流的超类，是一个抽象类，最重要的方法是void write(int b)
 写入一个字节到输出流，虽然传入的是一个int参数，但只会写入一个字节，即写入int最低8位表示字节的部分
 close()
 flush()    清缓冲区
 */

 public class MyOutputStream
 {
     public static void main(String[]args)throws Exception
     {
        try(OutputStream output = new FileOutputStream("readme.txt"))
        {
        output.write(72); // H
        output.write(101); // e
        output.write(108); // l
        output.write(108); // l
        output.write(111); // o

        output.write("Hello".getBytes("UTF-8"));
        output.close();
        }
        /**
         * OutputStream方法也是阻塞的
         */

         /**
          * 用FileOutputStream可以从文件获取输出流，这是OutputStream常用的一个实现类。
          此外，ByteArrayOutputStream可以在内存中模拟一个OutputStream：
          */

          byte[]data;
          try(ByteArrayOutputStream output = new ByteArrayOutputStream())
          {
              output.write("hello".getBytes());
              output.write("world".getBytes());
              data = output.toByteArray();
          }
          System.out.println(new String(data,"UTF-8"));
     }
 }


 

