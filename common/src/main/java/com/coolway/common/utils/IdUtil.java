package com.coolway.common.utils;

import java.util.UUID;

public class IdUtil {
	private IdUtil() {
	}

	public static String newUuid() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replaceAll("\\-", "");
	}

	public static String newSecretkey() {
		return IdUtil.newUuid().substring(0, 8);
	}

}
