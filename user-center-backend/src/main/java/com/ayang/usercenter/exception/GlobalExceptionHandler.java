package com.ayang.usercenter.exception;

import com.ayang.usercenter.common.BaseResponse;
import com.ayang.usercenter.common.ErrorCode;
import com.ayang.usercenter.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author shy
 * @description 全局异常处理器
 * @date 2024-09-22
 **/
@RestControllerAdvice
@Slf4j
@ResponseBody
public class GlobalExceptionHandler {

	@ExceptionHandler(BusinessException.class)
	public BaseResponse businessExceptionHandler(HttpServletResponse response, BusinessException e) {
		log.error("BusinessException: {}", e.getMessage(), e);
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		return ResultUtils.error(e.getCode(), e.getMessage(), e.getDescription());
	}

	@ExceptionHandler(RuntimeException.class)
	public BaseResponse runtimeExceptionHandler(HttpServletResponse response, RuntimeException e) {
		log.error("RuntimeException:", e);
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		return ResultUtils.error(ErrorCode.SYSTEM_ERROR, "");
	}
}
