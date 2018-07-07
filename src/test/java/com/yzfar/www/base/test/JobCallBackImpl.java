package com.yzfar.www.base.test;

import com.yzfar.www.base.model.JobCallBack;

/**
 * @author: cp
 * @date: 2018年5月28日 下午3:02:32
 */
public class JobCallBackImpl implements JobCallBack {

	@Override
	public void doCallBack(Object object) {
		System.err.println(object);
	}

}
