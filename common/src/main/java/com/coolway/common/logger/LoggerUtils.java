package com.coolway.common.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志工具类.
 *
 * @author chengdong
 */
public class LoggerUtils {

	//TODO 配置一个appender 单纯输出错误日志
    private static Logger error = LoggerFactory.getLogger("com.coolway.looger.error");

    public static void error(String errorMessage, Throwable e) {
        error.error(errorMessage, e);
    }

    public static void error(String errorMessage) {
        error.error(errorMessage);
    }


}
