package com.a7space.commons.patchca;


import org.apache.commons.codec.binary.Base64;
import com.a7space.commons.patchca.color.SingleColorFactory;
import com.a7space.commons.patchca.filter.predefined.*;
import com.a7space.commons.redis.RedisConstants;
import com.a7space.commons.redis.RedisService;
import com.a7space.commons.utils.TokenUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.text.MessageFormat;
import java.util.Random;

@Component("simpleCaptchaService")
public class SimpleCaptchaServiceImpl implements SimpleCaptchaService {
	private Logger logger = LoggerFactory.getLogger(SimpleCaptchaServiceImpl.class);

	@Autowired
	private RedisService redisService;

	public BufferedImage generateImage(String token){
		try{
			ConfigurableCaptchaService cs = new ConfigurableCaptchaService();
			cs.setColorFactory(new SingleColorFactory(new Color(25, 60, 170)));
			Random random = new Random();
			switch (random.nextInt(4)) {
				case 0:
					cs.setFilterFactory(new CurvesRippleFilterFactory(cs.getColorFactory()));
					break;
				case 1:
					cs.setFilterFactory(new MarbleRippleFilterFactory());
					break;
				case 2:
					cs.setFilterFactory(new DoubleRippleFilterFactory());
					break;
				case 3:
					cs.setFilterFactory(new WobbleRippleFilterFactory());
					break;
				case 4:
					cs.setFilterFactory(new DiffuseRippleFilterFactory());
					break;
			}

			Captcha captcha = cs.getCaptcha();
			if (TokenUtil.checkToken(token)) {
				this.redisService.setString(MessageFormat.format(RedisConstants.CAPTCHA_SESSION_KEY, token), captcha.getChallenge(), 5 * 60);
			} else {
				logger.warn("生成验证码的 Token 无效");
			}
			return captcha.getImage();
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("error:{}",e);
			return null;
		}
	}
	
	/**
	 * 产生一个验证码图片二进制流
	 */
	@Override
	public String generateCaptcha(String token) {
		try {
			BufferedImage image=generateImage(token);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(image, "png", baos);
			Base64 b64 = new Base64();
			String data = b64.encodeToString(baos.toByteArray());
			data = "data:image/png;base64," + data;
			return data;
		} catch (Exception e) {
			logger.error("convert image to byte error: ", e);
		}
		return null;
	}

	/**
	 * 比较验证码
	 *
	 * @param captcha
	 * @param token
	 * @param destroySession
	 */
	@Override
	public boolean compareCaptcha(String captcha, String token, boolean destroySession) {
		logger.debug(MessageFormat.format(RedisConstants.CAPTCHA_SESSION_KEY, token));
		String _captcha = this.redisService.getString(MessageFormat.format(RedisConstants.CAPTCHA_SESSION_KEY, token));
		if (destroySession) {
			this.redisService.del(MessageFormat.format(RedisConstants.CAPTCHA_SESSION_KEY, token));
		}
		logger.debug("SESSION captcha " + _captcha);
		logger.debug("INPUT captcha " + captcha);
		return _captcha != null && captcha != null && _captcha.toString().toUpperCase().equals(captcha.toUpperCase());
	}
}
