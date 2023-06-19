package com.atcpl.crowd.constant;

/**
 * 定义常量类,用来放整个工程的一些提示
 *
 * @author 蜡笔小新
 */
public class CrowdConstant {
    /**
     * 消息变量
     */
    public static final String MESSAGE_LOGIN_FAILED = "账号或密码有误，请重新输入";
    public static final String MESSAGE_LOGIN_ACCT_NOT_EXISTS = "抱歉！账号不存在";
    public static final String MESSAGE_LOGIN_ACC_ALREADY_IN_USE = "账号已被使用";
    public static final String MESSAGE_LOGIN_ACCT_ALREADY_IN_USE = "抱歉！账号已被使用";
    public static final String MESSAGE_ACCESS_FORBIDDEN = "请登录以后在访问";
    public static final String MESSAGE_STRING_INVALIDATE = "字符串不合法，请不要传入空字符串";
    public static final String MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE = "系统错误：登录账号不唯一";
    public static final String MESSAGE_ACCESS_DENIED = "权限不足，请求被拒绝";
    public static final String MESSAGE_CODE_NOT_EXISTS = "验证码已过期！请检查手机号是否正确或选择重新发送";
    public static final String MESSAGE_CODE_INVALID = "验证码不正确";
    public static final String MESSAGE_HEADER_PICTURE_UPLOAD_FAILED = "头图上传失败";
    public static final String MESSAGE_HEADER_PICTURE_IS_REQUIRE = "头图不可为空";
    public static final String MESSAGE_DETAIL_PICTURE_IS_REQUIRE = "详情图片不可为空";
    public static final String MESSAGE_DETAIL_PICTURE_UPLOAD_FAILED = "详情图片上传失败";
    public static final String MESSAGE_INTERIM_PROJECT_MISSING = "临时存储的PROJECT对象丢失";



    /**
     * 属性
     */
    public static final String ATTR_NAME_LOGIN_ADMIN = "admin";
    public static final String ATTR_NAME_EXCEPTION = "exception";
    public static final String ATTR_NAME_PAGEINFO = "pageInfo";
    public static final String ATTR_NAME_MESSAGE = "message";
    public static final String ATTR_NAME_LOGIN_MEMBER = "member";
    public static final String ALL_MENU = "allMenu";
    public static final String AUTH_INFO = "auth_info";

    /**
     * 临时项目
     */
    public static final String ATTR_NAME_INTERIM_PROJECT = "interimProject";
    public static final String ATTR_NAME_PORTAL_DATA = "portal_data";


    public static final String REDIS_CODE_PREFIX = "REDIS_CODE_PREFIX";

}
