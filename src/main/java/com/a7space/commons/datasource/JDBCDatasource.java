package com.a7space.commons.datasource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.alibaba.fastjson.JSONObject;

public class JDBCDatasource {
	
	private JdbcConfig jdbcConfig;
	private DriverManagerDataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	public JDBCDatasource(String jdbcConfigString) {
		JdbcConfig jdbcConfig=new JdbcConfig(jdbcConfigString);
		dataSource=getDataSource(jdbcConfig);
		jdbcTemplate=new JdbcTemplate(dataSource);
	}
	public JDBCDatasource(JdbcConfig jdbcConfig) {
		// TODO Auto-generated constructor stub
		dataSource=getDataSource(jdbcConfig);
		jdbcTemplate=new JdbcTemplate(dataSource);
		
	}
	public DriverManagerDataSource getDataSource(String driverClassName,String url,String username,String password) {
		DriverManagerDataSource ds=new DriverManagerDataSource();
		ds.setDriverClassName(driverClassName);
		ds.setUrl(url);
		ds.setUsername(username);
		ds.setPassword(password);
		return ds;
	}
	
	public DriverManagerDataSource getDataSource(JdbcConfig jdbcConfig) {
		return getDataSource(jdbcConfig.getDriver(),jdbcConfig.getUrl(), jdbcConfig.getUsername(), jdbcConfig.getPassword());
	}
	
	
	public JdbcConfig getJdbcConfig() {
		return jdbcConfig;
	}
	public void setJdbcConfig(JdbcConfig jdbcConfig) {
		this.jdbcConfig = jdbcConfig;
	}
	public DriverManagerDataSource getDataSource() {
		return dataSource;
	}
	public void setDataSource(DriverManagerDataSource dataSource) {
		this.dataSource = dataSource;
	}
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
