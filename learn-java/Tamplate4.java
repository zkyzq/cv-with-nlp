package Tamplate;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class Tamplate4 {
    public static void main(String []args)
    {
        /**
         * 因为java的泛型是通过擦拭法实现的，所以有以下三点局限：
         * (1)<T>不能是基本类型
         */
//        Pair<String> p1 = new Pair<>("Hello","world");
//        Pair<Integer> p2 = new Pair<>(123,456);
//        Class c1 = p1.getClass();
//        Class c2 = p2.getClass();
//        System.out.println(c1==c2);
//        System.out.println(c1==Pair.class);

        /**
         * 在继承了泛型类型的情况下，子类可以获取父类的泛型类型
         */

        Class<IntPair> clazz = IntPair.class;
        Type t = clazz.getGenericSuperclass();
        if(t instanceof ParameterizedType)
        {
            ParameterizedType pt = (ParameterizedType) t;
            Type[] types = pt.getActualTypeArguments();
            Type firstType = types[0];
            Class<?> typeclass = (Class<?>)firstType;
            System.out.println(typeclass);
        }

    }
}

class Pair<T>
{
    private T first;
    private T last;
    public Pair(){}
    public Pair(T first,T last)
    {
        this.first = first;
        this.last = last;
    }
    public T getFirst()
    {
        return this.first;
    }
    public T getLast()
    {
        return this.last;
    }
}

class IntPair extends Pair<Integer>
{
    public IntPair(Integer first,Integer last)
    {
        super(first,last);
    }
}
