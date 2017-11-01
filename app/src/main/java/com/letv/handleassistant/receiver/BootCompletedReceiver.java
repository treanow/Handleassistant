package com.letv.handleassistant.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.letv.handleassistant.framework.spfs.SPHelper;
import com.letv.handleassistant.service.BluetoothService;
import com.letv.handleassistant.utils.LogUtil;

/**
 * Created by xinshuai on 2016/11/14.
 */
public class BootCompletedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //开机了  未连接
        SPHelper.getInstance().setHasConnected(false);
        context.startService(new Intent(context, BluetoothService.class));
    }
}
