import java.util.List;

/** 
因此，我们总结一下equals()方法的正确编写方法：

先确定实例“相等”的逻辑，即哪些字段相等，就认为实例相等；
用instanceof判断传入的待比较的Object是不是当前类型，如果是，继续比较，否则，返回false；
对引用类型用Objects.equals()比较，对基本类型直接用==比较。
使用Objects.equals()比较两个引用类型是否相等的目的是省去了判断null的麻烦。两个引用类型都是null时它们也是相等的。

如果不调用List的contains()、indexOf()这些方法，那么放入的元素就不需要实现equals()方法。
*/

public class MyEqual
{
    public static void main(String[]args)
    {
        List<Person> list = List.of(
            new Person("Xiao", "Ming", 18),
            new Person("Xiao", "Hong", 25),
            new Person("Bob", "Smith", 20)
        );
        boolean exist = list.contains(new Person("Bob", "Smith", 20));
        System.out.println(exist ? "测试成功!" : "测试失败!");
    }


}

class Person
{
    String firstName;
    String lastName;
    int age;
    public Person(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }
    public boolean equals (Object o)
    {
        if(o instanceof Person)
        {
            Person p = (Person) o;
            if(this.firstName!=null&&this.lastName!=null&& this.age!=0)
            {
                return this.firstName.equals(p.firstName) && this.lastName.equals( p.lastName) && this.age == p.age;
            }
        }
        return false;
    }
}
