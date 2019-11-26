package com.quiet.live.hall.utils.base;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.quiet.live.hall.utils.string.StringUtil;

public class MD5Util {
	public static String md5(String text) {
		MessageDigest msgDigest = null;
		try {
			msgDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("System doesn't support MD5 algorithm.");
		}
		msgDigest.update(text.getBytes());
		byte[] bytes = msgDigest.digest();

		String md5Str = new String();
		for (int i = 0; i < bytes.length; ++i) {
			byte tb = bytes[i];
			char tmpChar = (char) (tb >>> 4 & 0xF);
			char high;
			if (tmpChar >= '\n')
				high = (char) ('a' + tmpChar - 10);
			else {
				high = (char) ('0' + tmpChar);
			}
			md5Str = md5Str + high;
			tmpChar = (char) (tb & 0xF);
			char low;
			if (tmpChar >= '\n')
				low = (char) ('a' + tmpChar - 10);
			else {
				low = (char) ('0' + tmpChar);
			}
			md5Str = md5Str + low;
		}

		return md5Str;
	}

	public static void main(String[] args) {
		System.out.println(md5("111111"));
	}
}