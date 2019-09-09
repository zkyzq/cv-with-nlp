import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
/**
 * @authur    zhangqizky
 * @apiNote  操作zip:ZipInputStream和JarInputStream
 */

 public class MyZip
 {
     public static void main(String[]args)throws Exception
     {
         /**
          * @从zip文件中读取
          */
        try(ZipInputStream zip = new ZipInputStream(new FileInputStream("day02.zip")))
        {
            ZipEntry entry = null;
            while((entry = zip.getNextEntry()) != null)
            {
                String name = entry.getName();
                if(!entry.isDirectory())
                {
                    int n;
                    while((n=zip.read())!=-1)
                    {
                        System.out.println(n);
                    }
                }
            }

        }
        /**
         * @写入Zip文件
         */
        try(ZipOutputStream zip = new ZipOutputStream(new FileOutputStream("out.zip")))
        {
            File[]files = {new File("MyFile.java"),new File("MyFilter.java")};
            for (File f :files)
            {
                zip.putNextEntry(new ZipEntry(f.getName()));
                zip.write(getFileDataAsBytes(f));
                zip.closeEntry();
            }
        }
     }
     public static byte[] getFileDataAsBytes(File f)
     {
         byte[] data = new byte[1024*1024];
         try(InputStream is =new FileInputStream(f))
         {
             int n;
             while((n=is.read(data))!=-1)
             {
                 System.out.println("read"+n+"bytes");
             }

         }catch(IOException e)
         {
             e.printStackTrace();
         }
         return data;
     }
 }
