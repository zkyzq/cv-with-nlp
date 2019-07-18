import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @GitHub: https://github.com/zhangqizky
 * @author tangxi.zq
 */
public class Main {

	static final Log log = LogFactory.getLog(Main.class);

	public static void main(String[] args) {
		log.info("Start process...");
		try {
			"".getBytes("invalidCharsetName");
		} catch (UnsupportedEncodingException e) {
			log.error("Invalid encoding.", e);
		}
		log.info("Process end.");
	}
}
