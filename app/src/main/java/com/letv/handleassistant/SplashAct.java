package com.letv.handleassistant;

import android.content.Intent;
import android.view.View;
import android.view.Window;

import com.letv.handleassistant.constant.Constant;
import com.letv.handleassistant.framework.activity.BaseActivity;
import com.letv.handleassistant.framework.spfs.SPHelper;
import com.letv.handleassistant.service.BluetoothService;
import com.letv.handleassistant.utils.CommonUtil;
import com.letv.handleassistant.utils.LogUtil;

import java.io.File;


/**
 * SplashAct
 */
public class SplashAct extends BaseActivity {
    private int REQUEST_CARSETTING = 0;// 跳转车辆设置for result

    @Override
    public void setContentLayout() {
        setFullScreen(true);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.act_splash);
    }

    @Override
    public void dealLogicBeforeInitView() {
        File fileDir = new File(Constant.imageDir);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        //蓝牙服务
        //判断服务是否启动
        boolean isRunning = CommonUtil.isServiceRunning(SplashAct.this, "com.letv.handleassistant.service.BluetoothService");
        if (!isRunning) {
            LogUtil.log("BluetoothService没运行");
            Intent uploadService = new Intent(mContext, BluetoothService.class);
            startService(uploadService);
        } else
            LogUtil.log("BluetoothService运行中");
    }



    @Override
    public void initView() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {

                }

                // 判断去哪个页面
                whereShouldWeGo();
            }
        }).start();
    }

    private void whereShouldWeGo() {
        startActivity(new Intent(SplashAct.this, MainActivity.class));
        finish();
    }

    @Override
    public void dealLogicAfterInitView() {

    }

    @Override
    public void onClickEvent(View view) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onNetChanged(boolean oldStatus, boolean newStatus) {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
