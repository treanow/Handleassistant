package com.letv.handleassistant.framework.activity;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.letv.handleassistant.R;
import com.letv.handleassistant.application.SoftApplication;
import com.letv.handleassistant.framework.manager.INetChangedListener;
import com.letv.handleassistant.framework.manager.NetChangeManager;
import com.letv.handleassistant.utils.DialogUtil;
import com.letv.handleassistant.utils.StringUtil;

public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener,INetChangedListener {

    protected SoftApplication softApplication;
    public boolean isAllowFullScreen;// 是否允许全屏
    public boolean hasMenu;// 是否有菜单显示
    private ProgressDialog progressDialog;
    private Dialog mDialog;
    protected Resources resources;
    protected Activity mContext;
    public String activityTag;//每个activity的标记
    private boolean isActive = true;
    private long timeDiffer = 300000;// 默认是5分钟

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        resources = getResources();
        softApplication = (SoftApplication) getApplicationContext();
        SoftApplication.unDestroyActivityList.add(this);

        if (isAllowFullScreen) {
            setFullScreen(true);
        } else {
            setFullScreen(false);
        }

        setContentLayout();
        dealLogicBeforeInitView();
        initView();
        dealLogicAfterInitView();
    }

    /**
     * 设置布局文件
     */
    public abstract void setContentLayout();

    /**
     * 在实例化布局之前处理的逻辑
     */
    public abstract void dealLogicBeforeInitView();

    /**
     * 实例化布局文件/组件
     */
    public abstract void initView();

    /**
     * 在实例化布局之后处理的逻辑
     */
    public abstract void dealLogicAfterInitView();

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
//    /**
//     * 普通联网获取数据
//     *
//     * @param <T>
//     * @param request
//     * @param onCompleteListener
//     */
//    @SuppressWarnings("rawtypes")
//    public <T extends BaseResponse> void getNetWorkDate(Request request, BaseParser<T> parser, OnCompleteListener<T> onCompleteListener) {
//        if (NetUtil.isNetDeviceAvailable(softApplication)) {
//            softApplication.requestNetWork(request, onCompleteListener, parser);
//        } else {
//            showToast(R.string.network_is_not_available);
//            dismissProgressDialog();
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SoftApplication.unDestroyActivityList.remove(this);
        NetChangeManager.newInstance(softApplication).removeMinitor(this);
        //旺pos
//        WeiposImpl.as().destroy();
    }



    /**
     * 是否全屏和显示标题，true为全屏和无标题，false为无标题，请在setContentView()方法前调用
     *
     * @param fullScreen
     */
    public void setFullScreen(boolean fullScreen) {
        if (fullScreen) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }

    }

    /**
     * 短时间显示Toast
     *
     * @param info 显示的内容
     */
    public void showToast(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param info 显示的内容
     */
    public void showToastLong(String info) {
        Toast.makeText(this, info, Toast.LENGTH_LONG).show();
    }

    /**
     * 短时间显示Toast
     * <p/>
     * 显示的内容
     */
    public void showToast(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     * <p/>
     * 显示的内容
     */
    public void showToastLong(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_LONG).show();
    }

    /**
     * onClick方法的封装，在此方法中处理点击
     *
     * @param view 被点击的View对象
     */
    abstract public void onClickEvent(View view);



    @Override
    public void onClick(View v) {
        onClickEvent(v);
    }

    /**
     * 显示正在加载的进度条
     */
    public void showProgressDialog() {
        DialogUtil.showLoadingDialog(this);
    }

    /**
     * 隐藏正在加载的进度条
     */
    public void dismissProgressDialog() {
        DialogUtil.dismissDialog();
    }

    public void showProgressDialog(String msg) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        progressDialog = new ProgressDialog(BaseActivity.this);
        progressDialog.setMessage(msg);
        try {
            progressDialog.show();
        } catch (WindowManager.BadTokenException exception) {
            exception.printStackTrace();
        }
    }




    /**
     * 设置标题
     */
    public void showTitle(Activity curActivity, String title) {
        TextView tv_title = (TextView) curActivity
                .findViewById(R.id.tv_title_bar_title);
        if (null != tv_title && !StringUtil.isNullOrEmpty(title)) {
            tv_title.setText(title);
        }
    }

    /**
     * 设置标题
     *
     * @param curActivity
     * @param title
     * @param isShowLeft   是否显示标题左侧图标
     * @param leftImageId  左侧图片id，没有为0
     * @param isShowRight  是否显示标题右侧图片
     * @param rightImageId 右侧图片id，没有为0
     */
    public void setMyTitle(Activity curActivity, String title,
                           Boolean isShowLeft, int leftImageId, Boolean isShowRight,
                           int rightImageId) {
        TextView tv_title = (TextView) curActivity
                .findViewById(R.id.tv_title_bar_title);
        ImageView iv_left = (ImageView) curActivity
                .findViewById(R.id.iv_title_bar_left);
        ImageView iv_right = (ImageView) curActivity
                .findViewById(R.id.iv_title_bar_right);
        if (null != tv_title && !StringUtil.isNullOrEmpty(title)) {
            tv_title.setText(title);
        }

        if (null != iv_left) {
            if (isShowLeft) {
                iv_left.setVisibility(View.VISIBLE);
                if (leftImageId != 0) {
                    iv_left.setImageResource(leftImageId);
                }
            } else {
                iv_right.setVisibility(View.GONE);
            }
        }

        if (null != iv_right) {
            if (isShowRight) {
                iv_right.setVisibility(View.VISIBLE);
                if (rightImageId != 0) {
                    iv_right.setImageResource(rightImageId);
                }
            } else {
                iv_right.setVisibility(View.GONE);
            }
        }
    }


    /**
     * 处理返回键
     *
     * @param curActivity
     */
    public void dealBack(final Activity curActivity) {
        View view = curActivity.findViewById(R.id.ll_title_bar_left);
        if (view != null) {
            view.setVisibility(View.VISIBLE);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissSoftKeyboard(curActivity);
                    curActivity.finish();
                }
            });
        }

    }

    /**
     * 处理弹出的输入法
     */
    public void dismissSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManage = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManage.hideSoftInputFromWindow(activity
                            .getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





}
