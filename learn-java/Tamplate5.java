package Tamplate;

public class Tamplate5 {
    public static void main(String []args)
    {
        Pair<Integer> p = new Pair<>(123,123);
        //无法直接将Pair<Integer>转为Pair<Number>，前者不是后者的子类，虽然Integer是Number的子类，需要使用extends通配符
        //有没有办法使得方法参数接受Pair<Integer>？办法是有的，这就是使用Pair<? extends Number>使得方法接收所有泛型类型为Number或Number子类的Pair类型
        int n =add(p);
        System.out.println(n);

    }
    static int add(Pair<?extends Number>p)
    {
        Number first = p.getFirst();
        Number last = p.getLast();
        //即使使用通配符也无法进行写入操作。
//        p.setFirst(new Integer(first.intValue() + 100));
//        p.setLast(new Integer(last.intValue() + 100));

        return p.getFirst().intValue()+p.getLast().intValue();
    }

}

