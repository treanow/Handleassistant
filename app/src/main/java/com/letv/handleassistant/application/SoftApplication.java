package com.letv.handleassistant.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;


import com.letv.handleassistant.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;



public class SoftApplication extends Application {
    /**
     * 存放活动状态的(未被销毁)的Activity列表
     */
    public static List<Activity> unDestroyActivityList = new ArrayList<Activity>();
    public static SoftApplication softApplication;


    @Override
    public void onCreate() {
        softApplication = this;

        super.onCreate();
        LogUtil.log("softApplication onCreate");
    }



    /**
     * 得到系统的版本号
     *
     * @return
     */
    public String getOSVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 得到应用的版本号
     *
     * @return
     */
    public int getAppVersionCode() {
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo;
        int versionCode = 0;
        try {
            packInfo = packageManager.getPackageInfo(getPackageName(), 0);
            versionCode = packInfo.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 得到应用的版本号
     *
     * @return
     */
    public String getAppVersionName() {
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo;
        String versionCode = "";
        try {
            packInfo = packageManager.getPackageInfo(getPackageName(), 0);
            versionCode = packInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }



}
