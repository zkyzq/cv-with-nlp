import java.lang.Class;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @authur  zhangqizky
 * @apiNote 动态代理:java标准库提供了动态代理功能，允许在运行期动态创建一个接口的实例
 *          动态代理是通过proxy创建代理对象，然后将接口方法代理给invoationHandler完成的
 */


public class Reflect6
{
    public static void main(String[]args)
    {
        InvocationHandler handler = new InvocationHandler(){
            @Override
            public Object invoke(Object proxy,Method method,Object[]args) throws Throwable
            {
                System.out.println(method);
                if (method.getName().equals("morning"))
                {
                    System.out.println("Good morning, " + args[0]);
                }
                return null;
            }
        };

        Hello hello = (Hello) Proxy.newProxyInstance(Hello.class.getClassLoader(), new Class[] {Hello.class}, handler);
        hello.morning("Bob");
    }
}

interface Hello
{
    void morning(String name);
}
