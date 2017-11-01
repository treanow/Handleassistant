package com.letv.handleassistant.framework.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.letv.handleassistant.R;


/**
 * 普通布局Act
 */
public class NormalAct extends BaseActivity {
    private int REQUEST_CARSETTING = 0;// 跳转车辆设置for result

    @Override
    public void setContentLayout() {
// 去标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.act_a_normal);
        this.setMyTitle(this, "乐视互动手柄", false, R.mipmap.arrow_back, false, 0);
        dealBack(mContext);
    }

    @Override
    public void dealLogicBeforeInitView() {
        registerListener();

    }

    private void registerListener() {
        //llLogout.setOnClickListener(this);
    }


    @Override
    public void initView() {
        //Fragment startActivityForResult
//        Intent intent = new Intent(act,MyWalletAct.class);
//        intent.putExtra("leftMoney", money);
//        ContentFragment.this.startActivityForResult(intent,REQUEST_MYWALLET);
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
//        if (requestCode == REQUEST_ADDCAR) {
//            getData();
//        } else if (requestCode == REQUEST_CARSETTING) {
//
//        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onNetChanged(boolean oldStatus, boolean newStatus) {

    }

}
