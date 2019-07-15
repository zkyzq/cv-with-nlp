/**
 * @author :    tangxi.zq
 * @apiNote:     断言是一种调试方式，断言失败会抛出AssertionError错误，并且程序会停止运行，因此断言只适合调试和测试，正式运行的时候还是抛出异常比较靠谱
 *               
 */

public class Assert
{
    public static void main(String[]args)
    {
        int x = -1;
        assert x>0 : "x must > 0";
        System.out.println(x);
    }
}
