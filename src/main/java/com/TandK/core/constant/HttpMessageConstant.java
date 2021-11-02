package com.TandK.core.constant;

/**
 * @author TandK
 */
public final class HttpMessageConstant {

    private HttpMessageConstant() {
    }

    public static final String HTTP_MESSAGE_FAIL = "非法请求";
    public static final String HTTP_MESSAGE_BAG_REQUEST = "请求体不完整";
    public static final String HTTP_MESSAGE_NOT_FOUND = "资源不存在";
    public static final String HTTP_MESSAGE_FORBIDDEN = "请勿重复请求";
    public static final String HTTP_MESSAGE_CONFLICT = "请求资源冲突";
    public static final String HTTP_MESSAGE_PRECONDITION_FAILED = "请求头有误";
    public static final String HTTP_MESSAGE_UNAUTHORIZED = "鉴权失败";
    public static final String HTTP_MESSAGE_INTERNAL_SERVER_ERROR = "服务器内部错误";
    public static final String HTTP_MESSAGE_BAG_REQUEST_EXCEL = "excel error";
    public static final String HTTP_DATE_BAG_REQUEST = "时间格式异常";

}
