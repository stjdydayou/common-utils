package com.a7space.commons.authority;


import java.util.List;

import com.a7space.commons.utils.ValidateUtil;
import com.a7space.commons.utils.encry.Encrypt;

public abstract class OauthService {

	public String getSid() {
		String sid = OauthCookie.getSid();
		if (ValidateUtil.isEmpty(sid)) {
			sid = OauthCookie.createdSid();
		}
		return sid;
	}


	public String genPwd(String loginPwd, String secret, boolean needSha1) {
		if (loginPwd == null) {
			return "";
		}
		if (needSha1) {
			loginPwd = Encrypt.sha1(loginPwd);
		}
		secret = Encrypt.md5(secret.toUpperCase());
		return Encrypt.md5(loginPwd.toUpperCase() + "@" + secret);
	}


	public boolean isAuth() {
		String sid = OauthCookie.getSid();
		if (sid != null) {
			return isAuth(sid);
		}
		return false;
	}

	public boolean isAuth(String sid) {
		boolean auth = false;
		OauthUser authUser = this.getOAuth(sid);
		if (authUser != null && authUser.getId() != null) {
			auth = true;
		}
		return auth;
	}


	public OauthUser getOAuth() {
		String sid = this.getSid();
		return this.getOAuth(sid);
	}

	public void setAuth(OauthUser oauthUser) {
		String sid = this.getSid();
		this.setAuth(sid, oauthUser);
	}

	public void setAuthorities(List<String> listAuthorities) {
		String sid = this.getSid();
		this.setAuthorities(sid, listAuthorities);
	}

	public boolean hasAuthority(String authority) {
		String sid = this.getSid();
		return this.hasAuthority(sid, authority);
	}

	public void destroy() {
		String sid = OauthCookie.getSid();
		destroy(sid);
	}

	public abstract void setAuth(String sid, OauthUser oauthUser);

	public abstract OauthUser getOAuth(String sid);

	public abstract void setAuthorities(String sid, List<String> listAuthorities);

	public abstract boolean hasAuthority(String sid, String authority);

	public abstract void destroy(String sid);
}
