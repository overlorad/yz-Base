package com.yzfar.www.base.test;

import com.yzfar.www.base.util.PathUtil;
import com.yzfar.www.base.util.RedisUtil;
import com.yzfar.www.base.util.SpringUtil;

/**
 * 
 * @author: cp
 * @date: 2018年6月14日 上午10:55:33
 */
public class SendTest {
	public static void main(String[] args) {
		String path = PathUtil.getProjectPath() + "src\\main\\resources\\spring\\spring.xml";
		SpringUtil.initSpring(path);
		RedisUtil bean = SpringUtil.getBean(RedisUtil.class);
		long dbSize = bean.getDbSize();
		System.err.println(dbSize);
	}
}
