
// /**
//  * @authur    
//  * @apiNote    Class 类。java中除了int等基本类型，其他都是class。class的本质是数据类型(type).jvm动态加载classpath
//  * 
//  */
// public class ClassDemo
// {
//     public static void main(String args[])
//     {
//         try{
//         Number b = new Double(123.456);
//         // String s = new Double(123.456);
//         }catch (IllegalArgumentException e)
//         {
//             e.printStackTrace();
//         }

//         // // Class ls = new Class(String);   自己的程序无法创建Class 实例，因为构造函数是私有的
//         // //那么如何获取？通过静态变量class获取
//         // Class cls = String.class;
//         // //通过实例获取
//         // String s = "Hello";
//         // Class cls2  = s.getClass();
//         // // 方法三：如果知道一个class的完整类名，可以通过静态方法Class.forName()获取：
//         // Class cls3 = Class.forName("java.lang.String");
//         // System.out.println(cls == cls2);
//         Integer n = new Integer(123);

//         boolean b3 = n instanceof Integer; // true
//         boolean b4 = n instanceof Number; // true

//         boolean b1 = n.getClass() == Integer.class; // true
//         boolean b2 = n.getClass() == Number.class; // false

        
//     } 
// }

public class ClassDemo {
    public static void main(String[] args) {
        printClassInfo("".getClass());
        printClassInfo(Runnable.class);
        printClassInfo(java.time.Month.class);
        printClassInfo(String[].class);
        printClassInfo(int.class);
    }

    static void printClassInfo(Class cls) {
        System.out.println("Class name: " + cls.getName());
        System.out.println("Simple name: " + cls.getSimpleName());
        if (cls.getPackage() != null) {
            System.out.println("Package name: " + cls.getPackage().getName());
        }
        System.out.println("is interface: " + cls.isInterface());
        System.out.println("is enum: " + cls.isEnum());
        System.out.println("is array: " + cls.isArray());
        System.out.println("is primitive: " + cls.isPrimitive());
    }
}
