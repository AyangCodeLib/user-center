package com.ayang.usercenter.service;

import com.ayang.usercenter.UserCenterApplication;
import com.ayang.usercenter.model.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {UserCenterApplication.class})
class UserCheckCodeServiceTest {

	@Resource
	private UserCheckCodeService userCheckCodeService;

	@Test
	public void testCreateCheckCode() {
		String checkCode = userCheckCodeService.createCheckCode();
		Assertions.assertNotNull(checkCode);
	}

}