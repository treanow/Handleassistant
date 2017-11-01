package com.letv.handleassistant.utils;

import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.provider.Settings.Secure;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.List;

public class NetUtil {
	private static final String TAG = "MobileUtils";

	/*
	 * 判断网络连接是否已开 2012-08-20true 已打�? false 未打�?
	 */
	public static boolean isNetDeviceAvailable(Context context) {
		boolean bisConnFlag = false;
		ConnectivityManager conManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo network = conManager.getActiveNetworkInfo();
		if (network != null) {
			bisConnFlag = conManager.getActiveNetworkInfo().isAvailable();
		}
		return bisConnFlag;
	}

	public static boolean isNetAvailable(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();

		if (info != null) {
			String typeName = info.getTypeName().toLowerCase(); // WIFI/MOBILE
			boolean isAvailable = false;
			if (typeName.equals("wifi")) {
				isAvailable = true;
			} else {
				String apnName = info.getExtraInfo().toLowerCase();
				if (apnName.contains("net")) {
					isAvailable = true;
				}
			}
			if (isAvailable && info.getDetailedState() == NetworkInfo.DetailedState.CONNECTED) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public static enum ProviderName {
		chinaMobile("中国移动"), chinaUnicom("中国联�?"), chinaTelecom("中国电信"), chinaNetcom("中国网�?"), other("未知");
		private String text;

		private ProviderName(String text) {
			this.text = text;
		}

		public String getText() {
			return this.text;
		}
	}

	/**
	 * 获取SIM卡的IMSI�?SIM卡唯�?��识：IMSI 国际移动用户识别�?（IMSI：International Mobile
	 * Subscriber Identification Number）是区别移动用户的标志， 储存在SIM卡中，可用于区别移动用户的有效信息�?
	 * IMSI由MCC、MNC、MSIN组成，其中MCC为移动国家号码，�?位数字组成，
	 * 唯一地识别移动客户所属的国家，我国为460；MNC为网络id，由2位数字组成，
	 * 用于识别移动客户�?��属的移动网络，中国移动为00，中国联通为01, 中国电信�?3；MSIN为移动客户识别码，采用等�?1位数字构成�?
	 * 唯一地识别国内GSM移动通信网中移动客户�? �?��要区分是移动还是联�?，只�?��得SIM卡中的MNC字段即可
	 */
	public static ProviderName getProviderName(Context context) {
		String imsi = getIMSI(context);
		if (imsi != null) {
			// 因为移动网络编号46000下的IMSI已经用完,�?��虚拟了一�?6002编号�?34/159号段使用了此编号
			LogUtil.log("imsi", Log.INFO, imsi);
			if (imsi.startsWith("46000") || imsi.startsWith("46002") || imsi.startsWith("46007")) {
				return ProviderName.chinaMobile;
			} else if (imsi.startsWith("46001")) {
				return ProviderName.chinaUnicom;
			} else if (imsi.startsWith("46003")) {
				return ProviderName.chinaTelecom;
			} else {
				return ProviderName.other;
			}
		} else {
			return ProviderName.other;
		}
	}

	/**
	 * IMEI 全称�?International Mobile Equipment Identity，中文翻译为国际移动装备辨识码，
	 * 即�?常所说的手机序列号，
	 * 用于在手机网络中识别每一部独立的手机，是国际上公认的手机标志序号，相当于移动电话的身份证。序列号共有15位数字，�?位（TAC）是型号核准号码�?
	 * 代表手机类型
	 * 。接�?位（FAC）是�?��装配号，代表产地。后6位（SNR）是串号，代表生产顺序号。最�?位（SP）一般为0，是�?��码，备用�?
	 * 国际移动装备辨识码一般贴于机身背面与外包装上，同时也存在于手机记忆体中，通过输入*#06#即可查询�?
	 * 
	 * @param context
	 * @return
	 */
	public static String getIMEI(Context context) {
		TelephonyManager ts = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return ts.getDeviceId();
	}

	/**
	 * IMSI 全称�?International Mobile Subscriber
	 * Identity，中文翻译为国际移动用户识别码�?它是在公众陆地移动电话网
	 * （PLMN）中用于唯一识别移动用户的一个号码�?在GSM网络，这个号码�?常被存放在SIM卡中
	 * 
	 * @param context
	 * @return
	 */
	public static String getIMSI(Context context) {
		TelephonyManager ts = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return ts.getSubscriberId();
	}

	/**
	 * gps是否�?��
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isGPSEnable(Context context) {
		/*
		 * 用Setting.System来读取也可以，只是这是更旧的用法 String str =
		 * Settings.System.getString(getContentResolver(),
		 * Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
		 */
		String str = Secure.getString(context.getContentResolver(), Secure.LOCATION_PROVIDERS_ALLOWED);
		Log.v("GPS", str);
		if (str != null) {
			return str.contains("gps");
		} else {
			return false;
		}
	}

	/**
	 * 如果gps是开�?��就关�?如果是关�?��就打�? private static final int BUTTON_WIFI = 0;
	 * private static final int BUTTON_BRIGHTNESS = 1; private static final int
	 * BUTTON_SYNC = 2; private static final int BUTTON_GPS = 3; private static
	 * final int BUTTON_BLUETOOTH = 4;
	 * 
	 * @param contex
	 */
	public static void toggleGPS(Context contex) {
		Intent gpsIntent = new Intent();
		gpsIntent.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
		gpsIntent.addCategory("android.intent.category.ALTERNATIVE");
		gpsIntent.setData(Uri.parse("custom:3"));
		try {
			PendingIntent.getBroadcast(contex, 0, gpsIntent, 0).send();
		} catch (CanceledException e) {
			e.printStackTrace();
		}
	}

	public boolean isOpenGps(Context contex) {
		LocationManager manager = (LocationManager) contex.getSystemService(Context.LOCATION_SERVICE);
		return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}

	public static void closeGps(Context contex) {
		if (isGPSEnable(contex)) {
			toggleGPS(contex);
		}
	}

	/**
	 * android.permission.ACCESS_WIFI_STATE android.permission.CHANGE_WIFI_STATE
	 * android.permission.WAKE_LOCK �?��wifi�?��使用异步
	 */
	public static void toggleWifi(Context context) {
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

		if (wifiManager.isWifiEnabled()) {
			wifiManager.setWifiEnabled(false);
		} else {
			wifiManager.setWifiEnabled(true);
		}
	}

	public static void getScanWifiResults(Context context) {
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		List<ScanResult> wifiResults = wifiManager.getScanResults();
		for (ScanResult wifi : wifiResults) {
			LogUtil.log(TAG, Log.DEBUG, wifi.toString());
		}

		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

		LogUtil.log(TAG, Log.DEBUG, TelephonyManager.PHONE_TYPE_GSM + "----" + tm.getPhoneType());
		List<NeighboringCellInfo> cellResults = tm.getNeighboringCellInfo();
		for (NeighboringCellInfo cell : cellResults) {
			LogUtil.log(TAG, Log.DEBUG, cell.getCid() + "-" + cell.getLac() + "-" + cell.getRssi() + "-" + cell.getPsc() + "-" + cell.getNetworkType());
		}

		LogUtil.log(TAG, Log.DEBUG, getProviderName(context).getText());
	}

	public static boolean isNetworkProvider(Context context) {
		LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		return lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
	}

	public static boolean isGpsProvider(Context context) {
		LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}

}
