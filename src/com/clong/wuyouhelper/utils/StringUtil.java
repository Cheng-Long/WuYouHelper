package com.clong.wuyouhelper.utils;

import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * 
 * @author: cl
 * @date: 2016-3-2-下午8:40:05
 */
public class StringUtil {

	/**
	 * 格式化为MD5字符串
	 * 
	 * @author: cl 需要转换的字符串
	 * @param str
	 * @return
	 * @date: 2016-3-2-下午8:21:33
	 */
	public static String toMD5(String str) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = str.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	/**
	 * 校验字符串是否为手机号码
	 * 
	 * @author: cl
	 * @param str
	 * @return
	 * @date: 2016-3-2-下午8:49:31
	 */
	public static boolean isPhoneNO(String phoneNo) {
		String regex = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(phoneNo);
		return m.matches();
	}

	/**
	 * 校验字符串是否为邮箱地址
	 * 
	 * @author: cl
	 * @param mail
	 * @return
	 * @date: 2016-3-2-下午8:56:31
	 */
	public static boolean isMail(String mail) {
		String regex = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(mail);
		return m.matches();
	}
}
