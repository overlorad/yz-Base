package com.yzfar.www.base.util;

import java.util.UUID;

/**
 * @version:1.0.0
 * @Description: （描述）
 * @Author: chengpeng
 * @Date: 9:58 2018/5/28
 */
public final class UUIDUtil {
	private UUIDUtil() {
	}

	/**
	 * 随机ID
	 */
	public static String randomUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "c");
	}

	public static Integer randomNumber(int min, int max) {
		return (int) (Math.random() * (max - min)) + min;
	}
}
