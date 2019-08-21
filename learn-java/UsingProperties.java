import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.io.FileReader;
import java.util.*;
/**
@authur        zhangqi
@apiNote       使用properties
 */

 public class UsingProperties
 {
     public static void main(String[]args) throws Exception
     {
        //  String f = "setting.properties";
        // //创建properties实例
        //  Properties props = new Properties();
        //  //调用load读取文件
        //  props.load(new FileInputStream(f));
        // //使用getProperty()获取实例
        //  String filepath = props.getProperty("last_open_file");
        //  String interval = props.getProperty("auto_save_interval","120");

        String settings = "# test" + "\n" + "course=Java" + "\n" + "last_open_date=2019-08-07T12:35:01";
        ByteArrayInputStream input = new ByteArrayInputStream(settings.getBytes("UTF-8"));
        Properties props = new Properties();
        props.load(input);

        System.out.println("course: " + props.getProperty("course"));
        System.out.println("last_open_date: " + props.getProperty("last_open_date"));
        System.out.println("last_open_file: " + props.getProperty("last_open_file"));
        System.out.println("auto_save: " + props.getProperty("auto_save", "60"));

        //如果有多个配置文件，可以反复调用，后面的会覆盖前面的

        //写入配置文件
        Properties props2 = new Properties();
        props2.setProperty("网址", "http://www.liaoxuefeng.com");
        props2.setProperty("语言", "Java");
        props2.store(new FileOutputStream("setting.properties"), "这是写入的properties注释");

        props.load(new FileReader("setting.properties", StandardCharsets.UTF_8));
        System.out.println("网址:"+ props.getProperty("网址"));

     }
 }
