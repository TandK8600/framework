package com.TandK.core.exception;

import lombok.Getter;

/**
 * BizCodeEnum <br/>
 * 错误码和错误信息定义类
 *
 * @author ZGH.MercyModest
 * @version V1.0.0
 * @create 2021/3/31
 */
@Getter
public enum BizCodeEnum {

    /**
     * 公用部分
     */
    SUCCESS(0, "success"),
    UNAUTHORIZED(401, "鉴权失败"),
    VALIDATION_EXCEPTION(400, "参数校验不通过"),
    URL_NOT_FOUND(404, "路由不存在"),
    SYSTEM_ERROR(500, "系统内部异常"),
    NO_EXPORT_DATA(600, "没有可导出的数据"),
    REMOTE_PROCEDURE_CALL_ERROR(601, "远程调用异常"),
    DATASOURCE_OPERATOR_FAIR(602, "数据库操作失败"),
    NO_OPERATION_PERMISSION(603, "抱歉亲,您暂时没有权限执行此操作哦"),

    /**
     * 用户异常从10000开始
     */
    ADD_USER_ERROR(10001, "添加用户异常"),
    EMPTY_USER_LIST(10002, "用户列表为空"),
    ;

    /**
     * code
     */
    private final int code;
    /**
     * msg
     */
    private final String msg;

    BizCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
