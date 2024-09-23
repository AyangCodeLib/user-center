package com.ayang.usercenter.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 阿洋努力学习
 * @description 用户注册请求体
 * @date 2024-09-17
 **/
@Data
public class UserRegisterRequest implements Serializable {

	private static final long serialVersionUID = -2460089783302001968L;

	/**
	 * 用户账户
	 */
	private String userAccount;

	/**
	 * 用户密码
	 */
	private String userPassword;

	/**
	 * 校验密码
	 */
	private String checkPassword;

	/**
	 * 校验编号
	 */
	private String checkCode;
}
