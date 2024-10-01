package com.ayang.usercenter.model.dto;

import com.ayang.usercenter.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author 阿洋努力学习
 * @description 用户查询请求体
 * @date 2024-09-17
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryRequest extends PageRequest implements Serializable {

    private static final long serialVersionUID = -4537875225772403321L;
    /**
     * 主键
     */
    private Long id;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 角色（user/admin/ban）
     */
    private String userRole;
}
