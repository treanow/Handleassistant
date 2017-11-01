package com.letv.handleassistant.framework.network;

import android.content.Context;
import android.content.Intent;

import com.letv.handleassistant.R;
import com.letv.handleassistant.framework.bean.BaseResponse;
import com.letv.handleassistant.utils.ToastUtils;


public abstract class OnCompleteListener<T extends BaseResponse> {

    private Context ct;

    public OnCompleteListener(Context ct) {
        this.ct = ct;
    }

    /**
     * 请求完
     *
     * @param resultString
     */
    public void onCompleted(T result, String resultString) {

    }

    /**
     * 请求成功
     *
     * @param result
     * @param resultString
     */
    public abstract void onSuccessed(T result, String resultString);

    /**
     * code不为0
     */
    public void onCodeError(T result) {
            ToastUtils.showToast(ct, result.msg);
    }

    /**
     * 请求失败
     */
    public void onPostFail() {
        ToastUtils.showToast(ct, R.string.server_error);
    }


}