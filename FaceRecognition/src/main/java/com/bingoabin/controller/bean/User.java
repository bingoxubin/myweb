package com.bingoabin.controller.bean;

/**
 * @author xubin03
 * @date 2021/4/11 1:24 上午
 */
public class User {
	//唯一标识
	private int id;
	//用户的人脸标识
	private String face_id;
	//用户名
	private String username;
	//描述
	private String description;
	//首次录入城市
	private String city;
	//录入次数
	private int count;
	//上一次进店时间
	private long longtime;

	public User() {
	}

	public User(int id, String face_id, String username, String description, String city, int count, long longtime) {
		this.id = id;
		this.face_id = face_id;
		this.username = username;
		this.description = description;
		this.city = city;
		this.count = count;
		this.longtime = longtime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFace_id() {
		return face_id;
	}

	public void setFace_id(String face_id) {
		this.face_id = face_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public long getLongtime() {
		return longtime;
	}

	public void setLongtime(long longtime) {
		this.longtime = longtime;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", face_id='" + face_id + '\'' +
				", username='" + username + '\'' +
				", description='" + description + '\'' +
				", city='" + city + '\'' +
				", count=" + count +
				", longtime=" + longtime +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		User user = (User) o;

		if (id != user.id) return false;
		if (count != user.count) return false;
		if (longtime != user.longtime) return false;
		if (face_id != null ? !face_id.equals(user.face_id) : user.face_id != null) return false;
		if (username != null ? !username.equals(user.username) : user.username != null) return false;
		if (description != null ? !description.equals(user.description) : user.description != null) return false;
		return city != null ? city.equals(user.city) : user.city == null;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (face_id != null ? face_id.hashCode() : 0);
		result = 31 * result + (username != null ? username.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		result = 31 * result + (city != null ? city.hashCode() : 0);
		result = 31 * result + count;
		result = 31 * result + (int) (longtime ^ (longtime >>> 32));
		return result;
	}
}
