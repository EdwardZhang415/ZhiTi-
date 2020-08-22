package com.upic.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;

public class SignUtil {

	/**
	 * 校验请求是否来自微信服务器
	 * 
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static boolean checkSignature(String signature, String timestamp, String nonce) {
		String token = WxConst.TOKEN;
		if (token == null || timestamp == null || nonce == null) {
			throw new RuntimeException("错误：token == null || timestamp == null || nonce == null");
		}
		String[] tmpArr = { token, timestamp, nonce };
		Arrays.sort(tmpArr);
		String tmpStr = StringUtils.join(tmpArr, "");
		// LOG.debug("拼接后的字符串=" + tmpStr);
		tmpStr = new String(DigestUtils.sha1Hex(tmpStr.getBytes()));
		// LOG.debug("sha1加密后字符串=" + tmpStr);
		if (tmpStr.equals(signature)) {
			return true;
		} else {
			return false;
		}
	}
}
