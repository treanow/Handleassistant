package com.letv.handleassistant.utils;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	public final static String md5(String source) {
		String dest = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			char[] charArray = source.toCharArray();
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
			dest = hexValue.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return dest;
	}
//	public static void main(String[] args) {
//		String s = "111111";
//		//e10adc3949ba59abbe56e057f20f883e
//		System.out.println(MD5.md5(MD5.md5(s)));
//	}
	
}
