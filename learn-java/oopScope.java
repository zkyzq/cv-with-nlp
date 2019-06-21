/**
 * @ authur      : tangxi.zq
 * @ time        : 2019-06-21
 * @ apiNote     : 1. 一个.java文件只能有一个public类，且这个类的类名必须与文件名保持一致
 *                 2. 一个类或者类中变量不用public，protected，private修饰的，则其作用域为整个包。
 *                 3. public修饰的可以被任何其他类访问，protected主要是作用于继承关系，子类或子类的子类可以访问。private只能在类中访问
 *                 4. 局部变量的定义应遵循最小可用原则
 *                 5. final修饰的字段无法修改，修饰的类无法继承，修饰的方法无法重写。
 *                 6. 写的时候应该先写public，再写private，因为看的时候会先关注一个类暴露给外部的字段和方法。
 */


public class oopScope    //和文件名oopScope保持一致，不然报错。
{
    
}

class Person   // 不能再用public修饰
{
    final int max_age = 110; //无法再重新赋值
    private int age;//类作用域
    public String name;

    protected void hi()  //子类无法覆写
    {
        System.out.println("Hi, "+this.name);
    }

}