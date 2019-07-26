import java.lang.Class;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Reflect5
{
    public static void main(String[]args)throws Exception
    {
        /**
         * 三种获取类的类型方法
         */
        // Class s1 = Class.forName("java.lang.String");
        // Class s2 = String.class;
        // String s = "";
        // Class s3 = s.getClass();

        //获取父类的class

        // Class i = Integer.class;
        // Class n = i.getSuperclass();
        // System.out.println(n);

        // Class o = n.getSuperclass();
        // System.out.println(o);
        // System.out.println(o.getSuperclass());

        //获取接口,一个类可以实现一个或者多个接口
        Class s = Integer.class;
        Class[] is = s.getInterfaces();
        for (Class i : is) {
            System.out.println(i);
            System.out.println(i.getSuperclass());
        }

        //判断某个实例是否属于某个类型时
        Object n = Integer.valueOf(123);
        boolean isDouble = n instanceof Double;   //false
        boolean isInteger = n instanceof Integer; // true
        boolean isNumber = n instanceof Number; // true
        boolean isSerializable = n instanceof java.io.Serializable; // true


        // Integer i = ?
        System.out.println(Integer.class.isAssignableFrom(Integer.class)); // true，因为Integer可以赋值给Integer
        // Number n = ?
        System.out.println(Number.class.isAssignableFrom(Integer.class)); // true，因为Integer可以赋值给Number
        // Object o = ?
        Object.class.isAssignableFrom(Integer.class); // true，因为Integer可以赋值给Object
        // Integer i = ?
        System.out.println((Integer.class.isAssignableFrom(Number.class))); // false，因为Number不能赋值给Integer
    }
}
