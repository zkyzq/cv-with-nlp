package Iterator;

import java.util.*;

/**
 * @author    zhangqizky
 * @apiNote    迭代器，提供统一的遍历接口
 */

public class MyIterator
{
    public static void main(String[]args)
    {
        ReverseList<String> rlist = new ReverseList<String>();
        rlist.add("Apple");
        rlist.add("Orange");
        rlist.add("Pear");
        for(String s:rlist)
        {
            System.out.println(s);
        }
    }
}
class ReverseList<T> implements Iterable<T>
{
    List<T> list = new ArrayList<T>();
    public void add(T t)
    {
        list.add(t);
    }
    @Override
    public Iterator<T> iterator()
    {
        //正序遍历
        //return new ReverseIterator(0);
        return new ReverseIterator(list.size());
    }
    class ReverseIterator implements Iterator<T>
    {
        int index;
        ReverseIterator(int index)
        {
            this.index = index;
        }
        @Override
        public boolean hasNext()
        {
//            return index < ReverseList.this.list.size();
            return index > 0;
        }
        @Override
        public T next()
        {
//            index ++;
            index--;
            return ReverseList.this.list.get(index);
        }
    }
}
