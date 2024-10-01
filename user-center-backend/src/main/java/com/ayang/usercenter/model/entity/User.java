package com.ayang.usercenter.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 *
 * @TableName user
 */
@TableName(value = "user")
@Data
public class User implements Serializable {
	/**
	 * 主键
	 */
	@TableId(type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 * 用户名称
	 */
	@TableField(value = "user_name")
	private String userName;

	/**
	 * 账户
	 */
	@TableField(value = "user_account")
	private String userAccount;

	/**
	 * 头像url
	 */
	@TableField(value = "avatar_url")
	private String avatarUrl;

	/**
	 * 性别
	 */
	@TableField(value = "gender")
	private Integer gender;

	/**
	 * 密码
	 */
	@TableField(value = "user_password")
	private String userPassword;

	/**
	 * 电话
	 */
	@TableField(value = "phone")
	private String phone;

	/**
	 * 邮箱
	 */
	@TableField(value = "email")
	private String email;

	/**
	 * 用户状态(0:正常...)
	 */
	@TableField(value = "user_status")
	private Integer userStatus;

	/**
	 * 角色（user/admin/ban）
	 */
	@TableField(value = "user_role")
	private String userRole;

	/**
	 * 校验编码
	 */
	@TableField(value = "check_code")
	private String checkCode;

	/**
	 * 创建时间
	 */
	@TableField(value = "create_time")
	private Date createTime;

	/**
	 * 更新时间
	 */
	@TableField(value = "update_time")
	private Date updateTime;

	/**
	 * 逻辑删除(0：存在，1：删除）
	 */
	@TableField(value = "is_delete")
	private Integer isDelete;

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;
}