package com.letv.handleassistant.utils;


import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.letv.handleassistant.application.SoftApplication;
import com.letv.handleassistant.framework.activity.BaseActivity;
import com.letv.handleassistant.framework.spfs.SPHelper;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CommonUtil {


    public static String castDouble(String str) {
        String encStr = "";
        encStr = new DecimalFormat("0.00").format(Double.parseDouble(str));
        return encStr;
    }

    /**
     * 判断集合是否为空
     *
     * @param collection
     * @return
     */
    public static boolean isListNull(Collection collection) {
        if (collection == null || collection.size() == 0) {
            return true;
        } else {
            return false;
        }
    }


    /*
   * 判断服务是否启动,context上下文对象 ，className服务的name
   */
    public static boolean isServiceRunning(Context mContext, String className) {

        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager
                .getRunningServices(1000);

        if (!(serviceList.size() > 0)) {
            return false;
        }

        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

    /**
     * s ->   小时去掉,只展示 分钟和秒
     */
    public static String formatSecond(int second) {

        Integer s = (Integer) second;

        Integer hours = (int) (s / (60 * 60));
        Integer minutes = (int) (s / 60 - hours * 60);
        Integer seconds = (int) (s - minutes * 60 - hours * 60 * 60);
        if (minutes < 10 && seconds < 10)
            return "0" + minutes + ":0" + seconds;
        else if (minutes < 10 && seconds >= 10)
            return "0" + minutes + ":" + seconds;
        else if (minutes >= 10 && seconds < 10)
            return minutes + ":0" + seconds;
        else
            return minutes + ":" + seconds;

    }


    /**
     * 将车牌号中间4位加密
     * 京A00000
     *
     * @param str
     * @return
     */

    public static String castCarNum(String str) {
        String encStr = "";
        encStr = str.substring(0, 2) + "·" + str.substring(2, str.length());
        return encStr;
    }

    /**
     * 清空所有需要清空的sp
     */
    public static void cleanAllSP(Context mContext) {
    }

    /**
     * 获取 支付方式:0 支付宝 1微信 2网银 3一卡通 4现金 5电子账户支付
     * @param payType
     * @return
     */
    public static String getPayType(String payType) {
        String str;
        if("0".equals(payType))
            str ="支付宝";
        else if("1".equals(payType))
            str ="微信";
        else if("2".equals(payType))
            str ="银联";
        else if("3".equals(payType))
            str ="一卡通";
        else if("4".equals(payType))
            str ="现金";
        else if("5".equals(payType))
            str ="电子账户支付";
        else if("6".equals(payType))
            str ="免收";
        else if("7".equals(payType))
            str ="逃逸";
        else
            str ="获取支付方式异常";

        return str;
    }

    public static String getCarType(String vehicleTypeName) {
        switch (vehicleTypeName){
            case "小车型":
                return "1";
            case "中车型":
                return "2";
            case "大车型":
                return "3";
            default:
                return "1";
        }

    }


    // 隐藏显示密码
    public static class EditTextInputListener implements View.OnClickListener {

        private EditText etpsw;
        private boolean isShow;

        public EditTextInputListener(EditText etpsw, boolean isShow) {
            this.etpsw = etpsw;
            this.isShow = isShow;
        }

        @Override
        public void onClick(View v) {
            if (isShow) {
                // 显示密码
                etpsw.setInputType(1);
            } else {
                // 隐藏密码
                etpsw.setInputType(129);
            }
            isShow = !isShow;
        }

    }

    /**
     * 退出销毁对应所有 的activity
     */
    public static void clearActivity() {
        if (SoftApplication.softApplication != null
                && !isListNull(SoftApplication.softApplication.unDestroyActivityList)) {

            List<Activity> clearList = new ArrayList<Activity>();
            boolean isHas = false;
            for (Activity activity : SoftApplication.softApplication.unDestroyActivityList) {
                if (activity instanceof BaseActivity) {
                    isHas = true;
                    clearList.add(activity);
                }
            }
            if (isHas) {
                for (Activity activity : clearList) {
                    activity.finish();
                }
            }
        }
    }

    /**
     * 销毁对应activityTag的activity
     *
     * @param activityTag
     */
    public static void clearActivity(String activityTag) {
        if (SoftApplication.softApplication != null && !isListNull(SoftApplication.softApplication.unDestroyActivityList)) {

            List<Activity> clearList = new ArrayList<Activity>();
            boolean isHas = false;
            for (Activity activity : SoftApplication.softApplication.unDestroyActivityList) {
                if (activity instanceof BaseActivity && (activityTag).equals(((BaseActivity) activity).activityTag)) {
                    isHas = true;
                    clearList.add(activity);
                }
            }
            if (isHas) {
                for (Activity activity : clearList) {
                    activity.finish();
                }
            }
        }
    }



    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     * @date 2013年7月23日
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     * @date 2013年7月23日
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }


    /**
     * 银行卡四位加空格
     *
     * @param mEditText
     */
    public static void bankCardNumAddSpace(final EditText mEditText) {
        mEditText.addTextChangedListener(new TextWatcher() {
            int beforeTextLength = 0;
            int onTextLength = 0;
            boolean isChanged = false;

            int location = 0;// 记录光标的位置  
            private char[] tempChar;
            private StringBuffer buffer = new StringBuffer();
            int konggeNumberB = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                beforeTextLength = s.length();
                if (buffer.length() > 0) {
                    buffer.delete(0, buffer.length());
                }
                konggeNumberB = 0;
                for (int i = 0; i < s.length(); i++) {
                    if (s.charAt(i) == ' ') {
                        konggeNumberB++;
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                onTextLength = s.length();
                buffer.append(s.toString());
                if (onTextLength == beforeTextLength || onTextLength <= 3
                        || isChanged) {
                    isChanged = false;
                    return;
                }
                isChanged = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isChanged) {
                    location = mEditText.getSelectionEnd();
                    int index = 0;
                    while (index < buffer.length()) {
                        if (buffer.charAt(index) == ' ') {
                            buffer.deleteCharAt(index);
                        } else {
                            index++;
                        }
                    }

                    index = 0;
                    int konggeNumberC = 0;
                    while (index < buffer.length()) {
                        if ((index == 4 || index == 9 || index == 14 || index == 19)) {
                            buffer.insert(index, ' ');
                            konggeNumberC++;
                        }
                        index++;
                    }

                    if (konggeNumberC > konggeNumberB) {
                        location += (konggeNumberC - konggeNumberB);
                    }

                    tempChar = new char[buffer.length()];
                    buffer.getChars(0, buffer.length(), tempChar, 0);
                    String str = buffer.toString();
                    if (location > str.length()) {
                        location = str.length();
                    } else if (location < 0) {
                        location = 0;
                    }

                    mEditText.setText(str);
                    Editable etable = mEditText.getText();
                    Selection.setSelection(etable, location);
                    isChanged = false;
                }
            }
        });
    }



    public static void notifySystemGallary(Activity activity) {
        if (activity == null) {
            return;
        }
        try {
            activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private static void showToast(Context context, String str, int duration) {
        if (context != null && !TextUtils.isEmpty(str)) {
            Toast toast = Toast.makeText(context, str, duration);
            toast.show();
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }


    public static void upKeyBoard(final EditText et) {

        et.requestFocus();
        //调出键盘
        et.setFocusableInTouchMode(true);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

                           public void run() {
                               InputMethodManager inputManager =
                                       (InputMethodManager) et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                               inputManager.showSoftInput(et, 0);
                           }

                       },
                100);
    }

}
