/**
 * @authur: tangxi.zq
 * @ note Java工具类
 */
import java.lang.Math;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;
import java.security.NoSuchAlgorithmException;
 public class Tool
 {
     public static void main(String args[])
     {
        Math.abs(-100); // 100
        Math.abs(-7.8); // 7.8

        Math.pow(2, 10); // 2的10次方=1024
        Math.sqrt(2); // 1.414...
        //随机数，无法指定种子，故认为是“真”随机数，(0,1)之间
        Math.random(); // 0.53907... 每次都不一样

        //生成min，max之间的随机数
        double x = Math.random(); // x的范围是[0,1)
        double min = 10;
        double max = 50;
        double y = x * (max - min) + min; // y的范围是[10,50)
        long n = (long) y; // n的范围是[10,50)的整数
        System.out.println(y);
        System.out.println(n);

        //伪随机数，可以指定种子，默认种子为当前的时间戳
        Random r = new Random(5);
        
        //SecureRandom 安全随机数,无法指定种子，且这个种子是通过CPU的热噪声、读写磁盘的字节、网络流量等各种随机事件产生的“熵”。
        SecureRandom sr = null;

        try
        {
            sr = SecureRandom.getInstanceStrong();
        }catch (NoSuchAlgorithmException e)
        {
            sr = new SecureRandom();
        }
        byte[] buffer = new byte[16];
        sr.nextBytes(buffer);
        System.out.println(Arrays.toString(buffer));
     }
 }
