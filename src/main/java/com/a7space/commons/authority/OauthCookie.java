package com.a7space.commons.authority;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.a7space.commons.utils.ConfigHelper;
import com.a7space.commons.utils.ServletContext;
import com.a7space.commons.utils.StringPatternUtil;
import com.a7space.commons.utils.TokenUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class OauthCookie {
	private static final Logger logger = LoggerFactory.getLogger(OauthCookie.class);

	/**
	 * 获取得到的SID，如果没有的话则会自创建一个,并且写入到客户端的Cookie中
	 *
	 * @return
	 */
	public static String getSid() {
		HttpServletRequest request = ServletContext.getRequest();
		return getSid(request);
	}

	public static String getSid(HttpServletRequest request) {
		String sid = null;
		
		//如果是android、ios、mweb，从request.header中取
		String source=request.getParameter("weex_source");
		if(StringPatternUtil.StringMatch(source, "(android|ios|mweb)")){
			sid=request.getParameter("weex_sid");
			return sid;
		}

		String oauthCookieName = ConfigHelper.getValue("sid.cookie.name");
		if (oauthCookieName == null) {
			oauthCookieName = OauthConstant.OAUTH_COOKIE_NAME;
		}
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equalsIgnoreCase(oauthCookieName)) {
					sid = c.getValue();
					break;
				}
			}
		}
		return sid;
	}

	public static String createdSid() {
		String sid = (new TokenUtil()).toTokenString();
		String oauthCookieName = ConfigHelper.getValue("sid.cookie.name");
		if (oauthCookieName == null) {
			oauthCookieName = OauthConstant.OAUTH_COOKIE_NAME;
		}

		StringBuilder sb = new StringBuilder(oauthCookieName + "=" + sid + ";");
		sb.append("path=/;");
		sb.append("domain=" + ConfigHelper.getValue("sid.cookie.domain") + ";");
		sb.append("HttpOnly");

		logger.debug("cookie的属性" + sb.toString());
		// 使用会话cookie进行权限验证
		ServletContext.getResponse().setHeader("SET-COOKIE", sb.toString());

		logger.debug("产生新的SID：" + sid);
		return sid;
	}

	public static void removeSid() {

		String oauthCookieName = ConfigHelper.getValue("sid.cookie.name");
		if (oauthCookieName == null) {
			oauthCookieName = OauthConstant.OAUTH_COOKIE_NAME;
		}

		Cookie cookie = new Cookie(oauthCookieName, null);
		cookie.setPath("/");
		cookie.setMaxAge(0);
		cookie.setDomain(ConfigHelper.getValue("sid.cookie.domain"));
		ServletContext.getResponse().addCookie(cookie);
	}

}
