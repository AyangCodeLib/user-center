package com.ayang.usercenter.service;

import com.ayang.usercenter.model.entity.UserCheckCode;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author 阿洋努力学习
 * @description 针对表【user_check_code】的数据库操作Service
 * @createDate 2024-09-21 18:08:13
 */
public interface UserCheckCodeService extends IService<UserCheckCode> {

	/**
	 * 创建校验编码
	 *
	 * @return 校验编码
	 */
	String createCheckCode();

	/**
	 * 校验校验编号
	 *
	 * @param checkCode 校验编号
	 * @return 是否可用
	 */
	boolean checkCode(String checkCode);

	/**
	 * 使用校验编号
	 *
	 * @param checkCode 校验编号
	 */
	void useCheckCode(String checkCode);
}
