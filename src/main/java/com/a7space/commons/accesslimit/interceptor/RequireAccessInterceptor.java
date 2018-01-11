package com.a7space.commons.accesslimit.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.a7space.commons.accesslimit.annotation.AccessLimit;
import com.a7space.commons.accesslimit.service.AccessLimitService;
import com.a7space.commons.utils.CookieUtil;
import com.a7space.commons.utils.UUIDCreator;

public class RequireAccessInterceptor implements HandlerInterceptor{
	private static Logger logger=LoggerFactory.getLogger(RequireAccessInterceptor.class);
	
	private static String COOKIE_VISITORID="visitor_id";
	private static String COOKIE_USERID="user_id";
	
	@Autowired
	private AccessLimitService accessLimitService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//本地开发环境设置跨域访问
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Cookie, Host, Referer, User-Agent,x-forwarded-for,remote-addr");  

		String visitorId=CookieUtil.getCookie(COOKIE_VISITORID);
        //如果是新访客，置入一个用户标识。
        if(StringUtils.isBlank(visitorId)){
            CookieUtil.setCookie(response, COOKIE_VISITORID, UUIDCreator.randomUUID().toString());
        }
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            AccessLimit accessLimit=method.getAnnotation(AccessLimit.class);
            if(accessLimit!=null){
                return accessLimitCheck(request, response, accessLimit);
            }
        }
        return true;
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	

    /**
     *  Created on 2017年2月23日 
     * <p>Discription:[登录检查以及请求频次检查]</p>
     * @author:[李德]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @param request
     * @param response
     * @param accessLimit
     * @throws Exception
     */
    private boolean accessLimitCheck(HttpServletRequest request,HttpServletResponse response, AccessLimit accessLimit) {
        //用户访问路径
        String url=request.getRequestURI();
        String cookieUserId=CookieUtil.getCookie(COOKIE_USERID);
        String cookieVisitorId=CookieUtil.getCookie(COOKIE_VISITORID);
        
        boolean requireLogin=accessLimit.requireLogin();
        //判断客户端是否登录，登录一次保留30天
        if(requireLogin){
            cookieUserId=CookieUtil.getCookie(COOKIE_USERID);
            if(StringUtils.isBlank(cookieUserId)){
                //没有登录跳转登录
                //response.sendRedirect(weiXinOauthUrl);
                //return false;
            }
        }
        
        //{inSeconds:10,counts:10,freezeSeconds:5}请求频率限制,在规定的时间times秒内，超过访问最大次数counts,将冻结访问freezeTimes秒
        String freequency=accessLimit.frequencyLimit();
        if(StringUtils.isNotBlank(freequency)){
            JSONObject obj=JSONObject.parseObject(freequency);
            int inSeconds=obj.getIntValue("inSeconds");
            int counts=obj.getIntValue("counts");
            int freezeSeconds=obj.getIntValue("freezeSeconds");
            
            //判断该用户的页面锁是否存在，如果存在，说明已经超过次数 直接禁止
            cookieUserId=StringUtils.isBlank(cookieUserId)?cookieVisitorId:cookieUserId;
            if(accessLimitService.isLocked(url,cookieUserId)){
                //throw new LoginException(ExceptionEnum.TOO_FREQUENTLY_ACCESS);
            }
            long accessCount=accessLimitService.addAccessCount(inSeconds, url,cookieUserId);
            if(accessCount>counts){
                accessLimitService.addLocked(freezeSeconds, url,cookieUserId);
                //throw new LoginException(ExceptionEnum.TOO_FREQUENTLY_ACCESS);
            }
        }
        return true;
    }
    
    public void setAccessLimitService(AccessLimitService accessLimitService) {
		this.accessLimitService = accessLimitService;
	}
}