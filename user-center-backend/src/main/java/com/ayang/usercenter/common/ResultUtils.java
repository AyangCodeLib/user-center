package com.ayang.usercenter.common;

/**
 * @author 阿洋努力学习
 * @description 返回工具类
 * @date 2024-09-22
 **/
public class ResultUtils {

	/**
	 * 成功
	 *
	 * @param data
	 * @return
	 */
	public static BaseResponse success(Object data) {
		return new BaseResponse(0, data, "ok");
	}

	/**
	 * 失败
	 *
	 * @param errorCode
	 * @return
	 */
	public static BaseResponse error(ErrorCode errorCode) {
		return new BaseResponse(errorCode);
	}

	/**
	 * 失败
	 *
	 * @param code
	 * @param message
	 * @return
	 */
	public static BaseResponse error(int code, String message) {
		return new BaseResponse(code, null, message);
	}

	/**
	 * 失败
	 *
	 * @param errorCode
	 * @param message
	 * @return
	 */
	public static BaseResponse error(ErrorCode errorCode, String message) {
		return new BaseResponse(errorCode.getCode(), null, message);
	}
}
