package com.bingoabin.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * 歌曲识别的工具类
 *
 * @author W-ln
 * @version 1.0
 */
@Component
public class SongUtil {

	@Autowired
	private RestTemplate restTemplate;

	private String url = "https://webqbh.xfyun.cn/v1/service/v1/qbh";
	private String appId = "7b44db15";
	private String apiKey = "8db2d070827411c00322f9f21687e31a";
	private String engineType = "afs";
	private String aue = "raw";

	/**
	 * 调用webAPI查询歌曲
	 *
	 * @param byteArray
	 * @return
	 */
	public String searchSong(byte[] byteArray) throws UnsupportedEncodingException {
		//构建请求头
		HttpHeaders headers = new HttpHeaders();
		Map<String, String> map = buildHttpHeader();
		for (String key : map.keySet()) {
			headers.add(key, map.get(key));
		}
		//构建请求实体
		HttpEntity<byte[]> request = new HttpEntity<>(byteArray, headers);
		//发送post请求
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, request, String.class);
		return responseEntity.getBody();
	}

	/**
	 * 构建httpHeaders
	 *
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public Map<String, String> buildHttpHeader() throws UnsupportedEncodingException {
		String curTime = System.currentTimeMillis() / 1000L + "";
		String param = "{\"aue\":\"" + aue + "\",\"engine_type\":\"" + engineType + "\"}";
		String paramBase64 = new String(Base64.encodeBase64(param.getBytes("UTF-8")));
		String checkSum = DigestUtils.md5Hex(apiKey + curTime + paramBase64);
		Map<String, String> header = new HashMap<String, String>();
		header.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
		header.put("X-Param", paramBase64);
		header.put("X-CurTime", curTime);
		header.put("X-CheckSum", checkSum);
		header.put("X-Appid", appId);
		//System.out.println(header);
		return header;
	}
}
