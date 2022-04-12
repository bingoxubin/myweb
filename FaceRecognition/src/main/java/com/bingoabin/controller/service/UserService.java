package com.bingoabin.controller.service;

import com.bingoabin.controller.bean.User;
import com.bingoabin.controller.db.UserDao;
import com.bingoabin.controller.db.UserDaoImpl;

/**
 * @author xubin03
 * @date 2021/4/11 2:35 上午
 */
//未来用户操作数据库不是直接找dao，而是找service，如果发现UserDaoImpl有问题了，我们只需要更换UserDaoImpl
public class UserService {
	private static UserDao dao = new UserDaoImpl();

	/**
	 * 用于新增用户人脸信息
	 *
	 * @param user 用户对象
	 * @return 新增的结果，大于0表示成功
	 */
	public static int insert(User user) {
		return dao.insert(user);
	}

	/**
	 * 基于人脸标识，进行用户次数的新增
	 *
	 * @param face_id 人脸标识
	 * @return 新增后的用户全部信息
	 */
	public static User count(String face_id) {
		return dao.count(face_id);
	}

	/**
	 * 通过人脸id，修改用户的姓名和备注信息
	 *
	 * @param face_id 人脸标识码
	 * @param user    用户的姓名和备注的信息对象
	 * @return 新增的结果，大于0表示成功
	 */
	public static int updateUserByFaceId(String face_id, User user) {
		return dao.updateUserByFaceId(face_id, user);
	}
}
