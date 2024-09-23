package com.ayang.usercenter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author 阿洋努力学习
 * @description 用户中心配置Bean类
 * @date 2024-09-16
 **/
@Configuration
public class UserCenterConfig {

	@Bean
	public BCryptPasswordEncoder bcryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
