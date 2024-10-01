package com.ayang.usercenter.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 阿洋努力学习
 * @description 用户新增请求体
 * @date 2024-09-17
 **/
@Data
public class UserAddRequest implements Serializable {

    private static final long serialVersionUID = 1244969995676166422L;
    /**
     * 用户账户
     */
    private String userAccount;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 头像url
     */
    private String avatarUrl;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;
}
