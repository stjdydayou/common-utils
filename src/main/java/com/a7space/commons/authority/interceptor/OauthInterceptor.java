package com.a7space.commons.authority.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.a7space.commons.authority.OauthService;
import com.a7space.commons.authority.OauthUser;
import com.a7space.commons.authority.annotation.RequiresPermissions;
import com.a7space.commons.exception.NoLoginException;
import com.a7space.commons.exception.PermissionDeniedException;

public class OauthInterceptor extends HandlerInterceptorAdapter {

	private Logger logger = LoggerFactory.getLogger(OauthInterceptor.class);

	@Autowired
	private OauthService oauthService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod _handler = (HandlerMethod) handler;
			RequiresPermissions requiresPermissions = _handler.getMethodAnnotation(RequiresPermissions.class);

			OauthUser authUser = this.oauthService.getOAuth();
			if (requiresPermissions != null) {
				if (!this.oauthService.isAuth()) {
					throw new NoLoginException("/login.jsp");
				}
				boolean hasAuthority = false;
				String[] arrayAuthorities = requiresPermissions.value();
				if (arrayAuthorities.length <= 0) {
					hasAuthority = true;
				} else {
					for (String authority : arrayAuthorities) {
						if (this.oauthService.hasAuthority(authority)) {
							hasAuthority = true;
							break;
						}
					}
				}
				if (!hasAuthority) {
					throw new PermissionDeniedException();
				}
				
			}
			request.setAttribute("authUser", authUser);
		}
		return true;
	}
}
