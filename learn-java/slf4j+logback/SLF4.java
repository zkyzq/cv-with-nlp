import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

/**

*

* @author tangxi.zq

*/

public class SLF4 {

    static final Logger logger = LoggerFactory.getLogger(SLF4.class);

    public static void main(String[] args) {

        logger.info("Start process {}...", SLF4.class.getName());

        try {

            "".getBytes("invalidCharsetName");

        } catch (UnsupportedEncodingException e) {

// TODO: 使用logger.error(String, Throwable)打印异常

logger.error("空字符串",e);

        }

        logger.info("Process end.");

    }

}

