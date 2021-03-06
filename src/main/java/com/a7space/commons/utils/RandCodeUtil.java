package com.a7space.commons.utils;


import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.UUID;
public class RandCodeUtil {
	public static String genAppKeyId() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String s = sdf.format(DateUtil.getCurrentDate());
		s = s + get(4, true);
		return s;
	}

	public static String getSalt() {
		return get(8, false);
	}

	/**
	 * 生成UUID
	 *
	 * @return
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.replace("-", "");
		return uuid;
	}

	/**
	 * 生成随机码
	 *
	 * @param len      长度
	 * @param isNumber 是否为全数字
	 * @return
	 */
	public static String get(int len, boolean isNumber) {
		String[] codes = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
				"S", "T", "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y",
				"z"};
		Random random = new SecureRandom();
		String code = "";
		for (int i = 0; i < len; i++) {
			if (isNumber) {
				code += codes[random.nextInt(10)];
			} else {
				code += codes[random.nextInt(codes.length)];
			}
		}
		return code;
	}
}
