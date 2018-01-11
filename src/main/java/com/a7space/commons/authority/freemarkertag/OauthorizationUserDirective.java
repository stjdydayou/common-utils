package com.a7space.commons.authority.freemarkertag;

import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.a7space.commons.authority.OauthCookie;
import com.a7space.commons.authority.OauthService;
import com.a7space.commons.authority.OauthUser;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

public class OauthorizationUserDirective implements TemplateDirectiveModel {

    @Autowired
    private OauthService authService;

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

        HttpServletRequest request = attr.getRequest();

        String sid = OauthCookie.getSid(request);

        String oauthUserName = params.get("name") == null ? "oauthUser" : params.get("name").toString();

        OauthUser oauthUser = this.authService.getOAuth(sid);

        env.setVariable(oauthUserName, ObjectWrapper.DEFAULT_WRAPPER.wrap(oauthUser));
        body.render(env.getOut());
    }

}
