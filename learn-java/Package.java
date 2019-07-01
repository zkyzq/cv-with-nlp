
/**
 * @authur      :tangxi.zq
 * @Description :Java的数据类型分两种：

                基本类型：byte，short，int，long，boolean，float，double，char

                引用类型：所有class和interface类型

                实际上，因为包装类型非常有用，Java核心库为每种基本类型都提供了对应的包装类型：

                基本类型	对应的引用类型
                boolean	java.lang.Boolean
                byte	java.lang.Byte
                short	java.lang.Short
                int	java.lang.Integer
                long	java.lang.Long
                float	java.lang.Float
                double	java.lang.Double
                char	java.lang.Character

 */
public class Package
{
    public static void main(String[]args)
    {
        //int和Integer类型互相转换
        Integer n = null;
        Integer n2 = new Integer(99);
        int n3 = n2.intValue();


        //Auto Boxing和Auto Unboxing，编译器可以帮助我们在引用类型和基本类型之间隐式转换，但不要瞎用，因为可能指向空指针
        Integer n4 = 100; // 编译器自动使用Integer.valueOf(int)
        int x = n4; // 编译器自动使用Integer.intValue()

        //注意，所有的包装类型都是不变类，且比较的时候需要用equals而不是==
        Integer x1 = 127;
        Integer y = 127;
        Integer m = 99999;
        Integer n5 = 99999;
        System.out.println("x == y: " + (x==y)); // true，这个是true只是偶然
        System.out.println("m == n: " + (m==n)); // false
        System.out.println("x.equals(y): " + x1.equals(y)); // true
        System.out.println("m.equals(n): " + m.equals(n5)); // true

        //创建的时候使用静态方法去创建，而不是new操作符,前者返回的是缓存的实例以节省内存
        Integer n6 = Integer.valueOf(100);

        //进制转换，这里我们注意到程序设计的一个重要原则：数据的存储和显示要分离。
        int x3 = Integer.parseInt("100"); // 100
        int x2 = Integer.parseInt("100", 16); // 256,因为按16进制解析

        System.out.println(Integer.toString(100)); // "100",表示为10进制
        System.out.println(Integer.toString(100, 36)); // "2s",表示为36进制
        System.out.println(Integer.toHexString(100)); // "64",表示为16进制
        System.out.println(Integer.toOctalString(100)); // "144",表示为8进制
        System.out.println(Integer.toBinaryString(100)); // "1100100",表示为2进制

        //常用静态变量
        // boolean只有两个值true/false，其包装类型只需要引用Boolean提供的静态字段:
        Boolean t = Boolean.TRUE;
        Boolean f = Boolean.FALSE;
        // int可表示的最大/最小值:
        int max = Integer.MAX_VALUE; // 2147483647
        int min = Integer.MIN_VALUE; // -2147483648
        // long类型占用的bit和byte数量:
        int sizeOfLong = Long.SIZE; // 64 (bits)
        int bytesOfLong = Long.BYTES; // 8 (bytes)
        // 所有整数和浮点数的包装类型都继承自Number，所以可以方便的通过包装类型获取各种基本类型
        // 向上转型为Number:
        Number num = new Integer(999);
        // 获取byte, int, long, float, double:
        byte b = num.byteValue();
        int n7 = num.intValue();
        long ln = num.longValue();
        float f2 = num.floatValue();
        double d = num.doubleValue();

        //  转为无符号的整数
        int z = -1;
        int w = 127;
        System.out.println(Byte.toUnsignedInt(z)); // 255
        System.out.println(Byte.toUnsignedInt(w)); // 127

    }
}
// class Integer
// {
//     private int value;

//     public Integer(int value)
//     {
//         this.value = value;
//     }
//     public int intValue()
//     {
//         return this.value;
//     }
// }
