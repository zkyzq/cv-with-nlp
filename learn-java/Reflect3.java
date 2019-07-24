import java.lang.Class;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

/**
 * @authur    zhangqi
 * @apiNote   getName()：返回方法名称，例如："getScore"；
              getReturnType()：返回方法返回值类型，也是一个Class实例，例如：String.class；
              getParameterTypes()：返回方法的参数类型，是一个Class数组，例如：{String.class, int.class}；
              getModifiers()：返回方法的修饰符，它是一个int，不同的bit表示不同的含义。
 */
public class Reflect3
{
    public static void main(String[]args) throws NoSuchMethodException,IllegalAccessException,InvocationTargetException
    {
        Class stdClass = Student.class;
        
        //获取public方法,第一个参数是method名字，第二个是参数
        System.out.println(stdClass.getMethod("getScore", String.class));
        //获取父类public方法
        System.out.println(stdClass.getMethod("getName"));

        //获取父类private方法
        System.out.println(stdClass.getDeclaredMethod("getGrade", int.class));


        //获取method并调用

         // String对象:
         String s = "Hello world";
         // 获取String substring(int)方法，参数为int:
         Method m = String.class.getMethod("substring", int.class);
         // 在s对象上调用该方法并获取结果:
         String r = (String) m.invoke(s, 6);//方法invoke就是调用该实例，参数第一个是实例对象，第二个是方法的可变参数，必须一致
         // 打印调用结果:
         System.out.println(r);
        
         // 调用静态方法，invoke的第一个参数始终为空
        // 获取Integer.parseInt(String)方法，参数为String:
        Method m1 = Integer.class.getMethod("parseInt", String.class);
        // 调用该静态方法并获取结果:
        // Integer n = (Integer) m1.invoke(null, "12345");
        // 打印调用结果:

        //调用非public方法，需要setAccessible(true)
        //reflect调用仍然符合多态的原则

        // 今日练习
        String name = "Xiao Ming";
		int age = 20;
		Person p = new Person();
        // TODO: 利用反射调用setName和setAge方法:
        Class p_c = Person.class;

        Method m_1 = p_c.getMethod("setName");
        Method m_2 = p_c.getMethod("setAge");
        m_1.invoke(p,name);
        m_2.invoke(p,age);
		System.out.println(p.getName()); // "Xiao Ming"
		System.out.println(p.getAge()); // 20
    }
}
class Student extends Person {
    public int getScore(String type) {
        return 99;
    }
    private int getGrade(int year) {
        return 1;
    }
}

class Person {

	private String name;
	private int age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
