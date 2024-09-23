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
import com.ayang.usercenter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 阿洋努力学习
 * @description 用户接口
 * @date 2024-09-17
 **/
@RestController
@RequestMapping("/user")
public class UserController {

	@Resource
	private UserService userService;

	/**
	 * 用户注册
	 *
	 * @param userRegisterRequest 用户注册请求体
	 * @return 用户主键
	 */
	@PostMapping("/register")
	public BaseResponse userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
		String userAccount = userRegisterRequest.getUserAccount();
		String userPassword = userRegisterRequest.getUserPassword();
		String checkPassword = userRegisterRequest.getCheckPassword();
		String checkCode = userRegisterRequest.getCheckCode();
		ThrowUtil.throwIf(StringUtils.isAnyBlank(userAccount, userPassword, checkPassword), ErrorCode.PARAMS_ERROR, "用户账户、用户密码、校验密码、邀请码不能为空");
		Long result = userService.userRegister(userAccount, userPassword, checkPassword, checkCode);
		return ResultUtils.success(result);
	}

	/**
	 * 用户登录
	 *
	 * @param userLoginRequest 用户登录请求体
	 * @param request          用户服务请求体
	 * @return 用户脱敏信息
	 */
	@PostMapping("/login")
	public BaseResponse userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
		String userAccount = userLoginRequest.getUserAccount();
		String userPassword = userLoginRequest.getUserPassword();
		ThrowUtil.throwIf(StringUtils.isAnyBlank(userAccount, userPassword), ErrorCode.PARAMS_ERROR, "用户账户、用户密码不能为空");
		User user = userService.userLogin(userAccount, userPassword, request);
		return ResultUtils.success(user);
	}

	/**
	 * 用户注销
	 *
	 * @param request
	 */
	@PostMapping("/logout")
	public BaseResponse userLogout(HttpServletRequest request) {
		Integer result = userService.userLogout(request);
		return ResultUtils.success(result);
	}

	/**
	 * 获取当前用户信息
	 *
	 * @param request
	 * @return
	 */
	@GetMapping("/current")
	public BaseResponse getCurrentUser(HttpServletRequest request) {
		User result = userService.getCurrentUser(request);
		return ResultUtils.success(result);
	}

	/**
	 * 查询用户列表
	 *
	 * @param userName 模糊查询的用户名
	 * @return 用户列表
	 */
	@GetMapping("/search")
	public BaseResponse searchUsers(String userName, HttpServletRequest request) {
		// 鉴权 仅管理员可查询
		userService.isAdmin(request);
		List<User> users = userService.searchUsers(userName);
		return ResultUtils.success(users);
	}

	/**
	 * 删除用户
	 *
	 * @param userId 用户主键
	 * @return 是否成功
	 */
	@PostMapping("/delete")
	public BaseResponse deleteUser(@RequestParam Long userId, HttpServletRequest request) {
		// 鉴权 仅管理员可删除
		userService.isAdmin(request);
		boolean result = userService.deleteUserById(userId);
		return ResultUtils.success(result);
	}
}
