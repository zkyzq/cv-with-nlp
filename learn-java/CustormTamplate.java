import java.util.*;

/**
 * @authur    zhangqizky
 * @apiNote   自己编写泛型
 */


class Pair<T>
{
    private T first;
    private T last;

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
    // 无法使用静态方法，必须改成另一种泛型类型
    public static <K> Pair<K> create(K first,K last)
    {
        return new Pair<K>(first,last);
    }

}

//多个泛型类型
class Pair2<T,K>
{
    private T first;
    private K last;

    public Pair2(T first,K last)
    {
        this.first = first;
        this.last = last;
    }
    public T getFirst(){return this.first;}
    public K getLast(){return this.last;}
}
public class CustormTamplate
{
    public static void main(String[]args)
    {
        Pair2<String,Integer> p = new Pair2<>("test",123);
        System.out.println(p.getFirst() + "," + p.getLast());
    }
}
