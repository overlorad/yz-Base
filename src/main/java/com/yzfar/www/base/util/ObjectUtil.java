package com.yzfar.www.base.util;

import static com.yzfar.www.base.util.StringUtil.fileToString;
import static com.yzfar.www.base.util.StringUtil.stringToFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @version:1.0.0
 * @Description: （描述）
 * @Author: chengpeng
 * @Date: 10:10 2018/5/28
 */
public class ObjectUtil {
	protected static Logger log = LogManager.getLogger();

	public static Map<String, Object> toMapping(Object obj) {
		Method[] methods = obj.getClass().getDeclaredMethods();
		Map<String, Object> result = new HashMap<>();
		for (Method method : methods) {
			String mName = method.getName();
			if (mName.startsWith("get")) {
				try {
					Object[] args = null;
					Object value = ((Method) method).invoke(obj, args);
					if (value != null) {
						char[] charArray = method.getName().toCharArray();
						char[] newcharArray = new char[charArray.length - 3];
						int k = 0;
						for (int i = 3; i < charArray.length; i++) {
							if (i == 3) {
								newcharArray[k++] = (char) (charArray[i] + 32);
							} else {
								newcharArray[k++] = charArray[i];
							}
						}
						result.put(new String(newcharArray), value);
					}
				} catch (Exception e) {
					continue;
				}
			}
		}
		return result;
	}

	public static byte[] objectToByte(Object obj) {
		byte[] bytes = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.flush();
			bytes = bos.toByteArray();
			oos.close();
			bos.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bytes;
	}

	public static <T> T byteToObject(byte[] bytes, Class<T> t) {
		Object obj = null;
		ByteArrayInputStream bis = null;
		ObjectInputStream ois = null;
		try {
			bis = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bis);
			obj = ois.readObject();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				ois.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				bis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return t.cast(obj);
	}

	public static boolean objToJson(Object obj, String path) {
		PathUtil.mkDirFile(path);
		String json = JSON.toJSONString(obj, SerializerFeature.DisableCircularReferenceDetect);
		return stringToFile(path, json, "gbk");
	}

	public static <T> T jsonToObj(Class<T> clazz, String path) {
		String json = fileToString(path, "gbk");
		if (json == null) {
			return null;
		}
		try {
			T parseObject = JSON.parseObject(json, clazz);
			return parseObject;
		} catch (Exception e) {
			log.error("json文件【{}】解析失败:{}", path, e.getMessage());
			return null;
		}
	}
}
