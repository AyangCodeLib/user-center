package com.ayang.usercenter.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 阿洋努力学习
 * @description 基本通用返回
 * @date 2024-09-22
 **/
@Data
public class BaseResponse implements Serializable {

	/**
	 * 返回编码
	 */
	private int code;

	/**
	 * 返回数据
	 */
	private Object data;

	/**
	 * 返回消息
	 */
	private String message;

	/**
	 * 描述
	 */
	private String description;

	private static final long serialVersionUID = 8817244408506576015L;

	public BaseResponse(int code, Object data, String message, String description) {
		this.code = code;
		this.data = data;
		this.message = message;
		this.description = description;
	}

	public BaseResponse(int code, Object data, String message) {
		this(code, data, message, "");
	}

	public BaseResponse(int code, Object data) {
		this(code, data, "", "");
	}

	public BaseResponse(ErrorCode errorCode) {
		this(errorCode.getCode(), null, errorCode.getMessage());
	}
}
