import java.util.*;

/**
 * @authur    zhangqizky
 * @apiNote   使用集合，删除重复的元素
 */

 public class MySet
 {
    public static void main(String[]args)
    {
        List<Message> received = List.of(
            new Message(1,"Hello!"), 
            new Message(2,"发工资了吗？"), 
            new Message(2, "发工资了吗？"),
            new Message(3, "去哪吃饭？"),
            new Message(3, "去哪吃饭？"),
            new Message(4, "Bye")
        );
        List<Message> displayMessages = process(received);
        for (Message message : displayMessages)
        {
            System.out.println(message.text);
        }
    }
    static List<Message> process(List<Message>received)
    {
        Set<Message> temp = new HashSet<Message>();
        for (Message m:received)
        {
            temp.add(m);
        }
        List<Message>res = new ArrayList<Message>();
        for(Message m:temp)
        {
            res.add(m);
        }
        return res;
    }
 }

 class Message
 {
     public final int sequence;
     public final String text;
     public Message(int sequence,String text)
     {
         this.sequence = sequence;
         this.text = text;
     }

     /**
      * 正确实现equals方法和hashCode方法
      */
     @Override
     public boolean equals(Object o)
     {
         if(o instanceof Message)
         {
            Message m = (Message)o;
            return (this.sequence == m.sequence) && Objects.equals(this.text,m.text);
         }
         return false;
     }
     @Override
     public int hashCode()
     {
        int h = 0; 
        h = 31 * h + text.hashCode();
        h = 31 * h + sequence;
        return h;
     }
 }
