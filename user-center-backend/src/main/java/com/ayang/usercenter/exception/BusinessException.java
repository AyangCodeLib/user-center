package com.ayang.usercenter.exception;

import com.ayang.usercenter.common.ErrorCode;

/**
 * @author shy
 * @description 自定义异常
 * @date 2024-09-22
 **/
public class BusinessException extends RuntimeException {

	private final int code;

	public BusinessException(int code, String message, String description) {
		super(message);
		this.code = code;
	}

	public BusinessException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.code = errorCode.getCode();
	}

	public BusinessException(ErrorCode errorCode, String message) {
		super(message);
		this.code = errorCode.getCode();
	}

	private static final long serialVersionUID = 1657649499334789001L;

	public int getCode() {
		return code;
	}
}
