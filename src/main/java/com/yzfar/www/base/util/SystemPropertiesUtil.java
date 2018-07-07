package com.yzfar.www.base.util;

import java.io.IOException;
import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * @version:1.0.0
 * @Description: （描述）
 * @Author: chengpeng
 * @Date: 9:47 2018/5/28
 */

public final class SystemPropertiesUtil extends PropertyPlaceholderConfigurer implements Serializable {
	private static final long serialVersionUID = 3934363322833266296L;
	private static final byte[] KEY = { 9, -1, 0, 5, 39, 8, 6, 19 };
	private static Map<String, String> ctxPropertiesMap;
	private static List<String> decryptProperties;

	/**
	 * 获取加密的值
	 */
	public static String getSaveValue(String key, String value) {
		if (decryptProperties.contains(key)) {
			return SecurityUtil.encryptDes(value, KEY);
		}
		return value;
	}

	class MapKeyComparator implements Comparator<String> {
		@Override
		public int compare(String str1, String str2) {
			return str1.compareTo(str2);
		}
	}

	@Override
	protected void loadProperties(Properties props) throws IOException {
		super.loadProperties(props);
		ctxPropertiesMap = new TreeMap<>(new MapKeyComparator());
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			String value = props.getProperty(keyStr);
			if (decryptProperties != null && decryptProperties.contains(keyStr)) {
				value = SecurityUtil.decryptDes(value, KEY);
				props.setProperty(keyStr, value);
			}
			ctxPropertiesMap.put(keyStr, value);
		}
	}

	public void setDecryptProperties(List<String> decryptProperties) {
		SystemPropertiesUtil.decryptProperties = decryptProperties;
	}

	public static String getString(String key) {
		try {
			return ctxPropertiesMap.get(key);
		} catch (MissingResourceException e) {
			return null;
		}
	}

	public static void setKey(String key, String value) {
		if (key == null) {
			return;
		}
		ctxPropertiesMap.put(key, value);
	}

	public static void setKeys(Map<String, String> keyToValues) {
		if (keyToValues == null) {
			return;
		}
		ctxPropertiesMap.putAll(keyToValues);
	}

	public static void deleteKey(String key) {
		ctxPropertiesMap.remove(key);
	}

	public static String getString(String key, String defaultValue) {
		try {
			String value = ctxPropertiesMap.get(key);
			if (StringUtils.isEmpty(value)) {
				return defaultValue;
			}
			return value;
		} catch (MissingResourceException e) {
			return defaultValue;
		}
	}

	public static int getInt(String key) {
		return Integer.parseInt(ctxPropertiesMap.get(key));
	}

	public static int getInt(String key, int defaultValue) {
		String value = ctxPropertiesMap.get(key);
		if (StringUtils.isBlank(value)) {
			return defaultValue;
		}
		return Integer.parseInt(value);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static boolean getBoolean(String key, boolean defaultValue) {
		String value = ctxPropertiesMap.get(key);
		if (StringUtils.isBlank(value)) {
			return defaultValue;
		}
		return new Boolean(value);
	}

	public static Map<String, String> getAllKeyToValue() {
		Map<String, String> map = new HashMap<>();
		map.putAll(ctxPropertiesMap);
		return map;
	}

}
