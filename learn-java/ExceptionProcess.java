import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * @authur   :tangxi.zq
 * @Note     :Java使用异常来表示错误，并通过try。。。catch来捕获
 *            一个函数定义的时候如果抛出了异常，那么调用的时候是一定要捕获的，不在调用的时候捕获，就要在最高层捕获，否则编译会报错。
 *            Error是无需捕获的严重错误，Exception是应该捕获的可以处理的错误
 *            RuntimeException无需强制捕获，非RuntimeException 需要强制捕获，或者用throws声明
 */

public class ExceptionProcess
{
    public static void main(String[]args) 
    {
        byte[] bs = toGBK("中文");
        System.out.println(Arrays.toString(bs));
    }
    static byte[] toGBK(String s) 
    {
        //如果不在这使用try语句，可以在函数名后抛出异常,这样上层在调用的时候就必须捕获
        try
        {
            return s.getBytes("GBK");
        }catch (UnsupportedEncodingException e)
        {
            //这里应该要处理，但如果不知道怎么处理至少要把异常栈打印出来
            e.printStackTrace();
        }
        return null;
    }
}
