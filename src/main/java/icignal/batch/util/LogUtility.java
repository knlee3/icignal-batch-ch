package icignal.batch.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 로그 관련 Util 클래스
 * 
 * @author jskim
 * 
 */
public class LogUtility {

	static final Logger logger = LoggerFactory.getLogger(LogUtility.class);
	

	/**
	 * 에러 로그 작성 함수
	 * 
	 * @param message 에러 메시지
	 */
	public final static void error(String message) {
		logger.error(message);
	}
	
	

	/**
	 * 에러 로그 작성 함수
	 * 
	 * @param message 에러 메시지
	 */
	public final static void error(Exception e) {
		logger.error("Exception: ", e);
	}
	
	/**
	 * 에러 로그 작성 함수
	 * 
	 * @param message 에러 메시지
	 */
	public final static void error(String message, Exception e) {
		logger.error(message, e);
	}
	
	/**
	 * 디버그 로그 작성 함수
	 * 
	 * @param message 디버깅 메시지
	 */
	public final static void debug(String message) {
		logger.debug(message);
	}

	/**
	 * 인포 로그 작성 함수
	 * 
	 * @param message 디버깅 메시지
	 */
	public final static void info(String message) {
		logger.info(message);
	}
}
