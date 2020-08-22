package com.upic.http;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class SignUtil {

	private static String token = "weixin";
	
	public static boolean checkSignature(String signature,String timestamp,String nonce){
		boolean result = false;
		//1:��token��timestamp��nonce���ֵ�����
		String params[] = new String[]{token,timestamp,nonce};
		Arrays.sort(params);
		//2:�������Ժ������ƴ�ӳ��ַ���
		String content = params[0].concat(params[1]).concat(params[2]);
		//3:��ƴ���Ժ���ַ�������SHA1����
		String str = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] digest = md.digest(content.toString().getBytes());
			//4:��byte[]תΪString
			str = byte2str(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		//5:���������ַ���str��signature���бȽ��жϣ������ͬ����У��ͨ������true����֮������
		if(str!=null && str.equals(signature)){
			result = true;
		}	
		return result;
	}

	/*
	 * �ֽ�����ת��Ϊ�ַ���
	 */
	public static String byte2str(byte[] array) {
		StringBuffer hexstr = new StringBuffer();
		String shaHex = "";
		for (int i = 0; i < array.length; i++) {
			shaHex = Integer.toHexString(array[i] & 0xFF);
			if (shaHex.length() < 2) {
				hexstr.append(0);
			}
			hexstr.append(shaHex);
		}
		return hexstr.toString();
	}

}
