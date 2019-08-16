import java.util.HashMap;
import java.util.Map;

/**
 * @authur      : tangxi
 * @apiNote     :使用Map:通过上述代码可知：
 * Map<K, V>是一种键-值映射表，当我们调用put(K key, V value)方法时，就把key和value做了映射并放入Map。
 * 当我们调用V get(K key)时，就可以通过key获取到对应的value。如果key不存在，则返回null。和List类似，Map也是一个接口，最常用的实现类是HashMap。
 *  始终牢记：Map中不存在重复的key，因为放入相同的key，只会把原有的key-value对应的value给替换掉。
 *  遍历Map时，不可假设输出的key是有序的！
 */


 public class MyMap
 {
     public static void main(String[]args)
     {
        Student s = new Student("Xiao Ming",99);
        Map<String,Student>map = new HashMap<>();
        map.put("Xiao Ming",s);
        Student target = map.get("Xiao Ming");
        System.out.println(s==target);//是同一个实例，等号判断都是相等的
        Student another = map.get("Bob");
        System.out.println(another); 

        //遍历Map
        Map<String, Integer> map2 = new HashMap<>();
        map2.put("apple", 123);
        map2.put("pear", 456);
        map2.put("banana", 789);
        //只遍历key，然后获取value
        for (String key : map2.keySet()) {
            Integer value = map2.get(key);
            System.out.println(key + " = " + value);
        }
        // 同时遍历key和value
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println(key + " = " + value);
        }
     }
 }

 class Student {
    public String name;
    public int score;
    public Student(String name, int score) {
        this.name = name;
        this.score = score;
    }
}
