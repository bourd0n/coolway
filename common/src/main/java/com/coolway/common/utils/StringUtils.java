package com.coolway.common.utils;

import java.util.UUID;

public class StringUtils {

	
	/**
	 * 
	 * 获取uuid码
	 *
	 * @return
	 */
	public static String getActiveCodeByRandom() {
		String activeCode = UUID.randomUUID().toString();
		activeCode = activeCode.replace("-", "");
		return activeCode;
	}
	
	
	/**
	 * 获取真是IP地址
	 * 
	 * @return
	 * @throws SocketException
	 
	public static String getRemortIP(HttpServletRequest request) {
		if (request.getHeader("x-forwarded-for") == null) {
			return request.getRemoteAddr();
		}
		return request.getHeader("x-forwarded-for");
	}
	*/
}
