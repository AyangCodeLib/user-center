package com.ayang.usercenter.controller;

import com.ayang.usercenter.annotaion.AuthCheck;
import com.ayang.usercenter.common.BaseResponse;
import com.ayang.usercenter.common.ResultUtils;
import com.ayang.usercenter.constant.UserConstant;
import com.ayang.usercenter.service.UserCheckCodeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 阿洋努力学习
 * @description 用户邀请码接口
 * @date 2024-09-17
 **/
@RestController
@RequestMapping("/userCheckCode")
public class UserCheckCodeController {

    @Resource
    private UserCheckCodeService userCheckCodeService;

    /**
     * 用户注册
     *
     * @return 邀请码
     */
    @PostMapping("/register")
//    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse userRegister() {
        String checkCode = userCheckCodeService.createCheckCode();
        return ResultUtils.success(checkCode);
    }
}
