import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
/**
 * @authur zhangqi
 * @apiNote 在接口中使用泛型
 */

public class UsingTamplate
{
    public static void main(String[]args)
    {
        List<String> list = new ArrayList();
        list.add("Hello");
        list.add("World");
        //String 本身已经实现了Comparable<String>接口
        String[]ss = new String[]{"Orange", "Apple", "Pear"};
        Arrays.sort(ss);


        Person []ps = new Person[]{
        new Person("Bob", 61),
        new Person("Alice", 88),
        new Person("Lily", 75),};
        Arrays.sort(ps);
        System.out.println(Arrays.toString(ps));
    }
}
class Person implements Comparable<Person>
{
    public String name;
    public int score;

    public Person(String name,int score)
    {
        this.name = name;
        this.score = score;
    }
    public String toString()
    {
        return this.name + "," + this.score;
    }
    public int compareTo(Person other)
    {
        return this.name.compareTo(other.name);
    }
}
