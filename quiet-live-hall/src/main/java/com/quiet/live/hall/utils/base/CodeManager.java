package com.quiet.live.hall.utils.base;

import java.util.Random;

public class CodeManager {
	public static String getRandomPwd(int pwd_len) {
		int maxNum = 36;

		int count = 0;
		char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
				't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

		StringBuffer pwd = new StringBuffer("");
		Random r = new Random();
		while (count < pwd_len) {
			int i = Math.abs(r.nextInt(36));

			if ((i >= 0) && (i < str.length))
				;
			pwd.append(str[i]);
			++count;
		}

		return pwd.toString();
	}

	public static String getRandomNum(int pwd_len) {
		int maxNum = 36;

		int count = 0;
		char[] str = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

		StringBuffer pwd = new StringBuffer("");
		Random r = new Random();
		while (count < pwd_len) {
			int i = Math.abs(r.nextInt(maxNum));

			if (i >= 0 && i < str.length){
				pwd.append(str[i]);
				++count;
			}
		}

		return pwd.toString();
	}
}
