package com.letv.handleassistant.framework.fragment;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.BadTokenException;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.letv.handleassistant.application.SoftApplication;
import com.letv.handleassistant.utils.DialogUtil;


public abstract class BaseFragment extends Fragment implements OnClickListener {

    protected Context mContext;
    protected Activity baseFragmentActivity;
    protected View view;
    private ProgressDialog progressDialog;
    private InputMethodManager manager;
    private Dialog mDialog;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = getActivity();
        this.baseFragmentActivity = activity;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = initView(inflater);
        if (view != null)
            initData(savedInstanceState);
        return view;
    }

    /**
     * 初始化布局
     */
    public abstract View initView(LayoutInflater inflater);

    public abstract void initData(Bundle savedInstanceState);

    protected abstract void onclickEvent(View view);

//    /**
//     * 普通联网获取数据
//     *
//     * @param <T>
//     * @param request
//     * @param onCompleteListener
//     */
//    @SuppressWarnings("rawtypes")
//    public <T extends BaseResponse> void getNetWorkDate(Request request, BaseParser<T> parser, OnCompleteListener<T> onCompleteListener) {
//        if (NetUtil.isNetDeviceAvailable(SoftApplication.softApplication)) {
//            SoftApplication.softApplication.requestNetWork(request, onCompleteListener, parser);
//        } else {
//            showToast(R.string.network_is_not_available);
//            dismissProgressDialog();
//        }
//    }


    /**
     * 短时间显示Toast
     *
     * @param info 显示的内容
     */
    public void showToast(String info) {
        Toast.makeText(SoftApplication.softApplication, info,
                Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param info 显示的内容
     */
    public void showToastLong(String info) {
        Toast.makeText(SoftApplication.softApplication, info, Toast.LENGTH_LONG)
                .show();
    }

    /**
     * 短时间显示Toast
     *
     * @param resId 显示的内容
     */
    public void showToast(int resId) {
        Toast.makeText(SoftApplication.softApplication, resId,
                Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param resId 显示的内容
     */
    public void showToastLong(int resId) {
        Toast.makeText(SoftApplication.softApplication, resId,
                Toast.LENGTH_LONG).show();
    }



    @Override
    public void onClick(View v) {
        onclickEvent(v);
    }


    /**
     * 显示正在加载的进度条
     */
    public void showProgressDialog() {
        DialogUtil.showLoadingDialog(baseFragmentActivity);
    }

    public void showProgressDialog(String msg) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(msg);
        try {
            progressDialog.show();
        } catch (BadTokenException exception) {
            exception.printStackTrace();
        }
    }


    /**
     * 隐藏正在加载的进度条
     */
    public void dismissProgressDialog() {
        DialogUtil.dismissDialog();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        hideKeyboard();
    }

    /**
     * 隐藏软键盘
     */
    public void hideKeyboard() {
        manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getActivity().getWindow().getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(getActivity().getWindow().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }



}
