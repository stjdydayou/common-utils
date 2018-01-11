package com.a7space.commons.utils.encry;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Encrypt {
	/**
	 * 获取MD5加密后的字符，算法不可以逆
	 * 
	 * @param str
	 * @return
	 */
	public static String md5(String str) {
		return new EncryMD5().getMD5ofStr(str);
	}

	/**
	 * 获取SHA1加密后的字符，算法不可以逆
	 * 
	 * @param str
	 * @return
	 */
	public static String sha1(String str) {
		return new EncrySHA1().getDigestOfString(str.getBytes());
	}

	/**
	 * 生成签名算法
	 * 
	 * @param paramValues
	 * @param secret
	 * @return
	 */
	public static String sign(Map<String, String> paramValues, String secret) {
		StringBuilder sign = new StringBuilder();

		try {
			byte[] sha1Digest = null;
			StringBuilder sb = new StringBuilder();
			List<String> paramNames = new ArrayList<String>(paramValues.size());
			paramNames.addAll(paramValues.keySet());
			Collections.sort(paramNames);

			sb.append(secret);
			for (String paramName : paramNames) {
				sb.append(paramName).append(paramValues.get(paramName));
			}
			sb.append(secret);
			System.out.println(sb.toString());
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			sha1Digest = md.digest(sb.toString().getBytes("UTF-8"));

			for (int i = 0; i < sha1Digest.length; i++) {
				String hex = Integer.toHexString(sha1Digest[i] & 0xFF);
				if (hex.length() == 1) {
					sign.append("0");
				}
				sign.append(hex.toUpperCase());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sign.toString();
	}
}
