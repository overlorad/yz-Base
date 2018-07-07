package com.yzfar.www.base.util;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * @version:1.0.0
 * @Description: （描述）
 * @Author: chengpeng
 * @Date: 9:42 2018/5/28
 */
public interface RedisUtil {

	/**
	 * 添加单个
	 */
	void add(final String key, final Serializable value, int day);

	/**
	 * 添加单个
	 */
	void add(final String key, final Serializable value);

	/**
	 * 添加set
	 */
	<T> void addToSet(int day, final String key, Serializable[] values);

	/**
	 * 获取set
	 */
	<T> Set<T> getFromSet(final Class<T> clazz, final String key);

	/**
	 * 通过key获取数据
	 */
	<T> T get(final Class<T> clazz, final String key);

	/**
	 * 通过key删除
	 */
	void del(final String key);

	/**
	 * 是否存在
	 */
	Boolean exists(final String key);

	/**
	 * 在某个时间点失效
	 */
	Boolean expireAt(String key, long unixTime);

	/**
	 * 获取所有key
	 */
	Set<String> getAllKey();

	/**
	 * 获取数据大小
	 */
	long getDbSize();
	
	/**
	 * 获取redis信息
	 */
	Map<String, String> getInfo();
}
