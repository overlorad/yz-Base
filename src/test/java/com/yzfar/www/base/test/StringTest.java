package com.yzfar.www.base.test;

import java.io.File;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yzfar.www.base.util.StringUtil;

/**
 * 
 * @author: cp
 * @date: 2018年6月20日 下午2:59:26
 */
public class StringTest {
	public static void main(String[] args) {
		File f = new File("C:\\Users\\cp787\\Desktop\\topoIcon");
		File[] listFiles = f.listFiles();
		JSONArray array = new JSONArray();
		for (File file : listFiles) {
			String fileToString = StringUtil.fileToString(file.getPath(), "utf-8");
			JSONObject jo = (JSONObject) JSON.parse(fileToString);
			array.add(jo);
		}
		StringUtil.stringToFile("C:\\Users\\cp787\\Desktop\\AllIcon.json", array.toString(), "utf-8");
	}
}
