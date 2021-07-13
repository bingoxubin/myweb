package com.epoint.bigdata.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 获取指定路径下的文件名，目录名
 *
 * @author xumaosheng
 * @date 2019/8/14 15:15
 */
public class GetFileInfoUtil {

	/*
	 * 读取指定路径下的文件名和目录名
	 * @param 指定文件夹路径
	 */
	public JSONArray getFileList(String rootpath, String currentpath) {
		String path = rootpath + currentpath;
		JSONArray jsonfile = new JSONArray();
		System.out.println("=================================" + path);
		File fileCatalog = new File(path);
		File[] fileList = fileCatalog.listFiles();

		//日历类
		Calendar cal = Calendar.getInstance();

		try {
			for (int i = 0; i < fileList.length; i++) {
				if (fileList[i].isFile()) {

					String fileName = fileList[i].getName();
					File file = new File(path + "/" + fileName);

					double size = getDirSize(file);
					String filesize = "";
					if (size > 1024 && size < 1048576) {
						filesize = ((int) (size * 100 / 1024.0)) / 100.0 + "MB";
					} else if (size >= 2048576) {
						filesize = ((int) (size * 100 / 2048576.0)) / 100.0 + "GB";
					} else {
						filesize = (int) size + "KB";
					}

					//------获取文件修改时间-
					long time = file.lastModified();
					cal.setTimeInMillis(time);
					Date date = cal.getTime();
					DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM); //显示日期，时间（精确到分）
					String dateStr = df.format(date);    //转换格式
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("filename", fileName);
					jsonObject.put("filesize", filesize);
					jsonObject.put("filedate", dateStr);
					jsonObject.put("href", "Packages/" + currentpath + "/" + fileName);
					jsonfile.add(jsonObject);

				}

				//如果是目录
				if (fileList[i].isDirectory()) {
					String fileName = fileList[i].getName();
					File file = new File(path + "/" + fileName);

					//------获取文件修改时间-
					long time = file.lastModified();
					cal.setTimeInMillis(time);
					Date date = cal.getTime();
					DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM); //显示日期，时间（精确到分）
					String dateStr = df.format(date);    //转换格式
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("filename", fileName);
					jsonObject.put("href", "index?path=" + currentpath + "/" + fileName);
					jsonObject.put("filesize", "-");
					jsonObject.put("filedate", dateStr);
					jsonfile.add(jsonObject);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonfile;
	}

	/**
	 * 获取文件夹、文件  的大小
	 */
	public static double getDirSize(File file) {
		//判断文件是否存在
		if (file.exists()) {
			//如果是目录则递归计算其内容的总大小
			if (file.isDirectory()) {
				File[] children = file.listFiles();
				double size = 0;
				for (File f : children) {
					size += getDirSize(f);
				}
				return size;
			} else {
				//如果是文件则直接返回其大小,以“KB”为单位
				double size = (double) file.length() / 1024;
				return size;
			}
		} else {
			System.out.println("文件或者文件夹不存在，请检查路径是否正确！");
			return 0.0;
		}
	}

	/**
	 * 获取文件的扩展名
	 *
	 * @param filename 带后缀文件名
	 * @return 不带后缀文件名
	 */
	public static String getExtension(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int i = filename.lastIndexOf('.');
			if ((i > -1) && (i < (filename.length() - 1))) {
				return filename.substring(i + 1);
			}
		}
		return null;
	}
}
