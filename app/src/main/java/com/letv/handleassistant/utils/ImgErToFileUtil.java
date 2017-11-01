package com.letv.handleassistant.utils;


import com.letv.handleassistant.constant.Constant;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;



/**
 * 将二进制流转换成图片文件
 * 
 * @author 晚风工作室 www.soservers.com
 *
 */

public class ImgErToFileUtil {

	public static void bytes2Str(byte[] bytes) {
		try {

			// 生成字符串
			String imgStr = byte2hex(bytes);
			System.out.println(imgStr);

			// 将字符串转换成二进制，用于显示图片
			// 将上面生成的图片格式字符串 imgStr，还原成图片显示
			OutputStream o = new FileOutputStream(Constant.imageDir + "/878789.jpeg");
			byte[] imgByte = hex2byte(imgStr);
			InputStream in = new ByteArrayInputStream(imgByte);
			byte[] b = new byte[1024];
			int nRead = 0;
			while ((nRead = in.read(b)) != -1) {
				o.write(b, 0, nRead);
			}
			o.flush();
			o.close();
			in.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}

	/**
	 * 二进制转字符串
	 * 
	 * @param b
	 *            byte数组
	 * @return 二进制字符串
	 */
	public static String byte2hex(byte[] b) {
		StringBuffer sb = new StringBuffer();
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0XFF);
			if (stmp.length() == 1) {
				sb.append("0" + stmp);
			} else {
				sb.append(stmp);
			}
		}
		return sb.toString();
	}

	/**
	 * 字符串转二进制
	 * 
	 * @param str
	 *            字符串
	 * @return byte数组
	 */
	public static byte[] hex2byte(String str) {
		if (str == null)
			return null;
		str = str.trim();
		int len = str.length();
		if (len == 0 || len % 2 == 1)
			return null;
		byte[] b = new byte[len / 2];
		try {
			for (int i = 0; i < str.length(); i += 2) {
				b[i / 2] = (byte) Integer.decode("0X" + str.substring(i, i + 2)).intValue();
			}
			return b;
		} catch (Exception e) {
			return null;
		}
	}

}