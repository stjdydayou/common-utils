package com.a7space.commons.authority;

import com.a7space.commons.utils.SpringContextUtil;
import com.alibaba.fastjson.JSONObject;

public class SecurityUtils {

    private static final OauthService oauthService = SpringContextUtil.getBean("oauthService", OauthService.class);

    public static <T> T getSubject(Class<T> clazz) {
        Object object = getSubject();
        T accessUserPO = JSONObject.toJavaObject((JSONObject) object, clazz);
        return accessUserPO;
    }

    public static Object getSubject() {
        //Object userInfo=new Object();
        OauthUser userInfo = oauthService.getOAuth();
        if (userInfo != null) {
            return userInfo.getUserInfo();
        } else {
            return null;
        }
    }
}
