package com.upic.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 流转换工具
 * @author ST
 *
 */
public class StreamUtil {
	/**
	 * 将流转成String
	 * 
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static String stream2String(InputStream inputStream) throws IOException {
		int len = 0;
		byte[] buffer = new byte[10240];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while ((len = inputStream.read(buffer)) != -1) {
			baos.write(buffer, 0, len);
		}
		baos.flush();
		baos.close();
		inputStream.close();
		String result = new String(baos.toByteArray(), "UTF-8");
		return result;
	}
	/**
	 * 将流转化成文件
	 * @param is 输入流
	 * @param file 文件路径
	 * @throws IOException
	 */
	public static void saveFile(InputStream is, File file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		int len = 0;
		byte[] buffer = new byte[10240];
		while ((len = is.read(buffer)) != -1) {
			fos.write(buffer, 0, len);
		}
		fos.flush();
		fos.close();
		is.close();
	}
	
	
	public static List<String> saveMutilFiles(HttpServletRequest request,String idCard) {
		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
		MultipartFile file = null;
		BufferedOutputStream stream = null;
//		List<Map<String, String>> url = new ArrayList<Map<String, String>>();
		List<String> url=new ArrayList<String>();
		for (int i = 0; i < files.size(); ++i) {
			file = files.get(i);
			if (!file.isEmpty()) {
				try {
					byte[] bytes = file.getBytes();
					String name = file.getOriginalFilename().substring(0, file.getOriginalFilename().length() - 4);
					String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().length() - 3,
							file.getOriginalFilename().length());
					Map<String, String> temp = new HashMap<>();
					temp.put(name, suffix);
//					url.add(temp);
					url.add(idCard+i+"."+suffix);
//					stream = new BufferedOutputStream(
//							new FileOutputStream(new File("img/" + idCard+i+".jpg")));
					stream = new BufferedOutputStream(
							new FileOutputStream(new File(WxConst.PICURL + "/"+idCard+i+".jpg")));
					stream.write(bytes);
					stream.close();
				} catch (Exception e) {
					stream = null;
				}
			} else {
			}
		}
		return url;
	}
	
}
