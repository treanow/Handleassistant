package com.letv.handleassistant.framework.manager;

import java.util.ArrayList;

import android.content.Context;

import com.letv.handleassistant.utils.LogUtil;


public class NetChangeManager {
	
	private static NetChangeManager nwm = null ;
	private Context ct;
	private ArrayList<INetChangedListener> mListeners = new ArrayList<INetChangedListener>();
	
	private NetChangeManager(Context ct){
		this.ct = ct ; 
	}
	
	public synchronized static NetChangeManager newInstance(Context ct){
		if(nwm==null){
			nwm = new NetChangeManager(ct);
		}
		return nwm ; 
	}
	
	/**
	 * 添加监听
	 */
	public void addMinitor(INetChangedListener listener){
		synchronized (mListeners) {
			if(mListeners.contains(listener)){
				LogUtil.log("listener has exist");
				return ; 
			}
			mListeners.add(listener);
		}
	}
	
	/**
	 * 移除监听
	 */
	public void removeMinitor(INetChangedListener listener){
		mListeners.remove(listener);
	}
	
	/**
	 * 通知监听
	 */
	public void notifyNetChange(boolean oldStatus , boolean newStatus) {
        synchronized (mListeners) {
            for(int i = 0; i < mListeners.size(); i++) {
            	mListeners.get(i).onNetChanged(oldStatus, newStatus);
            }
        }
    }
	
}
