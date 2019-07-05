/**
 * @ authur  ：tangxi.zq
 * @ time    : 2019-07-05
 */

//和BigInteger类似，BigDecimal用来精确表示多位小数，源码中的实现为一个BigInteger和一个scale来实现的，前者表示数值，后者表示小数位数
import java.math.*;

public class BigDec
{
    public static void main(String[]args)
    {
        BigDecimal bd = new BigDecimal("123.456");
        System.out.println(bd.multiply(bd)); // 15241.55677489
        BigDecimal d1 = new BigDecimal("123.45");
        BigDecimal d2 = new BigDecimal("123.4500");
        BigDecimal d3 = new BigDecimal("1234500");
        System.out.println(d1.scale()); // 2,两位小数
        System.out.println(d2.scale()); // 4
        System.out.println(d3.scale()); // 0

        //通过stripTrailingZeros()方法，可以将一个BigDecimal格式化为一个相等的，但去掉了末尾0的BigDecimal
        d2 = d2.stripTrailingZeros();
        System.out.println(d2);
        //如果scale返回负数，例如，-2，表示这个数是整数，并且末尾有2个0
        BigDecimal d4 = new BigDecimal("1234500");
        System.out.println(d4.scale());

        //截断和四舍五入
        BigDecimal d5 = new BigDecimal("123.456789");
        BigDecimal d6 = d5.setScale(4,RoundingMode.HALF_UP);//四舍五入
        BigDecimal d7 = d5.setScale(4,RoundingMode.DOWN);

        //比较必须用compareTo(),使用equals不仅要求值相等，还要求scale相等，因为equals是对象类实例统一的比较方法，要求对象的实例字段相等
        BigDecimal d8 = new BigDecimal("123.456");
        BigDecimal d9 = new BigDecimal("123.45600");
        System.out.println(d8.equals(d9)); // false,因为scale不同
        System.out.println(d8.equals(d9.stripTrailingZeros())); // true,因为d2去除尾部0后scale变为2
        System.out.println(d8.compareTo(d9)); // 0
    }
}
