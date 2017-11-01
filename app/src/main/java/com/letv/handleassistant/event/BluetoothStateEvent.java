package com.letv.handleassistant.event;

/**
 * Created by xinshuai on 2016/11/12.
 */
public class BluetoothStateEvent {
    public boolean hasConnected;

    public BluetoothStateEvent(boolean hasConnected) {
        this.hasConnected = hasConnected;
    }
}
