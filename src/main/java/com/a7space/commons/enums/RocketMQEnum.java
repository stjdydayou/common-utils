package com.a7space.commons.enums;

public enum RocketMQEnum {

	/**
	 * 空间配置消息通道
	 */
	TOPIC_SPACE_INFO("topic_space_info"),
	/**
	 * 空间单价配置消息
	 */
	TAG_INIT_SPACE_UNIT_PRICE("tag_init_space_unit_price");
	
	private String code;
	
	private RocketMQEnum(String code) {
		// TODO Auto-generated constructor stub
		this.code=code;
	}
	
	public String getCode() {
		return code;
	}
}
