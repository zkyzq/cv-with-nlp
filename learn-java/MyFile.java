import java.io.*;
import java.nio.file.*;
/**
 * @author    zhangqizky
 * @apiNote   文件的相关操作
 */

public class MyFile
{
    public static void main(String[]args) throws Exception
    {
        // File f = new File("myfile.exe");
        // System.out.println(f);
        // /**
        //  * 既可传入绝对路径，也可传入相对路径
        //  */
        // File f1 = new File("test.txt");
        // System.out.println(f1.getAbsolutePath());
        // System.out.println(f1.getCanonicalPath());
        // System.out.println(f1.isFile());
        // System.out.println(f1.isDirectory());
        // System.out.println(f1.createNewFile());

        // //临时文件
        // File f2 = File.createTempFile("tmp-",".txt");
        // f2.deleteOnExit();
        // System.out.println(f2.getAbsolutePath());
        // System.out.println(f2.isFile());

        // //遍历文件夹和目录

        // File f3 = new File("/Users/tangxi/27-days-java");
        // File[]fs1 = f3.listFiles();
        // printFiles(fs1);
        // File[] fs2 = f3.listFiles(new FilenameFilter() {
        //     public boolean accept(File dir,String name)
        //     {
        //         return name.endsWith(".java");
        //     }
        // });
        // printFiles(fs2);



        // // Path的使用
        // Path p1 = Paths.get(".","project","study");
        // System.out.println(p1);
        // Path p2 = p1.toAbsolutePath(); // 转换为绝对路径
        // System.out.println(p2);
        // Path p3 = p2.normalize(); // 转换为规范路径
        // System.out.println(p3);
        // File f4 = p3.toFile(); // 转换为File对象
        // System.out.println(f4);
        // for (Path p : Paths.get("..").toAbsolutePath()) { // 可以直接遍历Path
        //     System.out.println("  " + p);
        // }

        File f = new File("/Users/tangxi/27-days-java");
        printTree(f,"\t");
        
    }
    static void printTree(File file,String c)
    {
        if(file.isDirectory())
        {
            System.out.println(c+file.getName());
        }
        File[]files = file.listFiles();
        for(File f:files)
        {
            String temp = c+"\t";
            if(f.isDirectory())
            {
                printTree(f,temp);
            }
            else
            {
                System.out.println(temp + f.getName());
            }
        }
    }
    static void printFiles(File[]files)
    {
        System.out.println("=====================");
        if(files!=null)
        {
            for(File f:files)
            {
                System.out.println(f);
            }
        }
        System.out.println("=====================");
    }
}
