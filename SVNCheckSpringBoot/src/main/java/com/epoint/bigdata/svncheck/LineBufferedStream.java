package com.epoint.bigdata.svncheck;

import com.google.common.collect.EvictingQueue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LineBufferedStream {
	public LineBufferedStream(InputStream inputStream) {
		this.inputStream = inputStream;
		thd = new Thread(this::redirect);
		thd.setDaemon(true);// 当主进程关了后，该子进程自动关闭 反之，非守护进程，主进程结束后，子进程会阻止整个程序的终止
		thd.start();
	}

	private InputStream inputStream;
	private Thread thd = null;
	private EvictingQueue<String> _lines = EvictingQueue.create(10);
	private List<ILogMsg> loglst = new ArrayList<ILogMsg>();

	private void redirect() {
		try {
			String encodeType;
			if (System.getProperty("os.name").equals("Linux")) {
				encodeType = "UTF-8";
			} else {
				encodeType = "GBK";
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, encodeType));
			String line = "";
			while ((line = reader.readLine()) != null) {
				_lines.add(line);
				// 通知日志接收者
				for (ILogMsg log : loglst)
					log.log(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void AttachLog(ILogMsg log) {
		for (String item : _lines) {
			log.log(item);
		}
		loglst.add(log);
	}

	public void RemoveLog(ILogMsg log) {
		loglst.remove(log);
	}

	public void waitUntilClose() {
		try {
			thd.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public EvictingQueue<String> get_lines() {
		return _lines;
	}
}
