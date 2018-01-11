package com.a7space.commons.redis;

public class RedisConstants {
	public static final String REDIS_LUA_SCRIPT_INCR_SHA = "LUA_SCRIPT:INCR_SHA";

	public static final String REDIS_LUA_SCRIPT_DECR_SHA = "LUA_SCRIPT:DECR_SHA";

	public static final String CAPTCHA_SESSION_KEY="CAPTCHA:SESSION:{0}";
	
	public static String PORTAL_COOKIE_OPENID="weixin_user_openid";//微信浏览器中cookies中已经登录的userid
	public static long PORTAL_COOKIE_USERID_EXPIRE=2592000;//微信浏览器中cookies中userid保存有效期,30天
	public static String PORTAL_COOKIE_VISITORID="visitor_id";//所有用户第一次访问时会存放一个惟一标识，不存在时会再创建。

    public static int REDIS_DB_WEBSITE=1;
	public static String REDIS_KEY_ACCESS_LIMIT_PAGE_LOCKS="access_limit_page_locks";//访问频率限制页面锁
    public static String REDIS_KEY_ACCESS_LIMIT_PAGE_COUNTS="access_limit_page_counts";//访问频率限制页面访问次数


//	public static final String REDIS_LIST_LOTTERY_INFO = "LIST_LOTTERY_INFO";

	public static final String REDIS_SYS_CONFIG_KEY = "SYSTEM:CONFIG:{0}";

	public static final String REDIS_VERIFY_MAIL_CHANGE = "VERIFY:MAIL:CHANGE:{0}";

	public static final String USER_BALANCE_LOCK = "USER:BALANCE:LOCK:{0}";

	public static final String GENERATE_ORDER_INCR = "GENERATE:ORDER:INCR:{0}";
	
	public static int REDIS_DB_ELASTIX=2;//第三方电话系统分机号码存放
}
