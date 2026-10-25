package com.al.ai.platform.core.common.result;

import lombok.Getter;

@Getter
public enum ResultCode {
    SUCCESS(200, "操作成功"),
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未授权，请先登录"),
    FORBIDDEN(403, "拒绝访问"),
    NOT_FOUND(404, "请求资源不存在"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    BUSINESS_ERROR(1001, "业务异常"),
    DATA_NOT_FOUND(1002, "数据不存在"),
    DATA_ALREADY_EXISTS(1003, "数据已存在"),
    PARAMS_VALIDATION_ERROR(1004, "参数校验失败"),
    USER_NOT_LOGIN(2001, "用户未登录"),
    USER_ACCOUNT_DISABLED(2002, "用户账号已被禁用"),
    USER_PASSWORD_ERROR(2003, "用户名或密码错误"),
    USER_TOKEN_EXPIRED(2004, "Token 已过期"),
    USER_TOKEN_INVALID(2005, "Token 无效");

    private final Integer code;
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
