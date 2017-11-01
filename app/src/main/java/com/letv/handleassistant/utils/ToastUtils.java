package com.letv.handleassistant.utils;


import android.content.Context;
import android.widget.Toast;

import com.letv.handleassistant.R;

public class ToastUtils {
	 
	public static void showToast(Context ct , int resId){
		Toast.makeText(ct, ct.getResources().getString(R.string.server_error), Toast.LENGTH_SHORT).show() ;
	}
	public static void showToast(Context ct , String value){
		Toast.makeText(ct, value, Toast.LENGTH_SHORT).show() ;
	}
}
