## 前言
Java调用C++在开发中还是比较常见的，并且Java提供了一个特别方便的包，Java Native Access，也就是JNA包，来实现Java对Native Library的调用，
Java和Native语言之间的对应关系非常重要，如下表所示。

<table>
<thead><td>Native Type</td><td>Size</td><td>Java Type</td><td>Common Windows Types</td></thead>
<tr><td>char</td><td>8-bit integer</td><td>byte</td><td>BYTE, TCHAR</td></tr>
<tr><td>short</td><td>16-bit integer</td><td>short</td><td>WORD</td></tr>
<tr><td>wchar_t</td><td>16/32-bit character</td><td>char</td><td>TCHAR</td></tr>
<tr><td>int</td><td>32-bit integer</td><td>int</td><td>DWORD</td></tr>
<tr><td>int</td><td>boolean value</td><td>boolean</td><td>BOOL</td></tr>
<tr><td>long</td><td>32/64-bit integer</td><td>NativeLong</td><td>LONG</td></tr>
<tr><td>long long</td><td>64-bit integer</td><td>long</td><td>__int64</td></tr>
<tr><td>float</td><td>32-bit FP</td><td>float</td><td></td></tr>
<tr><td>double</td><td>64-bit FP</td><td>double</td><td></td></tr>
<tr><td>char*</td><td>C string</td><td>String</td><td>LPTCSTR</td></tr>
<tr><td>void*</td><td>pointer</td><td>Pointer</td><td>LPVOID, HANDLE, LP<i>XXX</i></td></tr>
</table>


## 将c代码打包成动态库
为了能使Java调用C，首先需要将c的代码代码打包为动态库，linux下后缀为.so，mac下后缀为.dylib，简单介绍打包过程:

这个功能一共有2个c源文件，1个头文件，zero.c,iio.c,iio.h
先将c代码编译为o文件
> gcc -c zero.c  
> gcc -c iio.c
发现文件夹下面有了zero.o和iio.o，再执行
> gcc -shared -o libZero.dylib zero.o iio.o -lpng -ltiff -ljpeg -lm

上面libZero.dylib为生成文件的名字,-l代表的是需要的附加依赖库。

这样就生成了需要的libZero.dylib库，如果在linux上，就生成了libZero.so文件。

## 在Java中调用C动态库
1. 新建maven工程，将刚刚生成的libZero.dylib拷贝到resources文件夹下
2. 调用的代码如下：
```
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;

public final class ZERO {
    public interface CLibrary extends Library {

        CLibrary dctLib = (CLibrary) Native.loadLibrary((Platform.isMac() ? "libZero.dylib" : "libZero.dylib"), CLibrary.class);
        IntByReference get_forgery_map(String FileName);
    }

    public static void main(String[]args) throws Exception
    {
        String filename = "IMG_2116-1.jpg";
        BufferedImage origin = ImageIO.read(new File(filename));
        ZERO z = new ZERO();
        int[] res = z.get_forgery_map(filename,origin.getWidth(),origin.getHeight());
        assert (res.length!=0);
        System.out.println(res.toString());
        System.out.println(res.length);


        BufferedImage image = new BufferedImage(origin.getWidth(), origin.getHeight(), BufferedImage.TYPE_USHORT_GRAY);
        WritableRaster raster = image.getRaster();
        raster.setPixels(0, 0, origin.getWidth(), origin.getHeight(), res);
        ImageIO.write(image,"png", new File("/Users/tangxi/Desktop/test_out.png"));
        System.out.println("write success");
    }


    public final int[] get_forgery_map(String FileName,int width,int height)
    {
        IntByReference intFromCByRef;
        int[][] dctCoeffs =null;
        intFromCByRef = CLibrary.dctLib.get_forgery_map(FileName);

        Pointer p = intFromCByRef.getPointer();
        int [] res = p.getIntArray(0,width*height);
        return res;
    }
}
```

其中函数名为get_forgery_map函数在zero.c文件中有定义，通过CLibrary.dctLib调用该函数，并注意java和native语言的变量类型对应，即可。
