package com.yzfar.www.base.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @version:1.0.0
 * @Description: （字符串工具）
 * @Author: chengpeng
 * @Date: 18:38 2018/5/25
 */
public final class StringUtil {
	protected static Logger logger = LogManager.getLogger();

	private StringUtil() {
	}

	public static boolean stringToFile(String filePath, String str, String encoding) {
		BufferedWriter fileWriter = null;
		try {
			fileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, true), encoding));
			fileWriter.write(str);
			logger.info("save string to 【{}】", filePath);
			return true;
		} catch (IOException ex) {
			logger.info("save string to 【{}】 fail:{}", filePath, ex.getMessage());
			return false;
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public static String fileToString(String filePath, String encoding) {
		File file = new File(filePath);
		Long fileLength = file.length();
		FileInputStream in = null;
		byte[] fileContent = new byte[fileLength.intValue()];
		try {
			in = new FileInputStream(file);
			in.read(fileContent);
			logger.info("read file 【{}】", filePath);
			return new String(fileContent, encoding);
		} catch (Exception e) {
			logger.info("read file 【{}】  fail:{}", filePath, e.getMessage());
			return null;
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 去掉回车换行
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	public static boolean isAllBlank(final CharSequence... css) {
		return StringUtils.isAllBlank(css);
	}

	public static boolean isAllEmpty(final CharSequence... css) {
		return StringUtils.isAllEmpty(css);
	}

	public static boolean isAllLowerCase(final CharSequence cs) {
		return StringUtils.isAllLowerCase(cs);
	}

	public static boolean isBlank(final CharSequence cs) {
		return StringUtils.isBlank(cs);
	}

	public static boolean isEmpty(final CharSequence cs) {
		return StringUtils.isEmpty(cs);
	}
}
