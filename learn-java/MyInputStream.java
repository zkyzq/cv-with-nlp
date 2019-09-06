import java.io.*;

/**
 * @author        zhangqizky
 * @apiNote       标准输入流InputStream
 */

 public class MyInputStream
 {
     public static void main(String[]args)throws Exception
     {
        InputStream input = new FileInputStream("MyFile.java");
        int i =0;
        for(;;)
        {
            int n = input.read();
            if (n==-1)
            {
                break;
            }
            System.out.println(n);
            i++;
        }
        System.out.println(i);
        input.close();
    }
 }
