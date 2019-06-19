/*
*****************************************************************************
* @authur      : tangxi.zq
* @time        : 2019-06-19
* @file         : 静态字段和静态方法
* @Description : 静态字段是一个类所有实例共享的字段，无论修改哪个实例的静态字段，效果都是一样的。
                 静态方法不需要实例变量，通过类名就可以调用。静态方法类似于其他编程语言的函数，静态方法内部无法访问实例字段，只能访问静态字段。
                 interface接口不能定义实例字段，但是可以定义静态字段。但是必须是final类型.
                 
                 
******************************************************************************
*/

class MemberNum
{
    public static void main(String[]args)
    {
        // Person ming = new Person("Xiao Ming", 12);
        // Person hong = new Person("Xiao Hong", 15);
        // // ming.number = 88;
        // Person.number = 88;
        // System.out.println(hong.number);
        // hong.number = 99;
        // System.out.println(ming.number);

        // TODO: 给Person类增加一个静态字段count和静态方法getCount，统计实例的个数
		Person p1 = new Person("小明");
		System.out.println(Person.getCount()); // 1
		Person p2 = new Person("小红");
		System.out.println(Person.getCount()); // 2
		Person p3 = new Person("小军");
		System.out.println(Person.getCount()); // 3
    }
}

class Person
{
    public String name;
    public int age;

    public static int number;
    public static int count;

    public Person(String name,int age)
    {
        this.name = name;
        this.age = age;
        count += 1;
    }
    public Person(String name)
    {
        this.name = name;
        count += 1;
    }
    public static int getCount()
    {
        return count;
    }
}