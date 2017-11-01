package com.letv.handleassistant.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class IOUtil {
	private IOUtil() {
	}
	
	public static String input2Str(InputStream in) throws IOException {
		return input2Str(in,"utf-8");
	}
	public static String input2Str(InputStream in, String code) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		int i = 0;
		byte [] bs = new byte[1024];
		while((i = in.read(bs))!=-1){
			out.write(bs,0,i);
		}
		byte [] bytes = out.toByteArray();
		String str = new String(bytes,code);
		return str;
	}
}
