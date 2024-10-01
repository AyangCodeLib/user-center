package com.ayang.usercenter.utils;

import java.util.Random;

/**
 * 生成默认值工具
 *
 * @author <a href="https://github.com/AyangCodeLib">阿洋努力学习</a>
 */
public class GenerateUtil {

    private static final Random RANDOM = new Random();

    public static String generateUserName() {
        StringBuilder userName = new StringBuilder();
        userName.append("用户");
        for (int i = 0; i < 4; i++) {
            userName.append(RANDOM.nextInt(10));
        }
        return userName.toString();
    }
}
