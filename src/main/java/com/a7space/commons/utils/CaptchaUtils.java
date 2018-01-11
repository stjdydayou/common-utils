package com.a7space.commons.utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.patchca.color.ColorFactory;
import org.patchca.filter.predefined.CurvesRippleFilterFactory;
import org.patchca.filter.predefined.DiffuseRippleFilterFactory;
import org.patchca.filter.predefined.DoubleRippleFilterFactory;
import org.patchca.filter.predefined.MarbleRippleFilterFactory;
import org.patchca.filter.predefined.WobbleRippleFilterFactory;
import org.patchca.service.Captcha;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.word.RandomWordFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CaptchaUtils {

private static Logger logger = LoggerFactory.getLogger(CaptchaUtils.class);
			 
	private static ConfigurableCaptchaService configurableCaptchaService = new ConfigurableCaptchaService();
	private static Random random = new Random();
	static {
		configurableCaptchaService.setColorFactory(new ColorFactory() {
			@Override
			public Color getColor(int x) {
				int[] c = new int[3];
				int i = random.nextInt(c.length);
				for (int fi = 0; fi < c.length; fi++) {
					if (fi == i) {
						c[fi] = random.nextInt(71);
					} else {
						c[fi] = random.nextInt(256);
					}
				}
				return new Color(c[0], c[1], c[2]);
			}
		});
		RandomWordFactory wf = new RandomWordFactory();
		wf.setCharacters("23456789abcdefghigkmnpqrstuvwxyzABCDEFGHIGKLMNPQRSTUVWXYZ");
		wf.setMaxLength(4);
		wf.setMinLength(4);
		configurableCaptchaService.setWordFactory(wf);
	}

	public static ConfigurableCaptchaService getConfigurableCaptchaService(){
		try {
			switch (random.nextInt(5)) {
			case 0:
				configurableCaptchaService.setFilterFactory(new CurvesRippleFilterFactory(configurableCaptchaService.getColorFactory()));
				break;
			case 1:
				configurableCaptchaService.setFilterFactory(new MarbleRippleFilterFactory());
				break;
			case 2:
				configurableCaptchaService.setFilterFactory(new DoubleRippleFilterFactory());
				break;
			case 3:
				configurableCaptchaService.setFilterFactory(new WobbleRippleFilterFactory());
				break;
			case 4:
				configurableCaptchaService.setFilterFactory(new DiffuseRippleFilterFactory());
				break;
			}
			return configurableCaptchaService;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error:{}",e);
			return null;
		}
	}
	public static Captcha createCaptcha() {
		try {
			configurableCaptchaService=getConfigurableCaptchaService();
			return configurableCaptchaService.getCaptcha();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error:{}",e);
			return null;
		}
	}
	
	public static byte[] getCaptchaBytes(Captcha captcha){
		byte[] result=null;
		try {
			if(captcha==null){
				logger.error("captcha can not be null");
				return result;
			}
    		BufferedImage image = captcha.getImage();
    	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	    try {
    	      ImageIO.write(image, "jpg", baos);
    	    } catch (IOException e) {
    	      logger.error("convert image to byte error: ", e);
    	    }
    	    result = baos.toByteArray();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error:{}",e);
		}
		return result;
	}
	
	/**
	 * @param captcha将验证码生成BASE64编码字符串
	 * @使用方法
	 * 1.在controller层直接返回该字符串
	 * 2.在页面中使用<img class="input-group-addon" id="imgVerifyCode" src="" data-url="${staticResPath}/captcha.ajax" style="width:100px;height:42px;border:0px;">
	 * 3.在js中增加展示和刷新方法
	 	function loadCaptcha(){
			var captchaUrl = $("#imgVerifyCode").attr("data-url");
			$.ajax({
				type : "get",
				url : captchaUrl +"?t="+ Math.random(),
				success : function (msg) {
					var bg = 'data:image/jpeg;base64,' + msg.replace(/\"/g, "");
					$('#imgVerifyCode').attr('src', bg);
				}
			});
		}
		
		$("#imgVerifyCode").click(function(){
			loadCaptcha();
		});
	 */
	public static String getCaptchaBase64String(Captcha captcha){
		String result="";
		byte[] data=getCaptchaBytes(captcha);
		if(data!=null){
			result=Base64.encodeBase64String(data);
		}
		return result;
	}
}
