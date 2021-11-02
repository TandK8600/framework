
package com.TandK.core.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 自定义异常
 *
 * @author ivancai
 */
@Getter
@Setter
public class RRException extends RuntimeException {

    private static final long serialVersionUID = -3394743697747774249L;
    /**
     * exception message
     */
    private String msg;
    /**
     * error code
     * default: 500
     */
    private int code = 500;

    /**
     * 有时候我们我可能有需要抛出异常的同时响应数据给前端,此字段将用于承载异常响应数据
     */
    private Object data;

    public RRException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public RRException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public RRException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public RRException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public RRException(BizCodeEnum bizCodeEnum) {
        super(bizCodeEnum.getMsg());
        this.msg = bizCodeEnum.getMsg();
        this.code = bizCodeEnum.getCode();
    }

    public RRException(String msg, int code, Object data) {
        super(msg);
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    public RRException(BizCodeEnum bizCodeEnum, Object data) {
        super(bizCodeEnum.getMsg());
        this.msg = bizCodeEnum.getMsg();
        this.code = bizCodeEnum.getCode();
        this.data = data;
    }

}
