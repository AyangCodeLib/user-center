package com.ayang.usercenter.service.impl;

import com.ayang.usercenter.common.ErrorCode;
import com.ayang.usercenter.constant.CommonConstant;
import com.ayang.usercenter.constant.UserConstant;
import com.ayang.usercenter.exception.BusinessException;
import com.ayang.usercenter.exception.ThrowUtil;
import com.ayang.usercenter.mapper.UserMapper;
import com.ayang.usercenter.model.dto.UserAddRequest;
import com.ayang.usercenter.model.dto.UserQueryRequest;
import com.ayang.usercenter.model.dto.UserUpdateRequest;
import com.ayang.usercenter.model.entity.User;
import com.ayang.usercenter.model.enums.UserRoleEnum;
import com.ayang.usercenter.service.UserCheckCodeService;
import com.ayang.usercenter.service.UserService;
import com.ayang.usercenter.utils.GenerateUtil;
import com.ayang.usercenter.utils.SqlUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author 阿洋努力学习
 * @description 针对表【user(用户表)】的数据库操作Service实现
 * @createDate 2024-09-16 15:03:20
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Resource
    private UserCheckCodeService userCheckCodeService;

    /**
     * 正则表达式，匹配非字母、数字、下划线和空格的字符
     */
    private static final String SPECIAL_CHARACTERS_PATTERN = "^[a-zA-Z0-9_]+$";

    /**
     * 校验用户数据
     *
     * @param userAccount
     * @param userPassword
     */
    private void verifyUser(String userAccount, String userPassword) {
        ThrowUtil.throwIf(userAccount.length() < 4 || userAccount.length() > 16, ErrorCode.PARAMS_ERROR, "用户账号长度应大于4，小于16");
        ThrowUtil.throwIf(userPassword.length() < 8 || userPassword.length() > 30, ErrorCode.PARAMS_ERROR, "用户密码长度应大于8，小于30");
        // 校验账户不能包含特殊字符
        ThrowUtil.throwIf(isValidUserAccount(userAccount), ErrorCode.PARAMS_ERROR, "用户账户不能包含特殊字符");
    }


    @Override
    public Long userRegister(String userAccount, String userPassword, String checkPassword, String checkCode) {
        // 1. 校验
        ThrowUtil.throwIf(StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, checkCode), ErrorCode.PARAMS_ERROR, "参数为空");
        verifyUser(userAccount, userPassword);
        // 密码长度
        ThrowUtil.throwIf(checkPassword.length() < 8 || checkPassword.length() > 30, ErrorCode.PARAMS_ERROR, "校验密码长度应大于8，小于30");
        // 邀请码长度
        ThrowUtil.throwIf(checkCode.length() > 10, ErrorCode.PARAMS_ERROR, "邀请码过长");
        // 密码和校验密码相同
        ThrowUtil.throwIf(!Objects.equals(userPassword, checkPassword), ErrorCode.PARAMS_ERROR, "密码和校验密码不同");
        // todo 分布式注册加锁
        // 账户不能重复
        User one = this.lambdaQuery().eq(User::getUserAccount, userAccount).one();
        ThrowUtil.throwIf(one != null, ErrorCode.PARAMS_ERROR, "用户账户已经被注册");
        // 校验编号是否存在或已经使用
        boolean isCanUse = userCheckCodeService.checkCode(checkCode);
        ThrowUtil.throwIf(!isCanUse, ErrorCode.PARAMS_ERROR, "校验编号不存在或已经使用");
        userCheckCodeService.useCheckCode(checkCode);
        // 2. 密码加密
        String encodedPassword = bCryptPasswordEncoder.encode(userPassword);
        // 3. 插入数据
        User user = new User();
        user.setUserName(GenerateUtil.generateUserName());
        user.setUserAccount(userAccount);
        user.setUserPassword(encodedPassword);
        user.setCheckCode(checkCode);
        this.save(user);
        return user.getId();
    }


    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 1. 校验
        ThrowUtil.throwIf(StringUtils.isAnyBlank(userAccount, userPassword), ErrorCode.PARAMS_ERROR, "参数为空");
        verifyUser(userAccount, userPassword);
        // 2. 校验账户是否存在
        User user = this.lambdaQuery().eq(User::getUserAccount, userAccount).one();
        ThrowUtil.throwIf(user == null, ErrorCode.PARAMS_ERROR, "用户不存在或密码错误");
        // 3. 校验密码是否正确
        boolean matches = bCryptPasswordEncoder.matches(userPassword, user.getUserPassword());
        ThrowUtil.throwIf(!matches, ErrorCode.PARAMS_ERROR, "用户不存在或密码错误");
        // 4. 脱敏
        User safetyUser = getSafetyUser(user);
        // 5. 记录用户的登录态
        // todo 单点登录
        request.getSession().setAttribute(UserConstant.USER_LOGIN_STATE, safetyUser);
        return safetyUser;
    }

    @Override
    public List<User> searchUsers(String userName) {
        LambdaQueryChainWrapper<User> query = this.lambdaQuery();
        query.like(StringUtils.isNoneBlank(userName), User::getUserName, userName);
        List<User> userList = query.list();
        return userList.stream().map(this::getSafetyUser).collect(Collectors.toList());
    }

    @Override
    public boolean deleteUserById(Long userId) {
        ThrowUtil.throwIf(userId == null || userId <= 0, ErrorCode.PARAMS_ERROR, "无对应用户");
        return this.removeById(userId);
    }

    @Override
    public User getCurrentUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        if (userObj instanceof User) {
            User currentUser = (User) userObj;
            Long userId = currentUser.getId();
            User user = this.getById(userId);
            return this.getSafetyUser(user);
        }
        throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR, "未登录，请先登录");
    }

    /**
     * 用户注销
     *
     * @param request 应用请求体
     */
    @Override
    public boolean userLogout(HttpServletRequest request) {
        ThrowUtil.throwIf(request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE) == null, ErrorCode.NOT_LOGIN_ERROR, "未登录，请先登录");
        // 移出登录态
        request.getSession().removeAttribute(UserConstant.USER_LOGIN_STATE);
        return true;
    }

    /**
     * 判断是否是管理员
     *
     * @param request
     * @return
     */
    @Override
    public boolean isAdmin(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        if (userObj instanceof User) {
            User user = (User) userObj;
            return isAdmin(user);
        }
        return false;
    }

    /**
     * 判断是否是管理员
     *
     * @param user
     * @return
     */
    @Override
    public boolean isAdmin(User user) {
        return !Objects.equals(user.getUserRole(), UserRoleEnum.ADMIN.getValue());
    }

    /**
     * 管理员添加用户
     *
     * @param userAddRequest
     * @return
     */
    @Override
    public long addUser(UserAddRequest userAddRequest) {
        String userAccount = userAddRequest.getUserAccount();
        // 默认密码 12345678
        String userPassword = "12345678";
        // 1. 校验
        ThrowUtil.throwIf(StringUtils.isAnyBlank(userAccount, userPassword), ErrorCode.PARAMS_ERROR, "参数为空");
        verifyUser(userAccount, userPassword);
        // todo 分布式新增用户
        // 账户不能重复
        User one = this.lambdaQuery().eq(User::getUserAccount, userAccount).one();
        ThrowUtil.throwIf(one != null, ErrorCode.PARAMS_ERROR, "用户账户已经被注册");
        // 2. 密码加密
        String encodedPassword = bCryptPasswordEncoder.encode(userPassword);
        // 3. 插入数据
        User user = new User();
        BeanUtils.copyProperties(userAddRequest, user);
        user.setUserName(GenerateUtil.generateUserName());
        user.setUserPassword(encodedPassword);
        this.save(user);
        return user.getId();
    }

    /**
     * 更新用户信息
     *
     * @param userUpdateRequest
     * @return
     */
    @Override
    public boolean updateUser(UserUpdateRequest userUpdateRequest) {
        User user = new User();
        BeanUtils.copyProperties(userUpdateRequest, user);
        boolean result = this.updateById(user);
        ThrowUtil.throwIf(!result, ErrorCode.OPERATION_ERROR, "更新失败");
        return true;
    }

    @Override
    public boolean resetPassword(Long userId) {
        User user = this.getById(userId);
        // 2. 密码加密
        String encodedPassword = bCryptPasswordEncoder.encode("12345678");
        user.setUserPassword(encodedPassword);
        boolean result = this.updateById(user);
        ThrowUtil.throwIf(!result, ErrorCode.OPERATION_ERROR, "重置密码失败");
        return true;
    }

    /**
     * 分页查询用户
     *
     * @param userQueryRequest
     * @return
     */
    @Override
    public Page<User> listUsersByPage(UserQueryRequest userQueryRequest) {
        int current = userQueryRequest.getCurrent();
        int pageSize = userQueryRequest.getPageSize();
        return this.page(new Page<>(current, pageSize), this.getQueryWrapper(userQueryRequest));
    }

    @Override
    public QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest) {
        ThrowUtil.throwIf(userQueryRequest == null, ErrorCode.PARAMS_ERROR, "请求参数为空");
        Long id = userQueryRequest.getId();
        String userName = userQueryRequest.getUserName();
        String userRole = userQueryRequest.getUserRole();
        String sortField = userQueryRequest.getSortField();
        String sortOrder = userQueryRequest.getSortOrder();

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(id != null, "id", id);
        queryWrapper.eq(StringUtils.isNotBlank(userRole), "user_role", userRole);
        queryWrapper.like(StringUtils.isNotBlank(userName), "user_name", userName);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), Objects.equals(CommonConstant.SORT_ORDER_ASC, sortOrder), sortField);
        return queryWrapper;
    }

    /**
     * 更新个人信息
     *
     * @param userUpdateRequest
     * @param request
     * @return
     */
    @Override
    public boolean updateMyInfo(UserUpdateRequest userUpdateRequest, HttpServletRequest request) {
        User currentUser = this.getCurrentUser(request);
        User user = new User();
        BeanUtils.copyProperties(userUpdateRequest, user);
        user.setId(currentUser.getId());
        boolean result = this.updateById(user);
        ThrowUtil.throwIf(!result, ErrorCode.OPERATION_ERROR, "更新个人信息失败");
        return true;
    }


    /**
     * 账户校验是否包含特殊字符
     *
     * @param userAccount 用户账户
     * @return true:包含特殊字符,false:不包含特殊字符
     */
    private static boolean isValidUserAccount(String userAccount) {
        Pattern pattern = Pattern.compile(SPECIAL_CHARACTERS_PATTERN);
        Matcher matcher = pattern.matcher(userAccount);
        // 如果找到特殊字符，!matcher.find() 返回 true，表示字符串不合法
        return !matcher.find();
    }

    /**
     * 数据脱敏
     *
     * @param originUser 原始用户数据
     * @return 脱敏后的用户数据
     */
    private User getSafetyUser(User originUser) {
        // 校验用户是否合法
        ThrowUtil.throwIf(originUser == null, ErrorCode.SYSTEM_ERROR, "用户数据丢失");
        User safetyUser = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setUserName(originUser.getUserName());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setPhone(originUser.getPhone());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setUserRole(originUser.getUserRole());
        safetyUser.setCheckCode(originUser.getCheckCode());
        safetyUser.setUserStatus(originUser.getUserStatus());
        safetyUser.setCreateTime(originUser.getCreateTime());
        return safetyUser;
    }
}




