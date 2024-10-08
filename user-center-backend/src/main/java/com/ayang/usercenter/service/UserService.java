package com.ayang.usercenter.service;

import com.ayang.usercenter.model.dto.UserAddRequest;
import com.ayang.usercenter.model.dto.UserQueryRequest;
import com.ayang.usercenter.model.dto.UserUpdateRequest;
import com.ayang.usercenter.model.entity.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 阿洋努力学习
 * @description 针对表【user(用户表)】的数据库操作Service
 * @createDate 2024-09-16 15:03:20
 */
public interface UserService extends IService<User> {


    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @param checkCode     校验编码
     * @return 新用户id
     */
    Long userRegister(String userAccount, String userPassword, String checkPassword, String checkCode);

    /**
     * 用户登录
     *
     * @param userAccount  用户账号
     * @param userPassword 用户密码
     * @param request      servlet请求
     * @return 脱敏后的用户信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 查询用户
     *
     * @param userName 用户名称
     * @return 查询到的用户列表
     */
    List<User> searchUsers(String userName);

    /**
     * 通过用户id删除用户
     *
     * @param userId 删除用户的id
     * @return 是否删除成功
     */
    boolean deleteUserById(Long userId);

    /**
     * 获取当前登录用户
     *
     * @param request 应用请求体
     * @return 当前登录用户脱敏数据
     */
    User getCurrentUser(HttpServletRequest request);

    /**
     * 用户注销
     *
     * @param request 应用请求体
     */
    boolean userLogout(HttpServletRequest request);

    /**
     * 判断是否是管理员
     *
     * @param request
     * @return
     */
    boolean isAdmin(HttpServletRequest request);

    /**
     * 判断是否是管理员
     *
     * @param user
     * @return
     */
    boolean isAdmin(User user);

    /**
     * 管理员添加用户
     *
     * @param userAddRequest
     * @return
     */
    long addUser(UserAddRequest userAddRequest);

    /**
     * 更新用户信息
     *
     * @param userUpdateRequest
     * @return
     */
    boolean updateUser(UserUpdateRequest userUpdateRequest);

    /**
     * 重置密码
     *
     * @param userId
     * @return
     */
    boolean resetPassword(Long userId);

    /**
     * 分页查询用户
     *
     * @param userQueryRequest
     * @return
     */
    Page<User> listUsersByPage(UserQueryRequest userQueryRequest);

    /**
     * 获取查询条件
     *
     * @param userQueryRequest
     * @return
     */
    QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest);

    /**
     * 更新个人信息
     *
     * @param userUpdateRequest
     * @param request
     * @return
     */
    boolean updateMyInfo(UserUpdateRequest userUpdateRequest, HttpServletRequest request);
}
