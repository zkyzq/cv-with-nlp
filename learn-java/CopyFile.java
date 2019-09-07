import java.io.*;

/**
 * @author   zhangqizky
 * @apiNote  实现复制文件的功能“
 * @param1:  source.txt
 * @param2:  copy.txt
 */

 public class CopyFile
 {
     public static void main(String[]args) throws IOException
     {
        // String source = "readme.txt";
        // String copy = "copy.txt";
        String source =args[0];
        String copy = args[1];
        InputStream input =new FileInputStream(source);
        OutputStream output = new FileOutputStream(copy);
        int n = 0;
        try {
            byte[] data = new byte[1024 * 1024];
            while((n=input.read(data))!=-1)
            {
                System.out.println("read"+n+"bytes.");
            }
            output.write(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            if(input != null)
            {
                input.close();
                output.close();
            }
        }
     }
 }
