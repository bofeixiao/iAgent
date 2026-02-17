package com.idataai.common.core.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一响应结果封装
 *
 * @param <T> 数据类型
 * @author iDataAI
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 提示消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 时间戳
     */
    private Long timestamp;

    public Result() {
        this.timestamp = System.currentTimeMillis();
    }

    public Result(Integer code, String message) {
        this();
        this.code = code;
        this.message = message;
    }

    public Result(Integer code, String message, T data) {
        this();
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功响应
     */
    public static <T> Result<T> success() {
        return new Result<>(200, "success");
    }

    /**
     * 成功响应(带数据)
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "success", data);
    }

    /**
     * 成功响应(自定义消息)
     */
    public static <T> Result<T> success(String message) {
        return new Result<>(200, message);
    }

    /**
     * 成功响应(自定义消息)
     */
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(200, message, data);
    }

    /**
     * 失败响应
     */
    public static <T> Result<T> error() {
        return new Result<>(500, "error");
    }

    /**
     * 失败响应(自定义消息)
     */
    public static <T> Result<T> error(String message) {
        return new Result<>(500, message);
    }

    /**
     * 失败响应(自定义状态码和消息)
     */
    public static <T> Result<T> error(Integer code, String message) {
        return new Result<>(code, message);
    }

    /**
     * 自定义响应
     */
    public static <T> Result<T> build(Integer code, String message, T data) {
        return new Result<>(code, message, data);
    }

    /**
     * 判断是否成功
     */
    public boolean isSuccess() {
        return this.code != null && this.code == 200;
    }
}
