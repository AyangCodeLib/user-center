package com.ayang.usercenter.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 阿洋努力学习
 * @description 基本通用返回
 * @date 2024-09-22
 **/
@Data
public class BaseResponse implements Serializable {

    /**
     * 返回编码
     */
    private int code;

    /**
     * 返回数据
     */
    private Object data;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 描述
     */
    private String description;

    private static final long serialVersionUID = 8817244408506576015L;

    public BaseResponse(int code, Object data, String message, String description) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = description;
    }

    public BaseResponse(int code, Object data, String message) {
        this(code, data, message, "");
    }

    public BaseResponse(int code, Object data) {
        this(code, data, "", "");
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage());
    }

    /**
     * 成功
     *
     * @param data
     * @return
     */
    public static BaseResponse success(Object data) {
        return new BaseResponse(0, data, "ok");
    }

    /**
     * 成功
     *
     * @return
     */
    public static BaseResponse success() {
        return new BaseResponse(0, null, "ok");
    }

    /**
     * 失败
     *
     * @param errorCode
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode) {
        return new BaseResponse(errorCode);
    }

    /**
     * 失败
     *
     * @param code
     * @param message
     * @return
     */
    public static BaseResponse error(int code, String message) {
        return new BaseResponse(code, null, message);
    }

    /**
     * 失败
     *
     * @param errorCode
     * @param message
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode, String message) {
        return new BaseResponse(errorCode.getCode(), null, message);
    }
}
