package com.TandK.core.exception;

import lombok.Getter;

/**
 * @author TandK
 * @since 2021/8/14 17:13
 */
@Getter
public enum BusinessExceptionEumn {

    THIRD_NET_ERROR(10001, "第三方服务网络异常", "第三方服务网络异常"),
    UPDATE_ERROR(10002, "更新异常", "更新异常"),
    ADD_ERROR(10003, "插入异常", "插入异常"),
    RESULT_NOT_FOUND(10004, "查询结果不存在", "查询结果不存在"),
    SENSITIVE_MESSAGE_CHECK_NOT_PASS(10005, "存在敏感词", "存在敏感词"),
    USER_NAME_EMPTY(10005, "用户名不能为空", "用户名不能为空"),
    UNAUTHORIZED(401, "鉴权失败", "鉴权失败");

    /**
     * code
     */
    private final int code;
    /**
     * msg
     */
    private final String msg;

    private final String reason;

    BusinessExceptionEumn(int code, String msg, String reason) {
        this.code = code;
        this.msg = msg;
        this.reason = reason;
    }

}
