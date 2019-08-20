import java.util.*;

/**
 * @authur       :zhangqizky
 * @apiNote      :有序的hashmap-》treemap
 * 使用TreeMap时，放入的Key必须实现Comparable接口。String、Integer这些类已经实现了Comparable接口，因此可以直接作为Key使用。作为Value的对象则没有任何要求。

如果作为Key的class没有实现Comparable接口，那么，必须在创建TreeMap时同时指定一个自定义排序算法：
SortedMap在遍历时严格按照Key的顺序遍历，最常用的实现类是TreeMap；

作为SortedMap的Key必须实现Comparable接口，或者传入Comparator；

要严格按照compare()规范实现比较逻辑，否则，TreeMap将不能正常工作。
 */

 public class MyTreeMap
 {
     public static void main(String[]args)
     {
         Map<String,Integer> map = new TreeMap<>();
         map.put("orange", 1);
         map.put("apple",2);
         map.put("pear",3);
         for(String key:map.keySet())
         {
             System.out.println(key);
         }

         Map<Person,Integer> map1 = new TreeMap<>(new Comparator<Person>()
         {
             public int compare(Person p1,Person p2)
             {
                 return p1.name.compareTo(p2.name);
             }
         });
         map1.put(new Person("Tom"), 1);
         map1.put(new Person("Bob"), 2);
         map1.put(new Person("Lily"), 3);
         for (Person key : map1.keySet()) {
             System.out.println(key);
         }
         // {Person: Bob}, {Person: Lily}, {Person: Tom}
         System.out.println(map1.get(new Person("Bob"))); // 2

         Map<Student,Integer>map2 = new TreeMap<>(new Comparator<Student>()
         {
             public int compare(Student s1, Student s2)
             {
                 if (s1.score == s2.score)
                 {
                     return 0;
                 }
                 return s1.score > s2.score?-1:1;
             }
         });

         map2.put(new Student("Tom",77),1);
         map2.put(new Student("Bob", 66), 2);
         map2.put(new Student("Lily", 99), 3);
         for (Student key : map2.keySet()) {
             System.out.println(key);
         }
         System.out.println(map2.get(new Student("Bob", 66))); 

     }
 }


 class Person {
    public String name;
    Person(String name) {
        this.name = name;
    }
    public String toString() {
        return "{Person: " + name + "}";
    }
}

class Student {
    public String name;
    public int score;

    Student(String name, int score) {
        this.name = name;
        this.score = score;
    }
    public String toString() {
        return String.format("{%s: score=%d}", name, score);
    }
}

