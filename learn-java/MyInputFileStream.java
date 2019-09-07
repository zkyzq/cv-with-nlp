import java.io.*;

/**
 * @author        zhangqizky
 * @apiNote       标准输入流InputStream
 */

 public class MyInputStream
 {
     public static void main(String[]args)throws Exception
     {
        // InputStream input = new FileInputStream("MyFile.java");
        // int i =0;
        // for(;;)
        // {
        //     int n = input.read();
        //     if (n==-1)
        //     {
        //         break;
        //     }
        //     System.out.println(n);
        //     i++;
        // }
        // System.out.println(i);
        // input.close();

        //在使用InputStream的时候一定要正确的关闭，但是如果碰到IO异常的话可能无法正确关闭
        //因此需要使用try...finally来保证一定正确的关闭了
        // InputStream input1 = null;
        // try {
        //     input1 = new FileInputStream("MyFile.java");
        //     int n;
        //     while ((n = input1.read()) != -1) { // 利用while同时读取并判断
        //         System.out.println(n);
        //     }
        // } finally {
        //     if (input1 != null) { input1.close(); }
        // }

        //因为InputStream和OutputStream都实现了了java.langAutoCloseable接口，因此，都可以
        //使用try(resource)编译器可以自动调用finally并为我们自动关闭
        // try(InputStream input = new FileInputStream("MyFile.java"))
        // {
        //     int n;
        //     while((n=input.read())!=-1)
        //     {
        //         System.out.println(n);
        //     }
        // }
        // 缓冲,通过重载int read()来实现
        InputStream input = new FileInputStream("MyFile.java");
        byte[]buffer = new byte[1024];
        int n1;
        while((n1=input.read(buffer))!=-1)
        {
            System.out.println("read" +n1 +"bytes.");
        }
        // 用ByteArrayInputStream可以从byte[]data中读取字节
        byte[]data = {72,101,108,108,111,33};

        try(InputStream input1 = new ByteArrayInputStream(data))
        {
            System.out.println(readAsString(input1));
        }

        try(InputStream input2 = new FileInputStream("MyFile.java"))
        {
            System.out.println(readAsString(input2));
        }
        
    }
    //接受InputStream作为参数，这样既可以从Byte中读取字符串，又可以从ByteArrayInputStream中读取字符串
    //这就是面向对象程序设计的精髓！！！！！
    public static String readAsString(InputStream input) throws IOException
    {
        StringBuilder sb = new StringBuilder();
        int n;
        while((n=input.read())!=-1)
        {
            sb.append((char)n);
        }
        return sb.toString();
    }
 }
