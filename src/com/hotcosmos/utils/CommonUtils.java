package com.hotcosmos.utils;

import java.util.UUID;

public class CommonUtils {

	/**
	 * 获取UUID(36位的不重复的字符串)
	 * 
	 * @return String UUID
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString();
	}
}
