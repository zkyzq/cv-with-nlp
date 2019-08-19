import java.util.HashMap;
import java.util.Map;

/**
 * @authur      : zhangqi
 * @apiNote     : 编写equals和hashCode
正确使用Map必须保证：

作为key的对象必须正确覆写equals()方法，相等的两个key实例调用equals()必须返回true；

作为key的对象还必须正确覆写hashCode()方法，且hashCode()方法要严格遵循以下规范：

如果两个对象相等，则两个对象的hashCode()必须相等；
如果两个对象不相等，则两个对象的hashCode()尽量不要相等。
即对应两个实例a和b：

如果a和b相等，那么a.equals(b)一定为true，则a.hashCode()必须等于b.hashCode()；
如果a和b不相等，那么a.equals(b)一定为false，则a.hashCode()和b.hashCode()尽量不要相等。
 */

 public class MyHash2
 {
     public static void main(String[]args)
     {
         //由于key也不一定是基础类型，比如string类型的key，这样，放入时的key和取用时的key不一定是同一个key，但还是可以正确取用，就是因为内部的比较采用的是equals比较
         String key1 = "a";
         Map<String,Integer> map1= new HashMap<>();
         System.out.println(map1.put(key1,123));
         String key2 = new String("a");
         System.out.println(map1.get(key1));
         System.out.println(map1.get(key2));
         System.out.println(key1==key2);
         System.out.println(key1.equals(key2));

     }
 }

 class Person
 {
     String firstName;
     String lastName;

     int age;

     /**
      * 编写equals和hashcode遵循的原则是：
      equals()用到的用于比较的每一个字段，都必须在hashCode()中用于计算；equals()中没有使用到的字段，绝不可放在hashCode()中计算。

      另外注意，对于放入HashMap的value对象，没有任何要求。
      由于扩容会导致重新分布已有的key-value，所以，频繁扩容对HashMap的性能影响很大。如果我们确定要使用一个容量为10000个key-value的HashMap，更好的方式是创建HashMap时就指定容量：
      相同的key可能得到相同的hashcode但是不可能得到相同的value，因为有List方法去解决冲突，但是解决冲突需要消耗时间
      所以，hashCode()方法编写得越好，HashMap工作的效率就越高。
      */
     @Override
     public int hashCode()
     {
         int h = 0;
         h = 31 * h + firstName.hashCode();
         h = 31 * h + lastName.hashCode();
         h = 31 * h + age;
         return h;
        // return Objects.hashCode(firstName,lastName,age);
     }
 }
