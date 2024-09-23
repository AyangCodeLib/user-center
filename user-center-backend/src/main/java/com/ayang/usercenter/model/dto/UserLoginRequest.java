package com.ayang.usercenter.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 阿洋努力学习
 * @description 用户登录请求体
 * @date 2024-09-17
 **/
@Data
public class UserLoginRequest implements Serializable {

	private static final long serialVersionUID = 8762278339727746681L;
	/**
	 * 用户账户
	 */
	private String userAccount;

	/**
	 * 用户密码
	 */
	private String userPassword;
}
