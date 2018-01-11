package com.a7space.commons.datasource;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

public class JdbcConfig implements Serializable{
	
	public JdbcConfig() {
		// TODO Auto-generated constructor stub
	}
	
	public JdbcConfig(String jdbcConfigString) {
		// TODO Auto-generated constructor stub
		JdbcConfig jdbcConfig=JSONObject.parseObject(jdbcConfigString, JdbcConfig.class);
		driver=jdbcConfig.getDriver();
		url=jdbcConfig.getUrl();
		username=jdbcConfig.getUsername();
		password=jdbcConfig.getPassword();
	}
	
    private String driver;

    private String url;

    private String username;

    private String password;

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

