import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class MyPriorityQueue
{
    public static void main(String[]args)
    {
        Queue<User> q = new PriorityQueue<>(new UserComparator());
        q.offer(new User("Bob", "A1"));
        q.offer(new User("Alice", "A2"));
        q.offer(new User("Boss", "V1"));
        System.out.println(q.poll()); // Boss/V1
        System.out.println(q.poll()); // Bob/A1
        System.out.println(q.poll()); // Alice/A2
        System.out.println(q.poll()); // null,因为队列为空
    }
}
class UserComparator implements Comparator<User>
{
    public int compare(User u1,User u2)
    {
        if(u1.number.charAt(0) == u2.number.charAt(0))
        {
            return u1.number.compareTo(u2.number);
        }
        //u1的号码是V开头，优先级高
        if(u1.number.charAt(0) == 'V')
        {
            return -1;
        }
        else
        {
            return 1;
        }
    }
}

class User
{
    public final String name;
    public String number;
    public User(String name,String number)
    {
        this.name = name;
        this.number = number;
    }
    public String toString()
    {
        return name + "/" + number;
    }
}
