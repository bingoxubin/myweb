package com.epoint.bigdata.controller;

import com.alibaba.fastjson.JSONObject;
import com.epoint.bigdata.util.GetFileInfoUtil;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import static com.epoint.bigdata.svncheck.SVNPackageByDirWindows.getConfigValue;

/**
 * springboot页面跳转的controller类，用于页面跳转与页面跳转后的逻辑处理
 *
 * @author xumaosheng
 * @date 2019/8/13 14:26
 */
@Controller
public class MainController {
	//默认根路径访问，url访问入口，http://localhost:8080/
	@RequestMapping(value = "/")
	public String empty() {
		return "index";
	}

	//加上index访问，url访问入口，http://localhost:8080/index
	@RequestMapping("/index")
	public String index() {
		return "index";
	}

	//用于index.html中跳转的dialog地址
	@RequestMapping("/svn")
	public String svn() {
		return "svn";
	}

	@RequestMapping("/test")
	public String test() {
		return "test";
	}

	static String packageaddr = null;
	static String svnaddr = null;
	static String tooladdr = null;
	static String otherurl = null;

	static {
		try {
			packageaddr = getConfigValue("configuration.properties", "packageaddr");
			svnaddr = getConfigValue("configuration.properties", "svnaddr");
			tooladdr = getConfigValue("configuration.properties", "tooladdr");
			otherurl = getConfigValue("configuration.properties", "otherurl");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static Properties props = null;
	static Producer<String, String> producer = null;

	static {
		props = new Properties();
		//broker地址
		props.put("bootstrap.servers", "192.168.209.120:9093");
		//请求时候需要验证
		props.put("acks", "all");
		//请求失败时候需要重试
		props.put("retries", 0);
		//内存缓存区大小
		props.put("buffer.memory", 33554432);
		//指定消息key序列化方式
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		//指定消息本身的序列化方式
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		producer = new KafkaProducer<>(props);
	}

	@RequestMapping(value = "/kafkatest", method = RequestMethod.GET)
	public String kafka() {
		JSONObject json = new JSONObject();
		json.put("rowguid", UUID.randomUUID());
		json.put("startat", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		json.put("method", "POST");
		json.put("requestsize", "800");
		json.put("consumer", "oarest9V7");
		json.put("clientip", "106.43.208.110");
		json.put("responsesize", "400");
		json.put("status", "200");
		json.put("forwardtime", "25");
		json.put("requesttime", "23");
		json.put("requesturl", "http://218.4.136.126:8000/oacustomer_v7/handle_getunhandlelist_v7");
		json.put("context", "http://218.4.136.126:8000/oacustomer_v7/handle_getunhandlelist_v7");
		json.put("apiid", UUID.randomUUID());
		json.put("apiname", "获取用户照片流");
		json.put("apiguid", UUID.randomUUID());
		json.put("useragent",
		         "Mozilla/5.0 (Linux; Android 7.1.2; vivo X9s Build/N2G47H; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/55.0.2883.91 Mobile Safari/537.36 EpointEJS/M7_3.2.5.d");
		String value = json.toString();
		producer.send(new ProducerRecord<>("loadrunner", "key", value));
		System.out.println(json.toString());

		System.out.println("Message sent successfully");
		// producer.close();
		return "kafka";
	}

	//用于处理index.html中ajax的响应跳转逻辑
	@RequestMapping(value = "/listdir", method = RequestMethod.GET)
	public void listdir(HttpServletRequest request, HttpServletResponse response) {
		try {
			String path = request.getParameter("path");
			if (path == null || path.equals(""))
				path = "/";
			if (path.startsWith("path"))
				path = path.substring("path=".length());
			String value = request.getParameter("value");
			path = URLDecoder.decode(path, "UTF-8");

			response.setContentType("application/txt;charset=UTF-8");
			response.setStatus(200);
			OutputStream responseBody = response.getOutputStream();
			responseBody.write(new GetFileInfoUtil().getFileList(packageaddr, path).toString().getBytes("utf-8"));
			responseBody.flush();
			responseBody.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//用于处理svn.html中ajax的响应跳转逻辑
	@RequestMapping(value = "/addsvn", method = RequestMethod.GET)
	public void addsvn(HttpServletRequest request, HttpServletResponse response) {
		final String value = request.getParameter("value");
		System.out.println("=======================开始====================");
		System.out.println("页面上传来的svn路径为=======================" + value);
		try {
			String projectName = value.split("/")[value.split("/").length - 2];
			System.out.println("项目名称projectName为=======================" + projectName);

			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Process p1 = Runtime.getRuntime().exec("cmd /c start /wait svn checkout " + value + " " + svnaddr + "" + value.substring((value.lastIndexOf("/") + 1), value.length()));
						p1.waitFor();
						System.out.println("svn checkout " + value + " " + svnaddr + "" + value.substring((value.lastIndexOf("/") + 1), value.length()));
						System.out.println("checkout成功,目录为：" + value + " " + svnaddr + "" + value.substring((value.lastIndexOf("/") + 1), value.length()));

						// String svnaddr = "";
						svnaddr = svnaddr + "" + value.substring((value.lastIndexOf("/") + 1), value.length());
						// 将tool.py和打包.bat文件，拷贝到具体的模型目录中去
						System.out.println("开始拷贝tool.py和打包.bat！");
						System.out.println("从==" + tooladdr + "tool.py" + "==拷贝到==" + svnaddr + "\\tool.py");
						copyFile(tooladdr + "tool.py", svnaddr + "\\tool.py");
						if (!new File(svnaddr + "\\打包.bat").exists()) {
							copyFile(tooladdr + "打包.bat", svnaddr + "\\打包.bat");
							System.out.println("从==" + tooladdr + "打包.bat" + "==拷贝到==" + svnaddr + "\\打包.bat");
						}
						System.out.println("拷贝结束！");

						// 执行命令，如果发现更新svn，实时执行打包.bat文件
						System.out.println("开始打包");
						Process p2 = Runtime.getRuntime()
						                    .exec("cmd /c start /wait " + svnaddr + "\\打包.bat", null,
						                          new File(svnaddr));
						p2.waitFor();
						System.out.println("打包结束");

						// 将生成的target.zip拷贝到类似E:\系统安装包\印章识别\find_stamp_1.0_win.zip目录中
						System.out.println("开始拷贝打包后的文件");
						String whlfile = getWhlFile(svnaddr);
						if (!whlfile.equals("")) {
							copyFile(svnaddr + "\\" + whlfile,
							         packageaddr + projectName + "\\"
									         + whlfile.replaceAll("any", "win_amd64"));
							System.out.println("拷贝==" + svnaddr + "\\" + whlfile + "==到目的==" + packageaddr + projectName + "\\"
									                   + whlfile.replaceAll("any", "win_amd64"));
						} else {
							copyFile(svnaddr + "\\target.zip",
							         packageaddr + projectName + "\\"
									         + value.substring((value.lastIndexOf("/") + 1), value.length())
									         + "_"
									         + getVersion(
									         svnaddr + "\\packageinfo.json",
									         "version") + "_win.zip");
							System.out.println("拷贝==" + svnaddr + "\\target.zip" + "==到目的==" + packageaddr + projectName + "\\"
									                   + value.substring((value.lastIndexOf("/") + 1), value.length())
									                   + "_"
									                   + getVersion(
									svnaddr + "\\packageinfo.json",
									"version") + "_win.zip");
						}

						// 删除拷贝的tool.py 和打包.bat文件
						System.out.println("拷贝结束！");
						// 删除拷贝的tool.py 和打包.bat文件
						System.out.println("开始删除tool.py和删除打包.bat");
						deleteDir(new File(svnaddr + "\\tool.py"));
						deleteDir(new File(svnaddr + "\\打包.bat"));
						System.out.println("=======================结束====================");
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}).start();

			response.setContentType("application/txt;charset=UTF-8");
			response.setStatus(200);
			OutputStream responseBody = response.getOutputStream();
			responseBody.write(("{\"result\":\"" + value + "    成功！\"}").getBytes("utf-8"));
			responseBody.flush();
			responseBody.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!otherurl.equals(null) && !otherurl.equals("")) {
			sendGet(otherurl + "?value=", value);
		}
	}

	//用于处理svn.html中ajax的响应跳转逻辑
	@RequestMapping(value = "/addsvnlinux", method = RequestMethod.GET)
	public void addsvnlinux(HttpServletRequest request, HttpServletResponse response) {
		try {
			System.out.println("=======================开始====================");
			String value = URLDecoder.decode(request.getParameter("value"), "utf-8");
			System.out.println("页面上传来的svn路径为=======================" + value);
			String projectName = value.split("/")[value.split("/").length - 2];
			System.out.println("项目名称projectName为=======================" + projectName);
			try {
				Process p1 = Runtime.getRuntime().exec("svn checkout " + value + " " + svnaddr + value.substring((value.lastIndexOf("/") + 1), value.length()));
				p1.waitFor();
				System.out.println("checkout成功,目录为：" + value + " " + svnaddr + value.substring((value.lastIndexOf("/") + 1), value.length()));
				System.out.println("value.substring=========================" + value.substring((value.lastIndexOf("/") + 1), value.length()));
				// String svnaddr = "";
				svnaddr = svnaddr + value.substring((value.lastIndexOf("/") + 1), value.length());
				System.out.println("svnaddr=========================" + svnaddr);
				// 将tool.py和打包.bat文件，拷贝到具体的模型目录中去
				System.out.println("开始拷贝tool.py和打包.sh！");
				System.out.println("从==" + tooladdr + "tool.py" + "==拷贝到==" + svnaddr + "/tool.py");
				copyFile(tooladdr + "tool.py", svnaddr + "/tool.py");
				if (!new File(svnaddr + "/打包.sh").exists()) {
					copyFile(tooladdr + "打包.sh", svnaddr + "/打包.sh");
					System.out.println("从==" + tooladdr + "打包.sh" + "==拷贝到==" + svnaddr + "/打包.sh");
				}
				System.out.println("拷贝结束！");

				// 执行命令，如果发现更新svn，实时执行打包.bat文件
				System.out.println("开始打包");
				Process p2 = Runtime.getRuntime()
				                    .exec("sh " + svnaddr + "/打包.sh", null,
				                          new File(svnaddr));
				p2.waitFor();
				System.out.println("打包结束");

				System.out.println("开始拷贝打包后的文件");
				System.out.println(packageaddr + projectName + "/"
						                   + value.substring((value.lastIndexOf("/") + 1), value.length())
						                   + "_"
						                   + getVersion(
						svnaddr + "/packageinfo.json",
						"version") + "_linux.zip");

				String whlfile = getWhlFile(svnaddr);
				if (!whlfile.equals("")) {
					copyFile(svnaddr + "/" + whlfile,
					         packageaddr + projectName + "/"
							         + whlfile.replaceAll("any", "linux_x86_64"));
					System.out.println("拷贝==" + svnaddr + "/" + whlfile + "==到目的==" + packageaddr + projectName + "/"
							                   + whlfile.replaceAll("any", "linux_x86_64"));
				} else {
					// 将生成的target.zip拷贝到类似E:\系统安装包\印章识别\find_stamp_1.0_win.zip目录中
					copyFile(svnaddr + "/target.zip",
					         packageaddr + projectName + "/"
							         + value.substring((value.lastIndexOf("/") + 1), value.length())
							         + "_"
							         + getVersion(
							         svnaddr + "/packageinfo.json",
							         "version") + "_linux.zip");
					System.out.println("拷贝==" + svnaddr + "/target.zip" + "==到目的==" + packageaddr + projectName + "/"
							                   + value.substring((value.lastIndexOf("/") + 1), value.length())
							                   + "_"
							                   + getVersion(
							svnaddr + "/packageinfo.json",
							"version") + "_linux.zip");
				}

				System.out.println("拷贝结束！");
				// 删除拷贝的tool.py 和打包.bat文件
				System.out.println("开始删除tool.py和删除打包.sh");
				deleteDir(new File(svnaddr + "/tool.py"));
				deleteDir(new File(svnaddr + "/打包.sh"));
				System.out.println("=======================结束====================");
			} catch (Exception e) {
				e.printStackTrace();
			}
			response.setContentType("application/txt;charset=UTF-8");
			response.setStatus(200);
			OutputStream responseBody = response.getOutputStream();
			responseBody.write(("{\"result\":\"" + value + "    成功！\"}").getBytes("utf-8"));
			responseBody.flush();
			responseBody.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 描述：将文件拷贝到指定目录
	 *
	 * @param oldAddress
	 * @param newAddress
	 * @throws Exception
	 */
	private static void copyFile(String oldAddress, String newAddress) throws Exception {
		if (new File(oldAddress).exists()) {
			FileInputStream input = new FileInputStream(oldAddress);
			if (!new File(newAddress).getParentFile().exists())
				new File(newAddress).getParentFile().mkdirs();
			FileOutputStream output = new FileOutputStream(newAddress);// 注意：newAddress必须包含文件名字，比如说将D:/AAA文件夹下的文件"a.xml"复制到D:\test目录下，则newAddress必须为D:\test\a.xml
			// oldAddress必须是a.xml文件的全路径，即D:\AAA\a.xml,否则就会报IO异常的错误
			int in = input.read();
			while (in != -1) {
				output.write(in);
				in = input.read();
			}
			input.close();
			output.close();
		}
	}

	/**
	 * 描述：删除指定目录及其文件
	 *
	 * @param dir
	 * @return
	 */
	private static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			// 递归删除目录中的子目录下
			for (int i = 0; i < children.length; i++) {
				File file = new File(dir + File.separator + children[i]);
				file.delete();
			}
		}
		// 目录此时为空，可以删除
		return dir.delete();
	}

	/**
	 * 描述：从packageinfo.json文件中获取version
	 *
	 * @param file
	 * @param version
	 * @return
	 */
	public static String getVersion(String file, String version) {
		String s = "";
		try {
			if (!new File(file).exists())
				return "1.0";
			BufferedReader bReader = new BufferedReader(new FileReader(file));
			// 打印输出信息
			String line = bReader.readLine();
			while (line != null) {
				if (line.contains("version")) {
					s = line.substring(line.indexOf("version") + "version':".length() + 1).replaceAll(",", "");
				}
				line = bReader.readLine();
			}
			return "1.0";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s.replaceAll("\"", "");
	}

	/**
	 * 发送get请求
	 *
	 * @return java.lang.String
	 * @author xumaosheng
	 * @date 2019/8/28 17:10
	 * @params [url, param]
	 */
	public static void sendGet(String path, String data) {
		try {
			URL url = new URL(path + URLEncoder.encode(data, "utf-8"));
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			PrintWriter out = null;
			conn.setDoInput(true);
			conn.setRequestMethod("GET");//GET和POST必须全大写
			conn.connect();
			InputStream is = conn.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
			String str = "";
			while ((str = br.readLine()) != null) {
				str = new String(str.getBytes(), "UTF-8");//解决中文乱码问题
				System.out.println(str);
			}
			is.close();
			conn.disconnect();
			System.out.println("完整结束");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 判断某个目录中是否有.whl文件
	 *
	 * @return com.alibaba.fastjson.JSONArray
	 * @author xumaosheng
	 * @date 2019/9/1 12:26
	 * @params [rootpath, currentpath]
	 */
	public static String getWhlFile(String path) {
		File fileCatalog = new File(path);
		File[] fileList = fileCatalog.listFiles();

		try {
			for (int i = 0; i < fileList.length; i++) {
				if (fileList[i].isFile()) {
					String fileName = fileList[i].getName();
					if (getExtension(fileName).equals("whl")) {
						return fileName;
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
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
