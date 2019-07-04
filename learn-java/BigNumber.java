/**
 * @ authur ：tangxi.zq
 * @ note   :BigInteger
 */

import java.math.BigInteger;

public class BigNumber
{
    public static void main(String[]args)
    {
        BigInteger bi = new BigInteger("1234567890");
        System.out.println(bi.pow(5)); // 2867971860299718107233761438093672048294900000

        //做运算的时候只能采用实例方法
        BigInteger i1 = new BigInteger("1234567890");
        BigInteger i2 = new BigInteger("12345678901234567890");
        BigInteger sum = i1.add(i2); // 12345678902469135780，实例方法

        //将biginteger转为基础类型，使用intValueExact或者longValueExact这种方法，超出范围会抛异常
        BigInteger i = new BigInteger("1234567890");
        System.out.println(i.longValue());
        System.out.println(i.multipy(i).longValueExact());
        //BigInteger和Integer等一样，都是继承自Number,所以有下面这些转换方法
        /** 
        转换为byte：byteValue()
        转换为short：shortValue()
        转换为int：intValue()
        转换为long：longValue()
        转换为float：floatValue()
        转换为double：doubleValue()
        */
    }
}
