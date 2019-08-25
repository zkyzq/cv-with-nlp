import java.util.*;

/**
 * @authur  zhangqizky
 * @apiNote 使用Queue,
 * 	            throw Exception	    返回false或null
添加元素到队尾	    add(E e)	       boolean offer(E e)
取队首元素并删除	 E remove()	        E poll()
取队首元素但不删除	 E element()	    E peek()

队列Queue实现了一个先进先出（FIFO）的数据结构：

通过add()/offer()方法将元素添加到队尾；
通过remove()/poll()从队首获取元素并删除；
通过element()/peek()从队首获取元素但不删除。

 */

 public class MyQueue
 {
     public static void main(String[]args)
     {
         Queue<String> q = new LinkedList<>();
         q.offer("qpple");
         q.offer("pear");
         q.offer("banana");

         //
        //  System.out.println(q.poll()); // apple
        //  System.out.println(q.poll()); // pear
        //  System.out.println(q.poll()); // banana
         System.out.println(q.poll()); // null,因为队列是空的

        // 队首永远都是apple，因为peek()不会删除它:
        System.out.println(q.peek()); // apple
        System.out.println(q.peek()); // apple
        System.out.println(q.peek()); // apple


     }
 }
