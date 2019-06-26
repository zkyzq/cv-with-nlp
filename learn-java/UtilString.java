import java.util.Arrays;
public class UtilString
{
    public static void main(String[]args)
    {
        String s= "Hello";
        System.out.println(s);

        //java字符串不可变，是指定义了之后在内存中不可变。下面的语句，是s所有的字符转为大写之后又重新赋给了s
        // s = s.toUpperCase();
        // System.out.println(s);
        //  下面代码能看出s不变
        String s1 = s.toUpperCase();
        System.out.println(s1);
        System.out.println(s);  //并没有变

        //字符串比较，一定要用equlas()方法而不能用==.一般将肯定不为空的放在前面。

        String s2 = "hello";
        String s3 = "hEllo".toLowerCase();
        String s4 = "hEllO";
        System.out.println(s2 == s3);
        System.out.println(s2.equals(s3));
        System.out.println(s2.equalsIgnoreCase(s4));

        // 是否包含子串:
        System.out.println("Hello".contains("ll")); // true
        "Hello".indexOf("l"); // 2
        "Hello".lastIndexOf("l"); // 3
        "Hello".startsWith("He"); // true
        "Hello".endsWith("lo"); // true

        //去除首尾空白trim()
        "  \tHello\r\n ".trim(); // "Hello"
        //strip()还可以去除类似中文的空格字符
        "\u3000Hello\u3000".strip(); // "Hello"
        " Hello ".stripLeading(); // "Hello "
        " Hello ".stripTrailing(); // " Hello"
        //拼接字符串
        String[] arr = {"A", "B", "C"};
        String s5 = String.join("***", arr); // "A***B***C"
        //分割字符串
        String s6 = "A,B,C,D";
        String[] ss = s.split("\\,"); // {"A", "B", "C", "D"}
        //转为char[],此时如果修改char数组，原字符串并不会变
        char[] cs = "Hello".toCharArray(); // String -> char[]
        String s7 = new String(cs); // char[] -> String
        //  从String不变的特性来看，如果传入的对象有可能改变，我们需要复制而不是直接引用
        int[] scores = new int[] { 88, 77, 51, 66 };
        Score sc = new Score(scores);
        sc.printScores();
        scores[2] = 99;
        sc.printScores();
    }
}
class Score {
    private int[] scores;
    public Score (int[]scores)
    {
        this.scores = scores.clone();
    }
    // public Score(int[] scores) {
    //     this.scores = scores;
    // }

    public void printScores() {
        System.out.println(Arrays.toString(scores));
    }
}