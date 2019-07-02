/**
 * @authur         :tangxi.zq
 * @Note           : 私有属性加public 读写方法称为java的一种设计，JavaBean，IDE中支持自动补全。
 */

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

public class JavaBean
{
    public static void main(String [] args)
    {
        BeanInfo info = Introspector.getBeanInfo(Person.class);
        for(PropertyDescriptor pd: info.getPropertyDescriptors())
        {
            System.out.println(pd.getName());
            System.put.println(" "+pd.getReadMethod());
            System.out.println(" "+pd.getWriteMethod());
        }
    }
}
class Person
{
    private String name;
    private int age;

    public String getName()
    {
        return this.name;
    }
    public int getAge()
    {
        return this.age;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public void setAge(int age)
    {
        this.age = age;
    }
}
