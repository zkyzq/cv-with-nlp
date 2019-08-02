import java.util.ArrayList;

/**
 * @authur    zhangqizky
 * @apiNote    泛型模板:实现编写一次，万能匹配，又通过编译器保证类型安全：这就是泛型
 */


 public class Tamplate
 {
     public static void main(String[]args)
     {
         ArrayList<String> strList = new ArrayList<String>();
         ArrayList<Float> floatList = new ArrayList<Float>();

         strList.add("Hello");
         String s = strList.get(0);
         //编译报错
        //  strList.add(new Integer(123));
         //编译报错 
        //  Integer n = strList.get(0);


        //标准库中ArrayList<T>实现了List<T>接口，它可以向上转型为List<T>。即public class ArrayList<T> implements List<T> {
    //...
//}

        List<Integer> list = new ArrayList<Integer>();


        //但是不能把ArrayList<Integer>向上转型为ArrayList<Number>或List<Number>
     }
 }
