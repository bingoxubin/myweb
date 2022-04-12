package com.bingoabin.controller.domain;

import java.io.Serializable;

/**
 * @author xubin03
 * @date 2021/3/22 8:34 下午
 */
public class User implements Serializable {
	private Integer id;
	private String username;
	private String PASSWORD;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPASSWORD() {
		return PASSWORD;
	}

	public void setPASSWORD(String PASSWORD) {
		this.PASSWORD = PASSWORD;
	}

	@Override
	public String toString() {
		return "Users{" +
				"id=" + id +
				", username='" + username + '\'' +
				", PASSWORD='" + PASSWORD + '\'' +
				'}';
	}
}

