package com.letv.handleassistant.constant;


import android.os.Environment;


public class Constant {
    /**
     * App信息的文件名
     */
    public static final String APP_CONFIG_FILE_NAME = "AppConfig.json";
    public static final int ERROR_CODE_OK = 0;
    public static final int TIMEOUT_SECOND = 20;//*20s
    public static final int TIMEOUT_MIN = 1;//几分钟
    public static final String CODE_ERROR = "ERROR";
    public static final String ERROR_SERVER = "服务器错误";
    public static final String ERROR_NETWORK = "网络异常";
    public static final String ERROR_DATA = "数据异常";
    public static final String APP_ID = "wxe9f5c9aef873cbfa";//微信支付appid
    public static final String PAGE_SIZE = "10";//10条
    public static final int TEMP_PIC_NUM = 5;//
    public static final int GET_CODE_TIME = 60;//
    public static final int LEFT_JS_CODE = -1;//
    public static final int RIGHT_JS_CODE = -2;//

    public static String NO_PHONE = "请输入手机号";
    public static String ERROR_PHONE = "请输入正确的手机号";
    public static String NO_NAME = "请输入用户名";
    public static String NO_CODE = "请输入验证码";
    public static String NO_PSW = "密码不能为空";
    public static String ERROR_PSW = "密码由8-18位字母数字或特殊字符组成";
    public static String NO_SAME_PSW = "两次输入密码不同";
    public static String ERROR_NAME = "用户名由字母开头，6-20位 字母、数字和下划线组成 ";
    public static String NO_OLD_PSW = "旧密码不能为空";
    public static String NO_NEW_PSW = "新密码不能为空";
    public static String NO_RESET_PSW = "请输入重置密码";
    public static String HAS_SENDED = "验证码已发送";
    public static String ERROR_SYS = "连接服务器异常，请稍后再试";
    public static String NO_AUTH = "请先进行实名认证";
    public static String REGIST_SUC = "注册成功";
    public static String TYPE = "type";
    public static String NODATA = "未查询到数据";
    public static String AUTO_USERNAME = "回显用户名";
    public static String SELECT_PARKLOT = "选择车场";
    public static String SELECT_JOB = "选择岗位";
    public static String ERROR_TIME = "出场时间有误";
    public static String DEVICE_PRE = "Newgamepad";//蓝牙连接手柄设备前缀


    /**
     * 保存图片的目录
     */
    public static String imageDir = Environment
            .getExternalStorageDirectory().getAbsolutePath() + "/handleassistant/picture/";
    /**
     * 获取图片前缀
     */
//    public static final String PIC_PRE = SoftApplication.softApplication.getAppInfo().serverAddress + "/kjt-consumer/api/common/getFile.do?filename=";
    /**
     * 相册
     */
    public static final String FILE_PATH_BASE = Environment
            .getExternalStorageDirectory().getAbsolutePath() + "/handleassistant/picture";
    public static final String FILE_PATH_TEMP = FILE_PATH_BASE + "/temp";
}
