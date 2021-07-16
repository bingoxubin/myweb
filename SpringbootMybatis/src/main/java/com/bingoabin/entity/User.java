package com.bingoabin.entity;

import java.io.Serializable;

/**
 * @author xubin34
 * @date 2021/7/16 11:37 上午
 */
public class User implements Serializable {
	private int id;//编号
	private String username;//用户名
	private String password;//密码

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				'}';
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
