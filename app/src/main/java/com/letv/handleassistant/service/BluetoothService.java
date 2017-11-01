package com.letv.handleassistant.service;

import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.letv.handleassistant.receiver.BluetoothReceiver;
import com.letv.handleassistant.utils.LogUtil;

/**
 * Created by xinshuai on 2016/11/12.
 */
public class BluetoothService extends Service{

    private BluetoothReceiver receiver;
    //服务被第一次创建的时候调用
    @Override
    public void onCreate() {
        LogUtil.log("服务---onCreate");
        //1.创建广播接收者对象
        receiver = new BluetoothReceiver();
        //2.创建intent-filter对象
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        //3.注册广播接收者
        registerReceiver(receiver, filter);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        LogUtil.log("服务onDestroy");
        //解除注册
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
