package com.TandK.core.exception;

public class BusinessException extends RuntimeException implements CustomException{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private static final int DEFCODE = 8600;
    /**
     * exception message
     */
    private String message;

    /**
     * exception reason
     */
    private String reason;

    /**
     * error code
     * default: 500
     */
    private int code = 8600;

    public BusinessException(BusinessExceptionEumn bizCodeEnum) {
        super(bizCodeEnum.getMsg());
        this.message = bizCodeEnum.getMsg();
        this.reason = bizCodeEnum.getReason();
        this.code = bizCodeEnum.getCode();
    }

    public BusinessException(String message) {
        super(message);
        this.code = DEFCODE;
        this.message = message;
        this.reason = message;
    }


    public BusinessException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
        this.reason = message;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.code = DEFCODE;
        this.message = message;
        this.reason = message;
    }


    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getReason() {
        return reason;
    }

    @Override
    public int getCode() {
        return code;
    }

}
