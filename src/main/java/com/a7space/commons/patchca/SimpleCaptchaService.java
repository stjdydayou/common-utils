package com.a7space.commons.patchca;

import java.awt.image.BufferedImage;

public interface SimpleCaptchaService {
	/**
	 * 生成一个图片验证码
	 * 
	 * @param token
	 * @return BufferedImage
	 */
	public BufferedImage generateImage(String token);
	
	/**
	 * 生成一个图片验证码
	 * 
	 * @param token
	 * @return base64的字符串
	 */
	String generateCaptcha(String token);

	/**
	 * 比较验证码
	 * 
	 * @param captcha
	 * @param token
	 * @return
	 */
	boolean compareCaptcha(String captcha, String token, boolean destroySession);
}
