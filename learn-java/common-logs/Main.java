/**
 * @author     tangxi.zq
 * @apiNote    日志打印
 */

import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Main {

	static final Log log = LogFactory.getLog(Main.class);

	public static void main(String[] args) {
		log.info("Start process...");
		try {
			"".getBytes("invalidCharsetName");
		} catch (UnsupportedEncodingException e) {
            // TODO: 使用log.error(String, Throwable)打印异常
            log.error("空字符串调用",e);
		}
		log.info("Process end.");
	}
}
