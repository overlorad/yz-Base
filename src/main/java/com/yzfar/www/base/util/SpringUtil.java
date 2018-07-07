package com.yzfar.www.base.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @version:1.0.0
 * @Description: （spring工具）
 * @Author: chengPeng
 * @Date: 19:06 2018/5/25
 */
public class SpringUtil {
	protected static Logger logger = LogManager.getLogger();
	private static FileSystemXmlApplicationContext context = null;

	private SpringUtil() {
	}

	/**
	 * 初始化spring
	 */
	public static boolean initSpring(String path) {
		logger.info("初始化spring文件【{}】", path);
		try {
			context = new FileSystemXmlApplicationContext(path);
			context.start();
			logger.info("初始化spring文件【{}】完成", path);
			return true;
		} catch (Exception e) {
			logger.error("初始化spring文件【{}】错误:{}", path, e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	public static Object getBean(String clazz) {
		if (context == null) {
			logger.error("未初始化spring");
			return null;
		}
		try {
			return context.getBean(clazz);
		} catch (Exception e) {
			logger.error("获取{}失败{}", clazz, e);
			return null;
		}
	}

	public static <T> T getBean(Class<T> clazz) {
		if (context == null) {
			logger.error("未初始化spring");
			return null;
		}
		try {
			return context.getBean(clazz);
		} catch (Exception e) {
			logger.error("获取{}失败{}", clazz.getName(), e);
			return null;
		}
	}
}
