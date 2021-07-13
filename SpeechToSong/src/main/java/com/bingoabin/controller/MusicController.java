package com.bingoabin.controller;

import com.bingoabin.util.FileUtil;
import com.bingoabin.util.SongUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * @author xubin03
 * @date 2021/4/12 1:18 上午
 */
@Controller
public class MusicController {
	@ResponseBody
	@RequestMapping("/music1")
	//注意：加了RespondeBody  即使使用了Controller，也可以直接返回字符串
	public String music1() {
		return "music。。。";
	}

	@RequestMapping("/music2")
	//注意：去掉了ResponseBody 即请求页面，需要加上application.yml配置
	public String music2() {
		return "music.html";
	}



	@Autowired
	private SongUtil songUtil;

	private String filePath;//歌曲的存储路径
	private final String FILENAME = "/song.wav"; //歌曲的名称

	//@ResponseBody
	@RequestMapping("/music")
	public String music() {
		System.out.println("music-----------------");
		return "music.html";
	}

	@ResponseBody
	@RequestMapping("/searchMusic")
	public String searchMusic() {
		//准备歌曲--文件转换为二进制数组
		byte[] byteArray = new byte[0];
		try {
			byteArray = FileUtil.readFile(filePath + FILENAME);
			//按照接口的要求发送数据识别歌曲的接口
			//发送的数据：请求体--歌曲文件   请求头
			String res = songUtil.searchSong(byteArray);
			System.out.println("查询歌曲的结果是：" + res);
			return res;
		} catch (IOException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	/**
	 * 收集声音---服务器端获取到客户端的哼唱的歌曲文件
	 * <p>
	 * 处理声音---根据api识别声音得到结果
	 */
	@ResponseBody
	@RequestMapping("/uploadSong")
	public String uploadSong(@RequestParam("audioData") MultipartFile song, HttpServletRequest request) {
		//获取存储路径
		filePath = request.getServletContext().getRealPath("/song");
		//将上传文件写入到服务器的指定路径
		//判断文件夹是否存在
		File fileDir = new File(filePath);
		if (!fileDir.exists()) {
			fileDir.mkdirs();//不存在就创建文件夹
		}
		//存储文件
		if (FileUtil.saveFile(filePath, FILENAME, song)) {
			System.out.println("写入成功！路径：" + (filePath + FILENAME));
			return "ok";
		}
		return "error";
	}
}
