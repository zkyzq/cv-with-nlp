
import java.lang.annotation.*;
import java.utils.*;
/**
 * @author   zhangqi
 * @apiNote   正确的使用注解
 */

@Resource("hello")
class Hello {
    @Inject
    int n;

    @PostConstruct
    public void hello(@Param String name) {
        System.out.println(name);
    }

    @Override
    public String toString() {
        return "Hello";
    }

    @Check(min=0, max=100, value=55)
    public int m;

    @Check(value=99)
    public int p;

    @Check(99) // @Check(value=99)
    public int x;

    @Check
    public int y;
}
public class Annotation
{
    public static void main(String[]args)
    {

    }
}
