package com.a7space.commons.exception;

public class NoLoginException extends Exception {

	private static final long serialVersionUID = 3934335121640650570L;

	private String loginUrl;

	public NoLoginException(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}
}
