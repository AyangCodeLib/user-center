package com.ayang.usercenter.service;

import com.ayang.usercenter.UserCenterApplication;
import com.ayang.usercenter.model.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

/**
 * 用户服务测试
 *
 * @author 阿洋努力学习
 */
@SpringBootTest(classes = {UserCenterApplication.class})
class UserServiceTest {

	@Resource
	private UserService userService;
	@Resource
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Test
	public void testAddUser() {
		User user = new User();
		user.setUserName("阿洋努力学习");
		user.setUserAccount("ayang");
		user.setGender(0);
		user.setUserPassword("111111");
		user.setUserStatus(0);

		boolean save = userService.save(user);
		Assertions.assertTrue(save);
	}

	@Test
	public void testEncodePassword() {
		String encode = bCryptPasswordEncoder.encode("12345678");
		System.out.println(encode);
		Assertions.assertTrue(bCryptPasswordEncoder.matches("12345678", encode));
	}

	@Test
	void userRegister() {
		String userAccount = "ayangCheckCode";
		String errorUserAccount1 = "ay ang";
		String userPassword = "12345678";
		String checkPassword = "12345678";
		String errorCheckPassword = "111111";
		String checkCode = "UICTr0K8";
		// 校验非空
		Long result = userService.userRegister("", "", checkPassword, checkCode);
		Assertions.assertEquals(result, -1);
		// 校验账号长度
		result = userService.userRegister("ay", userPassword, checkPassword, checkCode);
		Assertions.assertEquals(-1, result);
		// 校验密码长度
		result = userService.userRegister(errorUserAccount1, userPassword, checkPassword, checkCode);
		Assertions.assertEquals(-1, result);
		// 账户名含有空格等特殊字符
		result = userService.userRegister(errorUserAccount1, userPassword, checkPassword, checkCode);
		Assertions.assertEquals(-1, result);
		// 密码和校验密码不同
		result = userService.userRegister(userAccount, userPassword, errorCheckPassword, checkCode);
		Assertions.assertEquals(-1, result);
		// 校验账户重复
		result = userService.userRegister("阿洋努力学习", userPassword, errorCheckPassword, checkCode);
		Assertions.assertEquals(-1, result);
		// 正常
		result = userService.userRegister(userAccount, userPassword, checkPassword, checkCode);
		Assertions.assertTrue(result > 0);
	}

	@Test
	void userLogin() {
		String userAccount = "ayang";
		String userPassword = "12345678";
		User user = userService.userLogin(userAccount, userPassword, null);
		Assertions.assertNotNull(user);
	}
}