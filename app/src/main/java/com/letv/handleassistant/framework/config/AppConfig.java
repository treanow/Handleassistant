package com.letv.handleassistant.framework.config;

import android.content.Context;
import android.content.res.AssetManager;


import com.letv.handleassistant.constant.Constant;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;



public final class AppConfig {
	private static AppInfo appInfo;
	private AppConfig() {
		
	}
	public static AppInfo getAppConfigInfo(Context context) {
		AssetManager am = context.getAssets();
		try {
			InputStream is = am.open(Constant.APP_CONFIG_FILE_NAME);
			return parse(is);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static AppInfo parse(InputStream is) throws Exception {
		appInfo = new AppInfo();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = -1;
		while ((i = is.read()) != -1) {
			baos.write(i);
		}
		String configString = baos.toString();
		JSONObject appConfig = new JSONObject(configString);
		appInfo.serverAddress = appConfig.getString("server_address");
		appInfo.os = appConfig.getString("os");
		appInfo.crc = appConfig.getString("crc");
		appInfo.uid = appConfig.getString("uid");
		baos.close();
		is.close();
		return appInfo;
	}

}
