package com.ayang.usercenter.controller;

import com.ayang.usercenter.common.BaseResponse;
import com.ayang.usercenter.common.ErrorCode;
import com.ayang.usercenter.common.ResultUtils;
import com.ayang.usercenter.constant.UserConstant;
import com.ayang.usercenter.exception.BusinessException;
import com.ayang.usercenter.exception.ThrowUtil;
import com.ayang.usercenter.model.dto.UserLoginRequest;
import com.ayang.usercenter.model.dto.UserRegisterRequest;
import com.ayang.usercenter.model.entity.User;
import com.ayang.usercenter.service.UserCheckCodeService;
import com.ayang.usercenter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 阿洋努力学习
 * @description 用户邀请码接口
 * @date 2024-09-17
 **/
@RestController
@RequestMapping("/userCheckCode")
public class UserCheckCodeController {

	@Resource
	private UserService userService;
	@Resource
	private UserCheckCodeService userCheckCodeService;

	/**
	 * 用户注册
	 *
	 * @param request
	 * @return 邀请码
	 */
	@PostMapping("/register")
	public BaseResponse userRegister(HttpServletRequest request) {
		// 鉴权 仅管理员可查询
		userService.isAdmin(request);
		String checkCode = userCheckCodeService.createCheckCode();
		return ResultUtils.success(checkCode);
	}
}
