/**
 * @authur     tangxi.zq
 * @apiNote    日志打印
 */

import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;
public class Logging
{
    public static void main(String[]args)
    {
        Logger logger = Logger.getLogger(Logging.class.getName());
        logger.info("Start process...");
        try
        {
            "".getBytes("invalidCharseName");
        }catch(UnsupportedEncodingException e)
        {
            logger.severe("不支持的编码类型");
        }
        logger.info("Process end...");
    }
}
