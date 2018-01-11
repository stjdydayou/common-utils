package com.a7space.commons.utils;

public class Constant {
    public static String SYSTEM = "sys";

    public static String CRYPTOBASE64UTIL_DEFAULT_KEY = "pagu_lide";//base加密解密默认密钥

    public static int ENABLE_FLAG_ENABLE = 1;//已启用
    public static int ENABLE_FLAG_DISABLE = 0;//已禁用

    public static int DELETE_FLAG_DELETED = 1;//已删除
    public static int DELETE_FLAG_NOTDELETED = 0;//未删除

    //======================错误参数================================
    public static String ERROR_INVALID_TOKEN = "E0001";
    public static String ERROR_MEMBER_NOT_BINDED_WXOPENID = "E0002";
    public static String ERROR_WXOPENID_NOT_EXIST = "E0003";

    //======================以下为operate service常量参数================================
    public static int REDIS_DB_TOKEN = 1;
    public static int REDIS_DB_TOKEN_TTL = 60 * 2;//秒

    //======================以下为manager平台常量参数================================
    public static int REDIS_DB_MANAGER_SHIRO = 0;
    public static String REDIS_KEY_SHIRO_CACHE_KEY_PREX = "shiro_authorization_cache:";
    public static String REDIS_KEY_SHIRO_SESSION = "shiro-session:";
    //这里有个小BUG，因为Redis使用序列化后，Key反序列化回来发现前面有一段乱码，解决的办法是存储缓存不序列化
    public static String REDIS_KEY_SHIRO_ALL = "*shiro-session:*";
    public static int SESSION_VAL_TIME_SPAN = 1800;

    //======================以下为portal平台常量参数================================
    public static String PORTAL_COOKIE_OPENID = "weixin_user_openid";//微信浏览器中cookies中已经登录的userid
    public static long PORTAL_COOKIE_USERID_EXPIRE = 2592000;//微信浏览器中cookies中userid保存有效期,30天
    public static String PORTAL_COOKIE_VISITORID = "visitor_id";//所有用户第一次访问时会存放一个惟一标识，不存在时会再创建。

    public static int REDIS_DB_PORTAL = 1;
    public static int REDIS_TTL_DEFAULT = 60 * 60 * 12;
    public static String REDIS_KEY_ACCESS_LIMIT_PAGE_LOCKS = "access_limit_page_locks";//访问频率限制页面锁
    public static String REDIS_KEY_ACCESS_LIMIT_PAGE_COUNTS = "access_limit_page_counts";//访问频率限制页面访问次数

    public static String REDIS_KEY_WEIXIN_ACCESS_TOKEN = "weixin_accesstoken";//微信授权调用凭据
    public static String REDIS_KEY_WEIXIN_WEB_ACCESS_TOKEN = "weixin_web_access_token";//微信网页授权调用凭据
    public static String REDIS_KEY_WEIXIN_JSAPI_TICKET = "weixin_jsapi_ticket";
    public static String REDIS_TABLE_KEY_USERID2OPENID = "weixin_hash_userid2openidappid";

    public static String REDIS_TABLE_KEY_CUSTOMER_BASE_INFO = "table_customer_base_info";//用户表
    public static String REDIS_TABLE_KEY_CUSTOMER_WEIXIN_INFO = "table_customer_weixin_info";//用户表
    public static String ERROR_VIEW = "screen/forward/error.ftl";

    public static String REDIS_TABLE_KEY_APPOINTMENT_BINDWX_TOKEN = "table_portal_token";//扫码绑定微信凭据
    public static String REDIS_TABLE_KEY_APPOINTMENT_REMIND_MSG_SEND_FLAG = "table_appointment_remind_msg_send";//提醒消息发送标记

    public static int SPACE_CONTRACT_STATUS_NOT_SIGN = 0; //供应商合同状态-未签字
    public static int SPACE_CONTRACT_STATUS_NOT_AUDITED = 1; //供应商合同状态-未审核
    public static int SPACE_CONTRACT_STATUS_AUDITED_PASS = 2; //供应商合同状态-审核通过
    public static int SPACE_CONTRACT_STATUS_AUDITED_REFUSE = 3; //供应商合同状态-审核不通过
}
