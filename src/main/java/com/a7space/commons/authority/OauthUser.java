package com.a7space.commons.authority;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class OauthUser implements Serializable {

	private static final long serialVersionUID = -7252878048497511413L;

	private String id; // 唯一标识

	private String nickName; // 用户的登录账号

	private Date loginTime; // 最后登录时间

	private String sid; // 用户登录令牌

	private Object userInfo;//用户信息
	
	public OauthUser() {
		super();
	}

	public OauthUser(String id, String nickName, Date loginTime, String sid,Object userInfo) {
		this.id = id;
		this.nickName = nickName;
		this.loginTime = loginTime;
		this.sid = sid;
		this.userInfo=userInfo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public Object getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(Object userInfo) {
		this.userInfo = userInfo;
	}
}
