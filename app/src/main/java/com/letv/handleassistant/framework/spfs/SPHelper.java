package com.letv.handleassistant.framework.spfs;


import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;

import com.letv.handleassistant.application.SoftApplication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;



public class SPHelper {
    /**
     * SharedPreferences的名字
     */
    private static final String SP_FILE_NAME = "APPLICATION_SP";
    private static SPHelper sharedPrefHelper = null;
    private static SharedPreferences sharedPreferences;

    public static synchronized SPHelper getInstance() {
        if (null == sharedPrefHelper) {
            sharedPrefHelper = new SPHelper();
        }
        return sharedPrefHelper;
    }

    private SPHelper() {
        sharedPreferences = SoftApplication.softApplication.getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
    }

    //存按键x坐标
    public void setBtnX(int x,int y) {
        sharedPreferences.edit().putInt("BtnXx", x).commit();
        sharedPreferences.edit().putInt("BtnXy", y).commit();
    }
    public  int getBtnXx() {
        return sharedPreferences.getInt("BtnXx", 0);
    }
    public  int getBtnXy() {
        return sharedPreferences.getInt("BtnXy", 0);
    }
    //存按键y坐标
    public void setBtnY(int x,int y) {
        sharedPreferences.edit().putInt("BtnYx", x).commit();
        sharedPreferences.edit().putInt("BtnYy", y).commit();
    }
    public  int getBtnYx() {
        return sharedPreferences.getInt("BtnYx", 0);
    }
    public  int getBtnYy() {
        return sharedPreferences.getInt("BtnYy", 0);
    }
    //存左摇杆坐标 的中心坐标 和 半径 start

    public void setLeftJS(int x, int y) {
        sharedPreferences.edit().putInt("LeftJSx", x).commit();
        sharedPreferences.edit().putInt("LeftJSy", y).commit();
    }
    public  int getLeftJSx() {
        return sharedPreferences.getInt("LeftJSx", 0);
    }
    public  int getLeftJSy() {
        return sharedPreferences.getInt("LeftJSy", 0);
    }
    public void setLeftJSWidth(int x) {
        sharedPreferences.edit().putInt("LeftJSWidth", x).commit();
    }
    public  int getLeftJSWidth() {
        return sharedPreferences.getInt("LeftJSWidth", 0);
    }

    //存左摇杆坐标 的中心坐标 和 半径 end

    //存Right摇杆坐标 的中心坐标 和 半径 start
    public void setRightJS(int x, int y) {
        sharedPreferences.edit().putInt("RightJSx", x).commit();
        sharedPreferences.edit().putInt("RightJSy", y).commit();
    }
    public  int getRightJSx() {
        return sharedPreferences.getInt("RightJSx", 0);
    }
    public  int getRightJSy() {
        return sharedPreferences.getInt("RightJSy", 0);
    }
    public void setRightJSWidth(int x) {
        sharedPreferences.edit().putInt("RightJSWidth", x).commit();
    }
    public  int getRightJSWidth() {
        return sharedPreferences.getInt("RightJSWidth", 0);
    }
    //存Right摇杆坐标 的中心坐标 和 半径 end

    /**
     * 是否登录状态
     *
     * @param hasLogin
     */
    public void setHasConnected(boolean hasLogin) {
        sharedPreferences.edit().putBoolean("hasConnected", hasLogin).commit();
    }

    public boolean getConnected() {
        return sharedPreferences.getBoolean("hasConnected", false);
    }

    /**
     * 是否第一次
     *
     * @param isFirst
     */
    public void setIsFirst(boolean isFirst) {
        sharedPreferences.edit().putBoolean("isFirst", isFirst).commit();
    }

    public boolean getIsFirst() {
        return sharedPreferences.getBoolean("isFirst", true);
    }
/**
 * -----------------------------------------------------------------
 */
    /**
     * 清空所有的sp
     */
    public void clearAllSp() {
        sharedPreferences.edit().clear().commit();
    }


    /**
     * 将对象进行base64编码后保存到SharePref中
     *
     * @param context
     * @param key
     * @param object  必须Serializable化
     */
    public  void saveObj(Context context, String key, Object object) {

//        key = key + "_" + SPHelper.getInstance().getUid();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            // 将对象的转为base64码
            String objBase64 = new String(Base64.encode(baos.toByteArray(),
                    Base64.DEFAULT));

            sharedPreferences.edit().putString(key, objBase64).commit();
            oos.close();
        } catch (IOException e) {
            // NotSerializableException
            e.printStackTrace();
        }
    }

    /**
     * 将SharePref中经过base64编码的对象读取出来
     *
     * @param context
     * @param key
     * @return
     */
    public   Object getObj(Context context, String key) {
//        key = key + "_" + SPHelper.getInstance().getUid();
        String objBase64 = sharedPreferences.getString(key, null);
        if (TextUtils.isEmpty(objBase64))
            return null;

        // 对Base64格式的字符串进行解码
        byte[] base64Bytes = Base64
                .decode(objBase64.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);

        ObjectInputStream ois;
        Object obj = null;
        try {
            ois = new ObjectInputStream(bais);
            obj = ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (TextUtils.isEmpty(obj.toString())) {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return obj;
    }


}
