package com.yzfar.www.base.test;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yzfar.www.base.model.FitterFun;
import com.yzfar.www.base.util.DateUtil;
import com.yzfar.www.base.util.LeastSquares;
import com.yzfar.www.base.util.PathUtil;
import com.yzfar.www.base.util.StringUtil;

public class UtilTest {
	@Test
	public void listTest() {
		String path = PathUtil.getProjectPath();
		System.out.println(path);
	}

	@Test
	public void hibernateTest() {

	}

	public static void main(String[] args) {
		String filePath = PathUtil.getProjectPath() + "src\\test\\java\\com\\yzfar\\www\\base\\test\\history.txt";
		String encoding = "gbk";
		String string = StringUtil.fileToString(filePath, encoding);
		JSONArray array = (JSONArray) JSON.parse(string);
		long startOfTodDay = DateUtil.startOfyesterday();
		System.out.println(startOfTodDay);
		double[] labers = new double[array.size()];
		double[] values = new double[array.size()];
		for (int i = 0; i < array.size(); i++) {
			JSONObject object = (JSONObject) array.get(i);
			Double value = object.getDouble("indicatorvalue");
			Long time = object.getLong("time");
			int subTime = (int) ((time - startOfTodDay) / 1000);
			labers[i] = subTime;
			value = new BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			values[i] = value;
		}
		System.out.println(Arrays.toString(labers));
		int degree = LeastSquares.fitterDegree(labers, values);
		System.err.println(degree);
		FitterFun fitter = LeastSquares.fitter(labers, values, degree);
		Double sqrtLMS = fitter.getSqrtLMS(labers, values);
		for (int i = 0; i < values.length; i++) {
			double del = Math.abs(values[i] - fitter.getValue(labers[i]));
			System.out.println(sqrtLMS - del);
		}
	}
}
