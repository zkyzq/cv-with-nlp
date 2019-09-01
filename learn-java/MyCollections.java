
import java.util.*;
/**
 * @author   zhangqizky
 * @apiNote  使用集合类，util中提供的一系列静态方法，更方便的操作各种集合
 */


public class MyCollections
{
    public static void main(String[]args)
    {
        List<String>list = new ArrayList<>();
        list.add("apple");
        list.add("pear");
        list.add("orange");

        System.out.println(list);
        Collections.sort(list);
        System.out.println(list);
        Collections.shuffle(list);
        System.out.println(list);
        List<String>immutable = Collections.unmodifiableList(list);
        immutable.add("banana");
        
        //线程安全集合
        
    }
}
