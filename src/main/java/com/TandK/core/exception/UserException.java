package com.TandK.core.exception;

public class UserException extends RuntimeException{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * exception message
     */
    private String message;

    /**
     * error code
     * default: 401
     */
    private int code = 401;

    public UserException(BizCodeEnum bizCodeEnum) {
        super(bizCodeEnum.getMsg());
        this.message = bizCodeEnum.getMsg();
        this.code = bizCodeEnum.getCode();
    }

    public UserException(String message) {
        super(message);
        this.message = message;
    }


    public UserException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }


    @Override
    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

}
