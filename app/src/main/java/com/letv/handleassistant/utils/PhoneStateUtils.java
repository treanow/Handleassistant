package com.letv.handleassistant.utils;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class PhoneStateUtils {
	private TelephonyManager mPhoneManager;

	@SuppressWarnings("static-access")
	public PhoneStateUtils(Context context) {
		mPhoneManager = (TelephonyManager) context
				.getSystemService(context.TELEPHONY_SERVICE);
	}

	// 获得手机型号
	public String getPhoneModel() {
		return Build.MODEL;
	}

	/**
	 * 获得系统型号
	 * 
	 * @return
	 */
	public String getSysModel() {
		return Build.VERSION.RELEASE;
	}

	// 获得imei号
	public String getPhoneIMEI() {
		return mPhoneManager.getDeviceId();
	}

	// 获得手机号
	public String getPhoneNum() {
		return mPhoneManager.getLine1Number();
	}

	public String getIMSI() {
		String myIMSI = mPhoneManager.getSubscriberId();
		return myIMSI;
	}

	// 获得ip
	public String getIp() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (Exception e) {
		}
		return "0.0.0.0";
	}
}
