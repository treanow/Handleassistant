

package com.letv.handleassistant.receiver;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.letv.handleassistant.constant.Constant;
import com.letv.handleassistant.event.BluetoothStateEvent;
import com.letv.handleassistant.framework.spfs.SPHelper;
import com.letv.handleassistant.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;

public class BluetoothReceiver extends BroadcastReceiver {

    private String btMessage = "没收到广播";
    //监听蓝牙状态

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
        String name = device.getName()==null?"":device.getName();
        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
            btMessage = name + "设备已发现！！";
        } else if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
            btMessage = name + "设备已连接！！";
            if (name.startsWith(Constant.DEVICE_PRE)){
                LogUtil.log("通知界面 Newgamepad 设备蓝牙已连接");
                EventBus.getDefault().post(new BluetoothStateEvent(true));
                SPHelper.getInstance().setHasConnected(true);
            }
        } else if (BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED.equals(action)) {
            btMessage = name + "正在断开蓝牙连接。。。";
        } else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
            btMessage = name + "蓝牙连接已断开！！";
            if (name.startsWith(Constant.DEVICE_PRE)){
                LogUtil.log("通知界面 Newgamepad 设备蓝牙已断开");
                EventBus.getDefault().post(new BluetoothStateEvent(false));
                SPHelper.getInstance().setHasConnected(false);
            }
        }
        LogUtil.log(btMessage);

    }

}
