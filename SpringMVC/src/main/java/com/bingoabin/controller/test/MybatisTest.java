package com.bingoabin.controller.test;

import com.bingoabin.controller.domain.User;
import com.bingoabin.controller.dao.IUserDao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

/**
 * @author xubin03
 * @date 2021/3/23 9:11 下午
 */
public class MybatisTest {
	/**
	 * 测试查询用户
	 *
	 * @throws Exception
	 */
	@Test
	public void run1() throws Exception {
		// 加载配置文件
		InputStream in = Resources.getResourceAsStream("mybatis.xml");
		// 创建SqlSessionFactory对象
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
		// 创建SqlSession对象
		SqlSession session = factory.openSession();
		// 获取到代理对象
		IUserDao dao = session.getMapper(IUserDao.class);
		// 查询所有数据
		List<User> list = dao.findUsers();
		for (User users : list) {
			System.out.println(users);
		}
		// 关闭资源
		session.close();
		in.close();
	}

	/**
	 * 测试注册
	 *
	 * @throws Exception
	 */
	@Test
	public void run2() throws Exception {
		User users = new User();
		users.setUsername("xubin2");
		users.setPASSWORD("111111");

		// 加载配置文件
		InputStream in = Resources.getResourceAsStream("mybatis.xml");
		// 创建SqlSessionFactory对象
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
		// 创建SqlSession对象
		SqlSession session = factory.openSession();
		// 获取到代理对象
		IUserDao dao = session.getMapper(IUserDao.class);

		// 保存
		dao.insertUsers(users);

		// 提交事务
		session.commit();

		// 关闭资源
		session.close();
		in.close();
	}
}
