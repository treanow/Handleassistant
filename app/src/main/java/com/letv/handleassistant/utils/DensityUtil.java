package com.letv.handleassistant.utils;


import android.content.Context;

import com.letv.handleassistant.application.SoftApplication;

public class DensityUtil {

	/**
	 * 根据手机的分辨率�?dp 的单�?转成�?px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
	public static int dp2px(double dpValue) {
		return (int) Math.ceil(dpValue * SoftApplication.softApplication.getApplicationContext().getResources().getDisplayMetrics().density);
	}

	/**
	 * 根据手机的分辨率�?px(像素) 的单�?转成�?dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 获取屏幕宽度
	 * 
	 * @date 2013年7月23日
	 * @param context
	 * @return
	 */
	public static int getWidth(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}

	/**
	 * 获取屏幕高度
	 * 
	 * @date 2013年7月23日
	 * @param context
	 * @return
	 */
	public static int getHeight(Context context) {
		return context.getResources().getDisplayMetrics().heightPixels;
	}
	
	public static int sp2px(Context context, float spValue) {
    	final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;  
    	  return (int) (spValue * fontScale + 0.5f);
    }
}