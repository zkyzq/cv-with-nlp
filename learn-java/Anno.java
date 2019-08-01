package annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author   zhangqi
 * @apiNote   正确的使用注解
 */



public class Anno
{
    public static void main(String[]args)
    {
        System.out.println("ok");
    }
}

/**
 * @author tangxi
 */
@Target({ElementType.TYPE,ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@interface Report {
    int type() default 0;
    String level() default "info";
    String value() default "";
}
/**
 * @author tangxi
 */
@Target({ElementType.TYPE,ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@interface Check
{
    int value() default 0;
    int min() default Integer.MIN_VALUE;
    int max() default Integer.MAX_VALUE;
}

/**
 * @author tangxi
 */
@Target({ElementType.TYPE,ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@interface Resource
{
    String value() default "info";
}

/**
 * @author tangxi
 */
@Target({ElementType.FIELD,ElementType.METHOD})
@interface Inject
{}

/**
 * @author tangxi
 */
@Target({ElementType.FIELD,ElementType.METHOD})
@interface PostConstruct
{}

/**
 * @author tangxi
 */
@interface Param
{}
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

    @Check(99)
    public int x;

    @Check
    public int y;
}
