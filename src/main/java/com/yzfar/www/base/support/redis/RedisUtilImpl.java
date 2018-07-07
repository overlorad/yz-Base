package com.yzfar.www.base.support.redis;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.yzfar.www.base.util.RedisUtil;
import com.yzfar.www.base.util.StringUtil;

import redis.clients.jedis.Client;
import redis.clients.jedis.Jedis;

/**
 * @author: cp
 * @date: 2018年5月28日 下午3:14:19
 */
public class RedisUtilImpl implements RedisUtil {
	protected Logger logger = LogManager.getLogger();
	private RedisTemplate<Serializable, Serializable> redisTemplate;
	private Jedis jedis;

	public void setJedisPool(JedisConnectionFactory jedisPool) {
		jedis = jedisPool.getShardInfo().createResource();
	}

	public void setRedisTemplate(RedisTemplate<Serializable, Serializable> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void add(String key, Serializable value, int day) {
		redisTemplate.boundValueOps(key).set(value);
		expire(key, day);
	}

	@Override
	public void add(String key, Serializable value) {
		redisTemplate.boundValueOps(key).set(value);
	}

	@Override
	public <T> void addToSet(int day, String key, Serializable[] values) {
		try {
			redisTemplate.opsForSet().add(key, values);
			expire(key, day);
			logger.info("redis保存数据{}条", values.length);
		} catch (Exception e) {
			logger.error("redis保存失败:{}", e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> Set<T> getFromSet(Class<T> clazz, String key) {
		try {
			return (Set<T>) redisTemplate.opsForSet().members(key);
		} catch (Exception e) {
			logger.error("redis获取失败:{}", e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(final Class<T> clazz, final String key) {
		return (T) redisTemplate.boundValueOps(key).get();
	}

	@Override
	public void del(String key) {
		redisTemplate.delete(key);
	}

	@Override
	public Boolean exists(String key) {
		return redisTemplate.hasKey(key);
	}

	@Override
	public Boolean expireAt(String key, long unixTime) {
		return redisTemplate.expireAt(key, new Date(unixTime));
	}

	/**
	 * 过期天数
	 */
	private final Boolean expire(final String key, final int day) {
		return redisTemplate.expire(key, day, TimeUnit.DAYS);
	}

	@Override
	public Set<String> getAllKey() {
		return jedis.keys("*");
	}

	@Override
	public long getDbSize() {
		return Long.parseLong(getInfo().get("used_memory"));
	}

	@Override
	public synchronized Map<String, String> getInfo() {
		Client client = jedis.getClient();
		client.info();
		String info = client.getBulkReply();
		String[] split = info.split("\\n");
		Map<String, String> result = new HashMap<>();
		for (String keyToValueLine : split) {
			if (keyToValueLine.contains(":")) {
				String[] keyToValue = keyToValueLine.split(":");
				if (keyToValue.length == 2) {
					result.put(keyToValue[0], StringUtil.replaceBlank(keyToValue[1]));
				}
			}
		}
		return result;
	}

}
