package com.a7space.commons.enums;

public enum ExceptionEnum {
    
    
    SYS_TEM_ERROR("系统错误"),
    TOO_FREQUENTLY_ACCESS("您的访问太频繁，请稍后再试"),
    ILLEGAL_ASSESS("非法访问"),
    
    /**
     * 验证码
     */
    NEED_CODES("需要输入验证码"),
    CODES_ERROR("验证码校验错误"),

    /**
     * 登录错误
     */
    NOT_LOGIN("未登录"),
    LOGIN_FAILED("登录失败"),
    LOGIN_BLACK_USER("非法用户"),
    LOGIN_BASE_WEIXIN_NOT_EXIST("未授权的微信登录--用户不存在");
    
    private String describe;
    private ExceptionEnum(String describe) {
        // TODO Auto-generated constructor stub
        this.describe=describe;
    }
    public String getDescribe() {
        return describe;
    }
    public String getName(){
        return this.name();
    }
}
