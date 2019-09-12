import java.io.*;

/**
 * 
 */

 public class MyReader
 {
     public static void main(String[]args)
     {
         try {
             readFile();
         } catch (IOException e) {
             e.printStackTrace();
         }

         //CharReader
         try (Reader reader = new CharArrayReader("Hello".toCharArray())) {
        }
        //StringReader
        try (Reader reader = new StringReader("Hello")) {
        }
        //InputStreamReader
        // 持有InputStream:
        InputStream input = new FileInputStream("MyFile.java");
        // InputStream 变换为Reader:
        Reader reader = new InputStreamReader(input, "UTF-8");
     }
     public static void readFile() throws IOException
     {
        // Reader reader = new FileReader("MyFile.java");    //明确编码，在mac上是UTF-8编码，但是windows或者其他文本编辑器不一定是utf-8
        // for(;;)
        // {
        //     int n = reader.read();
        //     if(n == -1)
        //     {
        //         break;
        //     }
        //     System.out.print((char)n);
        // }
        // //必须要关闭！！！
        // reader.close();

        try (Reader reader = new FileReader("MyFile.java")) {
            char[] buffer = new char[1000];
            int n;
            while ((n = reader.read(buffer)) != -1) {
                System.out.println("read " + n + " chars.");
            }
        }
     }
 }
