package com.ayang.usercenter.exception;

import com.ayang.usercenter.common.ErrorCode;

/**
 * @author shy
 * @description 错误判断工具
 * @date 2024-09-22
 **/
public class ThrowUtil {

	public static void throwIf(boolean isThrow, ErrorCode errorCode, String description) {
		if (isThrow) {
			throw new BusinessException(errorCode, description);
		}
	}

	public static void throwIf(boolean isThrow, ErrorCode errorCode) {
		if (isThrow) {
			throw new BusinessException(errorCode);
		}
	}
}
