package com.ayang.usercenter.common;

import lombok.Getter;

/**
 * @author 阿洋努力学习
 * @description 错误码
 * @date 2024-09-22
 **/
public enum ErrorCode {


	SUCCESS(200, "success", ""),
	PARAMS_ERROR(40000, "请求参数错误", ""),
	NULL_ERROR(40001, "数据为空", ""),
	NOT_LOGIN(40100, "未登录", ""),
	NO_AUTH(40101, "无权限", ""),
	SYSTEM_ERROR(50000, "系统内部异常", ""),
	;


	/**
	 * 状态码信息
	 */
	private final int code;
	/**
	 * 状态码信息
	 */
	private final String message;
	private final String description;


	ErrorCode(int code, String message, String description) {
		this.code = code;
		this.message = message;
		this.description = description;
	}


	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public String getDescription() {
		return description;
	}
}
