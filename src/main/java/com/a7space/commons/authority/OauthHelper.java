package com.a7space.commons.authority;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OauthHelper {

	public static void main(String[] args) {
		long t1 = new Date().getTime();

		List<Long> authorityKeys = new ArrayList<Long>();
		authorityKeys.add(1L);
		authorityKeys.add(100L);

		String s = OauthHelper.makeAuthority(authorityKeys);

		System.out.println(new Date().getTime() - t1);

		OauthHelper.hasAuthority(1000000, s);

		System.out.println(new Date().getTime() - t1);
	}

	/**
	 * 判断是否有权限
	 * 
	 * @param authorityKey
	 * @param rc
	 * @return
	 */
	public static boolean hasAuthority(int authorityKey, String rc) {
		if (rc == null || "".equals(rc)) {
			return false;
		}
		if (authorityKey >= 0 && rc.length() > authorityKey && rc.charAt(authorityKey) == '1') {
			return true;
		}
		return false;
	}

	/**
	 * 创建权限字符串
	 * 
	 * @param authorityKeys
	 *            有权限的项,比如 1,3,6,11,20
	 * @return 权限字符串, 比如0101001001000000000
	 */
	public static String makeAuthority(List<Long> authorityKeys) {
		// 找出最大的权限Key值
		long maxKey = 0;
		for (long authorityKey : authorityKeys) {
			if (authorityKey > maxKey) {
				maxKey = authorityKey;
			}
		}

		// 生成没有权限的字串，全部是0
		StringBuilder sb = new StringBuilder("1");
		for (int i = 1; i <= maxKey; i++) {
			sb.append("0");
		}

		// 生成带有权限值的字串
		for (long authorityKey : authorityKeys) {
			if (authorityKey <= 0) {
				continue;
			}
			sb.setCharAt(Long.valueOf(authorityKey).intValue(), '1');
		}

		return sb.toString();
	}
}
