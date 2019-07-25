import java.lang.Class;
import java.lang.reflect.Field;
import java.lang.reflect.Constructor;


/**
 * @authur     zhangqi
 * @apiNote    使用反射来调用构造方法，从字段到method到构造方法
 *             getConstructor(Class...)：获取某个public的Constructor；
               getDeclaredConstructor(Class...)：获取某个Constructor；
               getConstructors()：获取所有public的Constructor；
               getDeclaredConstructors()：获取所有Constructor。
 */
public class Reflect4
{
    public static void main(String[]args) throws Exception
    {
        Person p = new Person();
        //只能使用该类的public无参数构造方法
        Person p2 = Person.class.newInstance();
        //Constructor方法Integer(int)
        Constructor cons1 = Integer.class.getConstructor(int.class);
        Integer n1 = (Integer) cons1.newInstance(123);
        System.out.println(n1);
        //获取构造方法Integer(String)
        Constructor cons2 = Integer.class.getConstructor(String.class);
        Integer n2 = (Integer)cons2.newInstance("456");
        System.out.println(n2);
    }
}
class Person
{
    public String name;
    public int age;
    public Person(){}

    public Person(String name,int age)
    {
        this.name = name;
        this.age = age;
    }
}
