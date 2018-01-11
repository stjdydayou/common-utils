package com.a7space.commons.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.a7space.commons.utils.ServletContext;
import com.a7space.commons.utils.StringUtils;

/**
 * Created on 2017年2月23日
 * <p>Title:       开放平台_[子平台名]_[模块名]/p>
 * <p>Description: [读取设置浏览器端cookies工具]</p>
 * @author         [李德] [510657316@qq.com]
 * @version        1.0
*/
public class CookieUtil {
    /**
     *  Created on 2017年2月23日 
     * <p>Discription:[设置客户端cookie，并且加密存储]</p>
     * @author:[李德]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @param key cookie加密前的key
     * @param value cookie加密前的值
     * @return
     */
    public static void setCookie(String key, String value){
        setCookie(ServletContext.getResponse(), key, value, null);
    }
    /**
     *  Created on 2017年2月23日 
     * <p>Discription:[设置客户端cookie，并且加密存储]</p>
     * @author:[李德]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @param key cookie加密前的key
     * @param value cookie加密前的值
     * @param expire cookie有效期，单位为秒
     * @return
     */
    public static void setCookie(String key, String value, long expire){
        setCookie(ServletContext.getResponse(), key, value, new Long(expire));
    }
    /**
     *  Created on 2017年2月23日 
     * <p>Discription:[设置客户端cookie，并且加密存储]</p>
     * @author:[李德]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @param key cookie加密前的key
     * @param value cookie加密前的值
     * @return
     */
    public static void setCookie(HttpServletResponse response, String key, String value){
        setCookie(response, key, value, null);
    }
    /**
     *  Created on 2017年2月23日 
     * <p>Discription:[设置客户端cookie，并且加密存储]</p>
     * @author:[李德]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @param key cookie加密前的key
     * @param value cookie加密前的值
     * @param expire cookie有效期，单位为秒
     * @return
     */
    public static void setCookie(HttpServletResponse response, String key, String value, Long expire){
        String encryptKey=null;
        String encryptValue = null;
        if (StringUtils.isNotEmpty(value)) {
            //encryptValue = CryptoBase64Util.encryption(value);
            encryptValue=CryptoDESUtil.encode(value);
        }
        //encryptKey=CryptoBase64Util.encryption(key);
        encryptKey=CryptoDESUtil.encode(key);
        Cookie cookie = new Cookie(encryptKey, encryptValue);
        if (expire != null) {
            cookie.setMaxAge(expire.intValue());
        }
        String domain = ServletUtil.getSecondLevelDomain();
        if (StringUtils.isNotEmpty(domain)) {
            cookie.setDomain(domain);
        }
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     *  Created on 2017年2月23日 
     * <p>Discription:[读取加密保存的cookie]</p>
     * @author:[李德]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @param key 客户端中cookies是加密保存，key是加密前的明文字符串
     * @return
     */
    public static String getCookie(String key){
        return getCookie(ServletContext.getRequest(), key);
    }

    /**
     *  Created on 2017年2月23日 
     * <p>Discription:[读取加密保存的cookie]</p>
     * @author:[李德]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @param request
     * @param key 客户端中cookies是加密保存，key是加密前的明文字符串
     * @return
     */
    public static String getCookie(HttpServletRequest request, String key){
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        //String desKey = CryptoBase64Util.encryption(key);
        String desKey=CryptoDESUtil.encode(key);
        for (Cookie c : cookies) {
            if (desKey.equals(c.getName())) {
                //return CryptoBase64Util.decryption(c.getValue());
                return CryptoDESUtil.decode(c.getValue());
            }
        }
        return null;
    }

    /**
     * Created on 2017年02月23日
     * <p>Description:读未加密保存的cookie</p>
     * @param key 客户端中cookies没有加密保存，key明文字符串
     * @author: 李德
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     */
    public static String getCookieSourceKey(String key){
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        Cookie[] cookies = ServletContext.getRequest().getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie c : cookies) {
            if (key.equals(c.getName())) {
                return c.getValue();
            }
        }
        return null;
    }
    


    /**
     * Created on 2017年02月23日
     * <p>Description:删除cookie</p>
     * @author: 李德
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     */
    public static void cleanCookie(String key){
        setCookie(key, null, 0);
    }
}