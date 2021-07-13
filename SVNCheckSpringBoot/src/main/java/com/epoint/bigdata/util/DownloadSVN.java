package com.epoint.bigdata.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author xumaosheng
 * @date 2019/8/16 13:21
 */
public class DownloadSVN {
	public static void main(String[] args) {

		try {
			String ss = URLDecoder.decode(getSvnAddr("E:\\NeedEncoding\\SVNCheckOut\\Word2Vec"), "utf-8");
			System.out.println(ss.split("/")[ss.split("/").length-2]);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// String svnaddr = "svn://192.168.0.51/EpointDM/AI/Trunk/模型归档/business_license";
		// // String svnaddr = "svn://192.168.0.51/EpointDM/AI/Trunk/模型归档/人脸识别/Face_Recognition";
		// System.out.println(svnaddr.substring((svnaddr.lastIndexOf("/") + 1), svnaddr.length())); //拿项目名称
		// System.out.println(svnaddr.substring(svnaddr.indexOf("模型归档") + 5)); //拿模型归档后面的名称
		// if (svnaddr.substring(svnaddr.indexOf("模型归档") + 5).contains("/")) {
		// 	String str = svnaddr.substring(svnaddr.indexOf("模型归档") + 5);
		// 	System.out.println(str.substring(0, str.indexOf("/")));
		// } else {
		// 	System.out.println(svnaddr.substring(svnaddr.indexOf("模型归档") + 5));
		// }
		//
		// try {
		// 	Process p1 = Runtime.getRuntime().exec("svn checkout svn://192.168.0.51/EpointDM/AI/Trunk/模型归档/人脸识别/Face_Recognition E:\\57.问题处理\\Face_Recognition");
		// 	p1.waitFor();
		// } catch (Exception e) {
		//
		// }
	}

	/**
	 * 描述：根据本地目录获取真实svn地址
	 *
	 * @param localaddr
	 */
	public static String getSvnAddr(String localaddr) {
		String result = "";
		String ss = "";
		try {
			String command = "svn info ";
			Process p = Runtime.getRuntime().exec(command + localaddr);
			// 读取命令的输出信息
			InputStream is = p.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
			p.waitFor();
			ss = reader.readLine();
			while (ss != null) {
				if (ss.contains("URL:")) {
					result = ss.substring("URL:".length() + 1);
					break;
				}
				ss = reader.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
