package com.ayang.usercenter.controller;

import com.ayang.usercenter.annotaion.AuthCheck;
import com.ayang.usercenter.common.BaseResponse;
import com.ayang.usercenter.common.ErrorCode;
import com.ayang.usercenter.constant.UserConstant;
import com.ayang.usercenter.exception.ThrowUtil;
import com.ayang.usercenter.model.dto.*;
import com.ayang.usercenter.model.entity.User;
import com.ayang.usercenter.service.UserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
        return BaseResponse.success(result);
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
        return BaseResponse.success(user);
    }

    /**
     * 用户注销
     *
     * @param request
     */
    @PostMapping("/logout")
    public BaseResponse userLogout(HttpServletRequest request) {
        ThrowUtil.throwIf(request == null, ErrorCode.PARAMS_ERROR);
        Boolean result = userService.userLogout(request);
        return BaseResponse.success(result);
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
        return BaseResponse.success(result);
    }

    /**
     * 管理员添加用户
     *
     * @param userAddRequest
     * @return
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse addUser(@RequestBody UserAddRequest userAddRequest) {
        long newUserId = userService.addUser(userAddRequest);
        return BaseResponse.success(newUserId);
    }


    /**
     * 删除用户
     *
     * @param userId 用户主键
     * @return 是否成功
     */
    @PostMapping("/delete")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse deleteUser(@RequestParam Long userId, HttpServletRequest request) {
        boolean result = userService.deleteUserById(userId);
        return BaseResponse.success(result);
    }

    /**
     * 修改用户信息
     *
     * @param userUpdateRequest 用户更新请求体
     * @return 用户信息
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse updateUser(@RequestBody UserUpdateRequest userUpdateRequest) {
        ThrowUtil.throwIf(userUpdateRequest.getId() == null, ErrorCode.PARAMS_ERROR);
        boolean result = userService.updateUser(userUpdateRequest);
        return BaseResponse.success(result);
    }

    /**
     * 重置用户密码
     *
     * @param userId
     * @return
     */
    @PostMapping("/reset/password")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse resetPassword(@RequestParam Long userId) {
        boolean result = userService.resetPassword(userId);
        return BaseResponse.success(result);
    }

    /**
     * 查询用户详情（管理员）
     *
     * @param userId
     * @return
     */
    @GetMapping("/detail")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse getUserDetail(@RequestParam Long userId) {
        User user = userService.getById(userId);
        ThrowUtil.throwIf(user == null, ErrorCode.NOT_FOUND_ERROR);
        return BaseResponse.success(user);
    }


    /**
     * 查询用户列表
     *
     * @param userName 模糊查询的用户名
     * @return 用户列表
     */
    @GetMapping("/search")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse searchUsers(String userName, HttpServletRequest request) {
        List<User> users = userService.searchUsers(userName);
        return BaseResponse.success(users);
    }

    /**
     * 分页获取用户列表
     *
     * @param userQueryRequest
     * @return
     */
    @GetMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse listUsersByPage(@RequestBody UserQueryRequest userQueryRequest) {
        Page<User> userPage = userService.listUsersByPage(userQueryRequest);
        return BaseResponse.success(userPage);

    }

    /**
     * 更新个人信息
     *
     * @param userUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update/my")
    public BaseResponse updateMyInfo(@RequestBody UserUpdateRequest userUpdateRequest, HttpServletRequest request) {
        boolean result = userService.updateMyInfo(userUpdateRequest, request);
        return BaseResponse.success(result);
    }


}
