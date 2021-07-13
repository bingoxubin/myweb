package com.epoint.bigdata.svncheck;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT;
import org.apache.log4j.Logger;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.epoint.bigdata.svncheck.SVNPackageByDirWindows.getConfigValue;

/**
 * 实现svn自动更新，打包
 *
 * @author xumaosheng
 * @date 2019/8/16 14:55
 * @params
 * @return
 */
public class SVNPackageByDirLinux {

	static String packageaddr = null;
	static String svnaddr = null;
	static String tooladdr = null;
	private static final String MVN_TAG = "target/classes/";
	public static Map<Long, ProcInfo> mapProcessID = new HashMap<Long, ProcInfo>();
	private static Logger logger = Logger.getLogger(SVNPackageByDirLinux.class);

	/**
	 * 程序入口
	 *
	 * @return void
	 * @author xumaosheng
	 * @date 2019/8/16 15:50
	 * @params [args]
	 */
	public static void main(String[] args) throws Exception {
		InputStream in = null;
		try {
			packageaddr = getConfigValue("configuration.properties", "packageaddr");
			svnaddr = getConfigValue("configuration.properties", "svnaddr");
			tooladdr = getConfigValue("configuration.properties", "tooladdr");
		} catch (Exception e) {
			e.printStackTrace();
		}
		timerPackage(packageaddr, svnaddr, tooladdr);
	}

	/**
	 * 更新svn源后，如果本地版本较老，直接update本地
	 *
	 * @return void
	 * @author xumaosheng
	 * @date 2019/8/16 15:45
	 * @params [svnaddr, packageaddr]
	 */
	public static void timerPackage(String packageaddr, String svnaddr, String tooladdr) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				try {
					File fileCatalog = new File(svnaddr);
					File[] fileList = fileCatalog.listFiles();
					for (int i = 0; i < fileList.length; i++) {
						if (fileList[i].toString().contains(".svn") || fileList[i].toString() == "") {
							continue;
						}
						String localversion = getVersion(fileList[i].toString());
						String svnversion = getVersion(getSvnAddr(fileList[i].toString()));
						String result = URLDecoder.decode(getSvnAddr(fileList[i].toString()), "utf-8");
						String projectName = result.split("/")[result.split("/").length - 2];
						if (!localversion.equals(svnversion)) {
							System.out.println("=======================此处有更新====================");
							System.out.println("=======================开始====================");
							System.out.println("当前svn地址result为：" + result);
							System.out.println("当前项目名projectName为：" + projectName);
							System.out.println("当前本地版本为：" + localversion);
							System.out.println("svn服务端版本为：" + svnversion);
							Process p = Runtime.getRuntime().exec("svn update " + fileList[i]);
							p.waitFor();
							System.out.println("update成功,目录为：" + fileList[i]);

							System.out.println("开始拷贝tool.py和打包.sh！");
							System.out.println("从==" + tooladdr + "==拷贝到==" + fileList[i].toString());
							if (!new File(fileList[i].toString() + "/打包.sh").exists()) {
								copyFile(tooladdr + "打包.sh", fileList[i].toString() + "/打包.sh");
							}

							copyFile(tooladdr + "tool.py", fileList[i].toString() + "/tool.py");
							System.out.println("拷贝结束！");
							// 执行命令，如果发现更新svn，实时执行打包.sh文件
							System.out.println("开始打包");
							Process p1 = Runtime.getRuntime()
							                    .exec("sh " + fileList[i].toString() + "/打包.sh", null,
							                          new File(fileList[i].toString()));

							long pid = getProcessID(p1);
							ProcInfo pinfo = new ProcInfo();// 单列模式情况下搞一个内部类
							pinfo.process = p1;
							pinfo.inputStream = new LineBufferedStream(p1.getInputStream());
							pinfo.errorStream = new LineBufferedStream(p1.getErrorStream());
							pinfo.outputStream = p1.getOutputStream();
							mapProcessID.put(pid, pinfo);

							ListenLog(pid, new ILogMsg() {
								@Override
								public void log(String msg) {
									System.out.println("-->" + msg);
								}
							});

							p1.waitFor();
							System.out.println("打包结束");

							System.out.println("开始拷贝打包后的文件");
							String whlfile = getWhlFile(fileList[i].toString());
							if (!whlfile.equals("")) {
								copyFile(fileList[i].toString() + "/" + whlfile,
								         packageaddr + projectName + "/"
										         + whlfile.replaceAll("any", "linux_x86_64"));
								System.out.println("拷贝==" + fileList[i].toString() + "/" + whlfile + "==到目的==" + packageaddr + projectName + "/"
										                   + whlfile.replaceAll("any", "linux_x86_64"));
							} else {
								// 将生成的target.zip拷贝到类似E:\系统安装包\印章识别\find_stamp_1.0_win.zip目录中
								copyFile(fileList[i].toString() + "/target.zip",
								         packageaddr + projectName + "/"
										         + fileList[i].toString().substring((fileList[i].toString().lastIndexOf("/") + 1), fileList[i].toString().length())
										         + "_"
										         + getVersion(
										         fileList[i].toString() + "/packageinfo.json",
										         "version") + "_linux.zip");
								System.out.println("拷贝==" + fileList[i].toString() + "/target.zip" + "==到目的==" + packageaddr + projectName + "/"
										                   + fileList[i].toString().substring((fileList[i].toString().lastIndexOf("/") + 1), fileList[i].toString().length())
										                   + "_"
										                   + getVersion(
										fileList[i].toString() + "/packageinfo.json",
										"version") + "_linux.zip");
							}
							System.out.println("拷贝结束！");
							// 删除拷贝的tool.py 和打包.bat文件
							System.out.println("开始删除tool.py和删除打包.sh");
							deleteDir(new File(fileList[i].toString() + "/tool.py"));
							deleteDir(new File(fileList[i].toString() + "/打包.sh"));
							System.out.println("=======================结束====================");
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 5000, 5000);
	}

	/**
	 * 描述：根据路径获取svn的版本号
	 *
	 * @param localaddr
	 */
	public static String getVersion(String localaddr) {
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
				if (ss.contains("Last Changed Rev")) {
					result = ss.substring("Last Changed Rev".length() + 2);
				}
				ss = reader.readLine();
			}
		} catch (
				Exception e) {
			e.printStackTrace();
		}
		return result;
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

	public static class ProcInfo {
		public Process process;
		public LineBufferedStream inputStream;
		public LineBufferedStream errorStream;
		public OutputStream outputStream;
	}

	// 根据进程对象返回进程号
	private static long getProcessID(Process p) {
		long result = -1;
		try {
			// for windows
			if (p.getClass().getName().equals("java.lang.Win32Process")
					|| p.getClass().getName().equals("java.lang.ProcessImpl")) {
				Field f = p.getClass().getDeclaredField("handle");
				f.setAccessible(true);
				long handl = f.getLong(p);
				Kernel32 kernel = Kernel32.INSTANCE;
				WinNT.HANDLE hand = new WinNT.HANDLE();
				hand.setPointer(Pointer.createConstant(handl));
				result = kernel.GetProcessId(hand);
				f.setAccessible(false);

			}
			// for unix based operating systems
			else if (p.getClass().getName().equals("java.lang.UNIXProcess")) {
				Field f = p.getClass().getDeclaredField("pid");
				f.setAccessible(true);
				result = f.getLong(p);
				f.setAccessible(false);
			}
		} catch (Exception ex) {
			result = -1;
		}
		return result;
	}

	/**
	 * 添加进程日志监听器
	 */
	public static void ListenLog(long pid, ILogMsg log) {
		if (!mapProcessID.containsKey(pid))
			return;
		mapProcessID.get(pid).inputStream.AttachLog(log);
		mapProcessID.get(pid).errorStream.AttachLog(log);
	}
}
