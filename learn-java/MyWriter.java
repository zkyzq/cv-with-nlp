
import java.io.*;
import java.io.CharArrayWriter;

/** 
 * @author    zhangqizky
 * @apiNote    Writer的用法
*/


public class MyWriter
{
    public static void main(String[]args) throws Exception
    {
        try(Writer w = new FileWriter("readme.txt"))
        {
            w.write('H');
            w.write("Hello".toCharArray());
            w.write("Hello");
        }
        try (CharArrayWriter writer = new CharArrayWriter()) {
            writer.write(65);
            writer.write(66);
            writer.write(67);
            char[] data = writer.toCharArray(); // { 'A', 'B', 'C' }
        }
        //OutputStreamWriter就是一个将任意的OutputStream转换为Writer的转换器
        try (Writer writer = new OutputStreamWriter(new FileOutputStream("readme.txt"), "UTF-8")) {
            writer.write("World");
        }

    }
}
