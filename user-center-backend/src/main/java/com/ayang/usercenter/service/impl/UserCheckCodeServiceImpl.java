package com.ayang.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ayang.usercenter.model.entity.UserCheckCode;
import com.ayang.usercenter.service.UserCheckCodeService;
import com.ayang.usercenter.mapper.UserCheckCodeMapper;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @author 阿洋努力学习
 * @description 针对表【user_check_code】的数据库操作Service实现
 * @createDate 2024-09-21 18:08:13
 */
@Service
public class UserCheckCodeServiceImpl extends ServiceImpl<UserCheckCodeMapper, UserCheckCode>
		implements UserCheckCodeService {

	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

	private static String generateCode(int length) {
		Random random = new Random();
		StringBuilder code = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			int index = random.nextInt(CHARACTERS.length());
			code.append(CHARACTERS.charAt(index));
		}
		return code.toString();
	}


	@Override
	public String createCheckCode() {
		// 生成校验编码
		String checkCode = generateCode(8);
		UserCheckCode userCheckCode = new UserCheckCode();
		userCheckCode.setCheckCode(checkCode);
		this.save(userCheckCode);
		return checkCode;
	}

	@Override
	public boolean checkCode(String checkCode) {
		UserCheckCode check = this.lambdaQuery()
				.eq(UserCheckCode::getCheckCode, checkCode)
				.eq(UserCheckCode::getCheckStatus, 0).one();
		return check != null;
	}

	@Override
	public void useCheckCode(String checkCode) {
		this.lambdaUpdate()
				.eq(UserCheckCode::getCheckCode, checkCode)
				.eq(UserCheckCode::getCheckStatus, 0)
				.set(UserCheckCode::getCheckStatus, 1)
				.update();
	}

	public static void main(String[] args) {
		String code = generateCode(6);  // 生成6位编码
		System.out.println("Generated Code: " + code);
	}
}




