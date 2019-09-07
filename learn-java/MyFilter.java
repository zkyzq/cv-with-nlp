import java.io.*;

/**
 * @author zhangqizky
 * @apiNote :Filter模式：
 * 通过一个“基础”组件再叠加各种“附加”功能组件的模式，称之为Filter模式（或者装饰器模式：Decorator）。它可以让我们通过少量的类来实现各种功能的组合：
 * Java的IO标准库使用Filter模式为InputStream和OutputStream增加功能：

可以把一个InputStream和任意个FilterInputStream组合；

可以把一个OutputStream和任意个FilterOutputStream组合。
 */
public class MyFilter
{
    public static void main(String[]args) throws Exception
    {
        byte[] data = "hello,world".getBytes("UTF-8");
        try(CountInputStream input = new CountInputStream(new ByteArrayInputStream(data)))
        {
            int n;
            while((n=input.read())!=-1)
            {
                System.out.println((char)n);
            }
            System.out.println("Total read" + input.getBytesRead() + "bytes");
        }
    }
}
class CountInputStream extends FilterInputStream
{
    private int count = 0; 
    CountInputStream(InputStream in)
    {
        super(in);
    }
    public int getBytesRead()
    {
        return this.count;
    }
    @Override
    public int read() throws IOException
    {
        int n = in.read();
        if (n!=-1)
        {
            this.count++;
        }
        return n;
    }
    public int read(byte[] b,int off,int len) throws IOException
    {
        int n = in.read(b,off,len);
        this.count++;
        return n;
    }
}

