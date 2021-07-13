package com.bingoabin.util;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * 文件读写的工具类
 *
 * @author W-ln
 * @version 1.0
 */
public class FileUtil {

	/**
	 * 读取指定的文件转换为二进制数组
	 *
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static byte[] readFile(String filePath) throws IOException {
		InputStream in = new FileInputStream(filePath);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024 * 4];
		int length = 0;
		while ((length = in.read(buffer)) != -1) {
			out.write(buffer, 0, length);
		}
		return out.toByteArray();
	}

	/**
	 * 保存文件
	 *
	 * @param song
	 * @return
	 */
	public static boolean saveFile(String filePath, String fileName, MultipartFile song) {
		try (InputStream inputStream = song.getInputStream();
		     FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath + fileName));) {
			IOUtils.copy(inputStream, fileOutputStream);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
