package com.letv.handleassistant.ui.activity;

import android.graphics.Rect;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.letv.handleassistant.R;
import com.letv.handleassistant.framework.activity.BaseActivity;
import com.letv.handleassistant.framework.spfs.SPHelper;
import com.letv.handleassistant.widget.TouchDebugView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 已经连接  跳转到 测试按键映射act
 * Created by xinshuai on 2016/11/5.
 */
public class BtnMappingAct extends BaseActivity {
    @Bind(R.id.tv_p_btn_num)
    TextView tvPBtnNum;
    @Bind(R.id.tv_dx)
    TextView tvDx;
    @Bind(R.id.tv_dy)
    TextView tvDy;
    @Bind(R.id.tv_Xv)
    TextView tvXv;
    @Bind(R.id.tv_Yv)
    TextView tvYv;
    @Bind(R.id.tv_prs)
    TextView tvPrs;
    @Bind(R.id.tv_size)
    TextView tvSize;
    @Bind(R.id.iv_btn_l2)
    ImageView ivBtnL2;
    @Bind(R.id.iv_btn_l1)
    ImageView ivBtnL1;
    @Bind(R.id.view_left)
    View viewLeft;
    @Bind(R.id.iv_btn_up)
    ImageView ivBtnUp;
    @Bind(R.id.iv_btn_down)
    ImageView ivBtnDown;
    @Bind(R.id.iv_btn_left)
    ImageView ivBtnLeft;
    @Bind(R.id.iv_btn_right)
    ImageView ivBtnRight;
    @Bind(R.id.view_right)
    View viewRight;
    @Bind(R.id.iv_btn_y)
    ImageView ivBtnY;
    @Bind(R.id.iv_btn_a)
    ImageView ivBtnA;
    @Bind(R.id.iv_btn_x)
    ImageView ivBtnX;
    @Bind(R.id.iv_btn_b)
    ImageView ivBtnB;
    @Bind(R.id.iv_joystick_left)
    ImageView ivJoystickLeft;
    @Bind(R.id.tv_left_center)
    TextView tvLeftCenter;
    @Bind(R.id.tv_left_up)
    TextView tvLeftUp;
    @Bind(R.id.tv_left_down)
    TextView tvLeftDown;
    @Bind(R.id.tv_left_left)
    TextView tvLeftLeft;
    @Bind(R.id.tv_left_right)
    TextView tvLeftRight;
    @Bind(R.id.iv_joystick_right)
    ImageView ivJoystickRight;
    @Bind(R.id.tv_right_center)
    TextView tvRightCenter;
    @Bind(R.id.tv_right_up)
    TextView tvRightUp;
    @Bind(R.id.tv_right_down)
    TextView tvRightDown;
    @Bind(R.id.tv_right_left)
    TextView tvRightLeft;
    @Bind(R.id.tv_right_right)
    TextView tvRightRight;
    @Bind(R.id.iv_btn_back)
    ImageView ivBtnBack;
    @Bind(R.id.iv_btn_start)
    ImageView ivBtnStart;
    @Bind(R.id.tv_is_test)
    TextView tvIsTest;
    @Bind(R.id.tv_progress_l2)
    TextView tvProgressL2;
    @Bind(R.id.tv_progress_r2)
    TextView tvProgressR2;
    @Bind(R.id.sv_touch)
    TouchDebugView svTouch;
    @Bind(R.id.progressbar_l2)
    ProgressBar progressbar_l2;
    @Bind(R.id.progressbar_r2)
    ProgressBar progressbar_r2;

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_btn_mapping);
        ButterKnife.bind(this);
    }

    //建议在这取坐标值
    //x+图片高度/2     y-状态栏高度+图片高度/2
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //状态栏高度
        Rect frame = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        //存坐标
        int[] location = new int[2];
        ivBtnX.getLocationInWindow(location);//相对于屏幕的绝对坐标(画布是相对于View的坐标)
        SPHelper.getInstance().setBtnX(location[0] + ivBtnX.getWidth() / 2,
                location[1] + ivBtnX.getWidth() / 2 - statusBarHeight);
        ivBtnY.getLocationInWindow(location);
        SPHelper.getInstance().setBtnY(location[0] + ivBtnY.getWidth() / 2,
                location[1] + ivBtnY.getWidth() / 2 - statusBarHeight);

        //左摇杆
        ivJoystickLeft.getLocationInWindow(location);
        SPHelper.getInstance().setLeftJS(location[0] + ivJoystickLeft.getWidth() / 2,
                location[1] + ivJoystickLeft.getWidth() / 2 - statusBarHeight);
        SPHelper.getInstance().setLeftJSWidth(ivJoystickLeft.getWidth());//115 618 169
        //右摇杆
        ivJoystickRight.getLocationInWindow(location);
        SPHelper.getInstance().setRightJS(location[0] + ivJoystickRight.getWidth() / 2,
                location[1] + ivJoystickRight.getWidth() / 2 - statusBarHeight);
        SPHelper.getInstance().setRightJSWidth(ivJoystickRight.getWidth());//

    }

    @Override
    public void dealLogicBeforeInitView() {
        tvIsTest.setOnClickListener(this);
    }

    @Override
    public void initView() {
        svTouch.ivBtnX = ivBtnX;
        svTouch.ivBtnY = ivBtnY;
        svTouch.progressbar_l2 = progressbar_l2;
        svTouch.progressbar_r2 = progressbar_r2;
        svTouch.tvProgressL2 = tvProgressL2;
        svTouch.tvProgressR2 = tvProgressR2;
    }

    @Override
    public void dealLogicAfterInitView() {

    }

    private boolean tv_is_test = false;

    @Override
    public void onClickEvent(View view) {
        switch (view.getId()) {
            case R.id.tv_is_test:
                if (!tv_is_test)
                    tvIsTest.setText("取消");
                else {
                    tvIsTest.setText("测试");
                    ivBtnX.setVisibility(View.VISIBLE);
                    ivBtnY.setVisibility(View.VISIBLE);
                }
                tv_is_test = !tv_is_test;
                svTouch.isTest = tv_is_test;

                break;

            default:
                break;
        }


    }

    @Override
    public void onNetChanged(boolean oldStatus, boolean newStatus) {

    }


}
