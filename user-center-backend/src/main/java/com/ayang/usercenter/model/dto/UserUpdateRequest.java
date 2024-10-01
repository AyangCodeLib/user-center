package com.ayang.usercenter.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 阿洋努力学习
 * @description 用户更新请求体
 * @date 2024-09-17
 **/
@Data
public class UserUpdateRequest implements Serializable {

    private static final long serialVersionUID = 6507848240234355547L;
    /**
     * 主键
     */
    private Long id;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 头像url
     */
    private String avatarUrl;

    /**
     * 角色（user/admin/ban）
     */
    private String userRole;
}
