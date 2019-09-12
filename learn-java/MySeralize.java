import java.io.*;
import java.util.Arrays;

/** 
 * @authur    zhangqizky
 * @apiNote   序列化与反序列化，将java对象变成二进制内容，即byte[]数组.
 * 可序列化的Java对象必须实现java.io.Serializable接口，类似Serializable这样的空接口被称为“标记接口”（Marker Interface）；

反序列化时不调用构造方法，可设置serialVersionUID作为版本号（非必需）；

Java的序列化机制仅适用于Java，如果需要与其它语言交换数据，必须使用通用的序列化方法，例如JSON。
*/

public class MySeralize
{
    public static void main(String[]args)throws IOException,ClassNotFoundException
    {
        //序列化
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try(ObjectOutputStream os = new ObjectOutputStream(buffer))
        {
            os.writeInt(12345);
            os.writeUTF("Hello");
            os.writeObject(Double.valueOf(123.456));
        }
        System.out.println(Arrays.toString(buffer.toByteArray()));

        ByteArrayInputStream bufferin = new ByteArrayInputStream(buffer.toByteArray());
        //反序列化,反序列化时，由JVM直接构造出Java对象，不调用构造方法，构造方法内部的代码，在反序列化时根本不可能执行
        try(ObjectInputStream in = new ObjectInputStream(bufferin))
        {
            int n = in.readInt();
            String s = in.readUTF();
            Double d = (Double)(in.readObject());
            System.out.println(n);
            System.out.println(s);
            System.out.println(d);
        }
    }
}
