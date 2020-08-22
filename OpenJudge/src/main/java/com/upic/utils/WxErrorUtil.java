package com.upic.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 解析错误原因工具类
 * @author ST
 *
 */
public class WxErrorUtil {
	private static Properties properties = new Properties();
	static {
		InputStream inputStream = null;
		try {
			inputStream = WxErrorUtil.class.getClassLoader()
					.getResourceAsStream("error.properties");
			properties.load(inputStream);
			inputStream.close();
			//properties.list(System.out);
		} catch (IOException e) {
			e.printStackTrace();
			inputStream = null;
			throw new ExceptionInInitializerError(e);
		}
	}

	/**
	 * @param key 错误码
	 * @return 错误原因
	 */
	public static String getValue(Integer key) {
		return properties.getProperty(key + "", "未知错误");
	}

	public static void main(String[] args) {
		String value = WxErrorUtil.getValue(400201);
		System.out.println(value);
	}
}
