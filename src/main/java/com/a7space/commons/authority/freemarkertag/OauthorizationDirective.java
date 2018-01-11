package com.a7space.commons.authority.freemarkertag;


import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.a7space.commons.authority.OauthCookie;
import com.a7space.commons.authority.OauthService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

public class OauthorizationDirective implements TemplateDirectiveModel {

	@Autowired
	private OauthService authService;

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {

		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

		HttpServletRequest request = attr.getRequest();

		String sid = OauthCookie.getSid(request);

		boolean hasAuthority = false;

		String authoritiesString = params.get("authorities") == null ? "" : params.get("authorities").toString().trim();

		String[] arrayAuthorities = authoritiesString.split(";");

		if (arrayAuthorities.length > 0) {
			for (String authority : arrayAuthorities) {
				if (this.authService.hasAuthority(sid, authority)) {
					hasAuthority = true;
					break;
				}
			}
		} else {
			hasAuthority = true;
		}

		env.setVariable("hasAuthority", ObjectWrapper.DEFAULT_WRAPPER.wrap(hasAuthority));
		body.render(env.getOut());
	}

}
