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
    SUCCESS(0, "success"), TOKEN_INVALID(401, "token无效或过期"), VALIDATION_EXCEPTION(400, "参数校验不通过"),
    URL_NOT_FOUND(404, "路由不存在"), SYSTEM_ERROR(500, "系统内部异常"), NO_EXPORT_DATA(600, "没有可导出的数据"),
    REMOTE_PROCEDURE_CALL_ERROR(601, "远程调用异常"), DATASOURCE_OPERATOR_FAIR(602, "数据库操作失败"),
    NO_OPERATION_PERMISSION(603, "抱歉亲,您暂时没有权限执行此操作哦"),

    /**
     * 权限管理服务10000开始
     */
    PARENT_DEPARTMENT_NOT_EXIST(10001, "上级部门不存在或已删除"), MEMBER_NOT_EXIST(10002, "成员不存在或已删除"),

    DIRECTOR_OUTNUMBER(10003, "主管人数超过限定值"), DEPARTMENT_NOT_EXIST(10004, "部门不存在或已删除"),

    SUB_DEPARTMENT_EXIST(10005, "此部门存在子部门，不可删除"), DEPARTMENT_MEMBER_EXIST(10006, "此部门存在人员，不可删除"),

    ACCOUNT_CAN_NOT_BINDING(10007, "此账号不可绑定"), MEMBER_NOT_DISABLE(10008, "操作失败：含有未停用成员"),

    CITY_ALREADY_EXIST(10009, "操作失败：城市已存在，不可新增"), AFTER_APPLY_TIME_AREA(10010, "操作失败：开放申请后不可删除区域"),

    ROLE_DELETED(10011, "角色已经被删除"), ACCOUNT_NOT_EXISTS(10012, "账号不可用"),

    DEPARTMENT_NAME_ALREADY_EXIST(10013, "部门名称已存在"),

    MEMBER_PHONE_ALREADY_EXIST(10014, "手机号已存在"),

    WAREHOUSE_AREA_EXCEPTION(10015, "选中的区域有未开放"), AFTER_APPLY_TIME_APPLY_TIME(10016, "操作失败：开放申请后不可修改开放申请时间"),
    AFTER_ORDER_TIME_ORDER_TIME(10017, "操作失败：开放下单后不可修改开放下单时间"),
    APPLY_TIME_AFTER_ORDER_TIME(10018, "操作失败：开放申请时间需在开放下单时间之前"),
    SYNC_ALL_DM_FROM_FEISHU_ERROR(10019,"调用第三方服务全量同步飞书部门成员接口异常:"),
    /**
     * 商品服务管理模块11000开始
     */
    CATEGORY_MISSING(11001, "类目不存在"),

    CATEGORY_GROSS_MARGIN_EXIST(11002, "毛利最大值必须大于毛利最小值"),

    CATEGORY_ID_ALREADY_EXIST(11003, "亲,当前类目编号已经存在了哦~"),

    CATEGORY_PARENT_NOT_EXIST(11004, "亲,请选择上级类目~"),

    CATEGORY_DISABLE_EXIST(11005, "类目存在上架商品，无法禁用"),

    CATEGORY_DELETE_EXIST(11006, "类目存在上架商品，无法删除"),

    CATEGORY_PARENT_EXIST(11007, "上级类目不存在，请重新选择"),

    BRAND_NOT_EXIST(11008, "品牌不存在"),

    CATEGORY_NAME_REPEAT_EXIST(11009, "类目名称重复"),

    PRODUCT_CATEGORY_NAME_REPEAT_EXIST(11010, "营销分类名称重复"),

    MARKET_CATEGORY_DISABLE_EXIST(11011, "营销分类存在上架商品，无法禁用"),

    MARKET_CATEGORY_DELETE_EXIST(11012, "营销分类存在上架商品，无法删除"),

    MARKET_CATEGORY_NOT_EXIST(11012, "营销分类不存在"),

    ATTRIBUTES_CATEGORY_EXIST(11101, "属性所属类目不能为空"),

    ATTRIBUTES_OPTIONS_EXIST(11102, "显示类型为单选/多选时,选项不能为空"),

    ATTRIBUTES_NOT_EXIST(11103, "属性不存在"),

    ATTRIBUTES_HAVE_PRODUCT_DELETE_EXIST(11104, "存在关联商品无法删除"),

    ATTRIBUTES_HAVE_PRODUCT_DISABLE_EXIST(11105, "存在关联商品无法禁用"),

    PRODUCT_ATTRIBUTES_UUID_NOT_EXIST(11201, "商品属性模版ID不能为空"),

    PRODUCT_MISSING(11202, "商品不存在"),

    PRODUCT_UPDATE_MARKET_NAME_NOT_EXIST(11203, "商品名称不能为空"),

    PRODUCT_UPDATE_MARKET_SUB_TITLE_NOT_EXIST(11204, "副标题不能为空"),

    PRODUCT_UPDATE_MARKET_MEMBER_NOT_EXIST(11205, "产品经理不能为空"),

    PRODUCT_UPDATE_MARKET_BG_SRC_NOT_EXIST(11206, "背景图片不能为空"),

    PRODUCT_UPDATE_MARKET_BANNER_NOT_EXIST(11207, "商品轮播图不能为空"),

    PRODUCT_UPDATE_MARKET_SKU_NOT_EXIST(11208, "SKU不能为空"),

    PRODUCT_UPDATE_MARKET_SKU_PRICE_NOT_EXIST(11209, "SKU售价不能为空"),

    PRODUCT_UPDATE_MARKET_SKU_UUID_NOT_EXIST(11210, "SKU_UUID不能为空"),

    PRODUCT_SHELVES_CYCLE_NOT_MISSING(11212, "商品存在生效或待生效上架信息，不可编辑上架类型"),


    PRODUCT_LOG_CATEGOTY_TYPE_NOT_EXIST(11301, "变更记录类型不存在"),


    PRODUCT_UP_CYCLE_BEFORE_NOT_EXIST(11401, "下架时间不能小于上架时间"),

    PRODUCT_UP_CYCLE_REPEAT_NOT_EXIST(11402, "周期之间不能重叠"),

    PRODUCT_UP_TIME_CURRENT_NOW_EXIST(11403, "上架时间不能晚于当前时间"),

    PRODUCT_UP_TIME_CURRENT_NOT_EXIST(11404, "上架时间区间不能为空"),

    PRODUCT_UP_TIME_CYCLE_NOT_EXIST(11406, "商品不存在预上架周期，无法调整上架"),

    PRODUCT_SHELVES_TYPE_NOT_EXIST(11405, "上架信息不存在"),

    /**
     * 订单服务12000开始
     */

    /**
     * 营销服务13000开始
     */

    /**
     * 仓库服务14000开始
     */

    /**
     * 采购服务15000开始
     */
    SUPPLIER_DATA_NOT_EXISTS(15001, "数据不存在"),

    APPLY_STATUS_FAIL(15101, "当前申请状态不可编辑"),

    APPLY_MEMBER_NAME_NOT_EXISTS(15102, "产品经理不可为空"),

    APPLY_MEMBER_UUID_NOT_EXISTS(15103, "产品经理UUID不可为空"),

    SUPPLIER_NAME_NOT_EXISTS(15201, "供应商名称不能为空"),

    SUPPLIER_NAME_EXISTS(15299, "供应商名称已存在"),

    SUPPLIER_COMANY_TYPE_NOT_EXISTS(15202, "公司类型不能为空"),

    SUPPLIER_TYPE_NOT_EXISTS(15203, "供应商类型不能为空"),

    SUPPLIER_PRODUCTION_QUALIFICATION_SRC_NOT_EXISTS(15204, "生产资质不能为空"),

    SUPPLIER_DAYS_PAYABLE_OUTSTANDING_NOT_EXISTS(15205, "账期不能为空"),

    SUPPLIER_PROVINCE_CITY_AREA_NOT_EXISTS(15206, "省市区不能为空"),

    SUPPLIER_ADDRESS_NOT_EXISTS(15207, "详细地址不能为空"),

    SUPPLIER_MEMBER_NAME_NOT_EXISTS(15208, "产品经理不能为空"),

    SUPPLIER_CONTACTS_NOT_EXISTS(15209, "联系人不能为空"),

    SUPPLIER_CONTACTS_NAME_NOT_EXISTS(15210, "联系人名称不能为空"),

    SUPPLIER_CONTACTS_PHONE_NOT_EXISTS(15211, "联系人手机号不能为空"),

    SUPPLIER_BUSINESS_LICENSE_SRC_NOT_EXISTS(15212, "营业执照不能为空"),

    SUPPLIER_ID_CARD_SRC_NOT_EXISTS(15213, "身份证不能为空"),

    SUPPLIER_FINANCIAL_NOT_EXISTS(15214, "财务信息不能为空"),

    SUPPLIER_FINANCIAL_BANK_NOT_EXISTS(15215, "财务信息-开户名称不能为空"),

    SUPPLIER_FINANCIAL_BANK_ACCOUNT_NOT_EXISTS(15216, "财务信息-银行帐号不能为空"),

    SUPPLIER_FINANCIAL_OPENING_PERMIT_NOT_EXISTS(15217, "财务信息-开户许可证不能为空"),

    SUPPLIER_CONTACT_NOT_EXISTS(15218, "合同信息不能为空"),

    SUPPLIER_CONTACT_SIGNED_TIME_NOT_EXISTS(15219, "合同信息-签订时间不能为空"),

    SUPPLIER_CONTACT_END_TIME_NOT_EXISTS(15220, "合同信息-截止时间不能为空"),

    SUPPLIER_CONTACT_FILE_NOT_EXISTS(15221, "合同信息-合同文件不能为空"),

    SUPPLIER_STAGING_EDIT_TYPE_ERROR(15222, "非暂存状态供应商无法删除"),

    SUPPLIER_TRADEMARK_SRC_NOT_EXISTS(15223, "商标文件不能为空"),

    SUPPLIER_DEFAULT_REPEAT_NOT_EXISTS(15224, "默认联系人只能存在一个"),

    PLAN_SKU_HAVE_NOT_EXISTS(15301, "以下商品在仓库群组的生效周期内，已经创建过采购计划，请勿重复提交。"),

    PLAN_NOT_EXISTS(15302, "采购计划不存在"),

    PLAN_EFFECTIVE_END_TIME_EXISTS(15303, "采购计划生效时间已过，无法移除"),

    PLAN_SKU_NOT_EXISTS(15304, "采购计划sku不存在"),

    PLAN_SKU_BY_PURCHASE_ORDER_EXISTS(15305, "sku已关联采购单,无法操作"),

    PURCHASE_ORDER_BY_PLAN_SKU_NOT_EXISTS(15306, "sku数据异常,无法操作"),

    ORDER_NOT_EXISTS(15401, "采购单不存在"),

    ORDER_CONVERSION_EXISTS(15402, "采购单已存在转可售记录"),

    ORDER_CONVERSION_NOT_EXISTS(15404, "采购单不存在转可售记录"),

    ORDER_CONVERSION_EFFECTIVE_BEGIN_TIME(15403, "生效时间不可小于当前时间"),

    ORDER_CONVERSION_EFFECTIVE_BEGIN_TIME_ERROR(15405, "生效时间已生效，不可编辑"),

    PAYMENT_ADD_EXISTS(15501, "存在已付款申请的采购单"),

    /**
     * 财务服务16000开始
     */

    /**
     * 用户服务17000开始
     */
    USER_DATA_NOT_EXISTS(17001, "数据不存在"), USER_PERMISSION_WAREHOUSE_SYSTEM_ERROR(17002, "系统内部异常:权限系统获取仓库信息异常"),
    USER_LEAVE_TIME_OVERLAPS(17003, "操作失败：在{}至{}内已经提交过请假"), USER_LEAVE_END(17004, "操作失败：请假时间已结束，不可取消"),
    USER_PHONE_ALREADY_EXIST(17005, "操作失败：手机号已存在"), USER_START_TIME_AFTER_END_TIME(17006, "操作失败：开始时间需早于结束时间"),
    USER_START_TIME_EXCEPTION(17007, "操作失败：开始时间需为星期一或星期四的21:00"),
    USER_END_TIME_EXCEPTION(17008, "操作失败：结束时间需为星期一或星期四的21:00"),
    USER_UPDATE_CLASS_AFTER_END_TIME(17009, "操作失败：班级已结束,不可修改"),
    USER_UPDATE_ACTIVITY_ENROLL_END_TIME_AFTER(17010, "操作失败：报名结束时间已过,不可修改"),
    USER_UPDATE_ACTIVITY_START_TIME_AFTER(17011, "操作失败：活动开始时间已过,不可修改"),
    USER_UPDATE_ACTIVITY_END_TIME_AFTER(17012, "操作失败：活动结束时间已过,不可修改"),
    USER_COMMUNITY_ALREADY_EXISTING_BIG_PARTNER(17013, "操作失败：所在社区已有大团长"),
    USER_NOT_DOWNGRADE_PARTNER(17014, "操作失败：团长不是降级的"), USER_PARTNER_NOT_SMALL(17015, "操作失败：此用户不是小团长"),
    USER_EXPORT_DATA_NOT_EXISTS(17016, "数据未导出：筛选出的数据为空"), MEMBER_PHONE_NOT_EXISTS(17017, "员工手机号不存在"),
    MEMBER_NOT_BINDING_ACCOUNT(17018, "您的账号还没有录入系统，请联系对应工作人员进行添加"),
    MEMBER_ACCOUNT_DISABLED(17019, "您的账号被禁用，请联系对应工作人员进行处理"), WECHAT_NOT_BING_PHONE(17020, "未绑定手机号，请输入手机号进行绑定"),
    ILLEGAL_UNIONID_OR_OPENID(17021, "非法 unionid 或 openid"),
    WECHAT_AUTHENTICATION_INVALID(17022, "亲,您的微信授权失效了哦~请重新扫码登陆哦~"), ILLEGAL_TEMP_TICKET(17023, "非法临时票据"),
    NOT_ANY_PERMISSION(17024, "抱歉~亲,您目前没有任何访问权限哦.如有疑问可联系系统管理员."), USER_PARTNER_NOT_EXISTS(17025, "团长不存在或已删除"),
    USER_CREATE_QR_CODE_ERROR(17026, "系统内部异常：生成签到二维码异常异常"),PHONE_HAD_BIND_OTHER_WECHAT(17027,"亲,当前手机已经绑定了微信,无法再次绑定哦~"),

    /**
     * 第三方服务18000开始
     */
    SEND_SMS_FAIR(18001, "发送短信失败"), CAPTCHA_VERIFICATION_FAIR(18002, "验证码验证失败"),
    CAPTCHA_VERIFICATION_INVALID(18003, "验证码已过期"), WECHAT_USER_CANCEL_AUTHENTICATION(18003, "微信用户取消授权"),
    WECHAT_CODE_TO_ACCESS_TOKEN_FAIR(18004, "微信授权:code 换取 accessToken 失败"),
    WECHAT_GET_USER_INFO_FAIR(18005, "获取用户微信信息失败"), REFRESH_TOKEN_FAIR(18006, "微信刷新或续期access_token失败"),
    FEISHU_REFRESH_TOKEN_FAIR(18007, "飞书刷新或续期access_token失败"),
    GET_FEISHU_TOKEN_FROM_REDIS_FAIR(18008, "从redis获取飞书access_token失败"),
    FEISHU_ERROR(18009,"飞书错误: "),
    HTTP_REQUEST_FEISHU_ERROR(18010,"发送请求到飞书错误: "),

    /**
     * 内容服务19000开始
     */
    CMS_DATA_NOT_EXISTS(19001, "数据已删除或不存在"), CMS_UPDATE_MESSAGE_RELEASE_TIME_AFTER(19002, "消息已发布，不可修改发布时间"),
    CMS_PARTNER_FAQ_NOT_EXISTS(19003, "操作失败：100问ID不存在或已删除"), CMS_EXPORT_DATA_NOT_EXISTS(1904, "数据未导出：筛选出的数据为空"),
    CMS_FOLLOW_MEMBER_NOT_EXISTS(1905, "操作失败：产品经理为空"),

    /**
     * 售后服务 20000
     */
    AFTER_SALE_ORDER_REPETITION_AUDIT(20001, "亲,售后订单已经完成审核~无法进行再次审核"),NO_CORRECT_DATA_TO_IMPORT(20002,"没有可导入的正确数据,请核对后重试.")
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
