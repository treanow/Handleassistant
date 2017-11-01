package com.letv.handleassistant.ui.activity;

import android.view.View;
import android.widget.TextView;

import com.letv.handleassistant.R;
import com.letv.handleassistant.framework.activity.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 帮助页面
 * Created by xinshuai on 2016/11/2.
 */
public class HelpAct extends BaseActivity {

    @Bind(R.id.tv_go_buy)
    TextView tv_go_buy;

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_help);
        ButterKnife.bind(this);
        this.setMyTitle(this, "帮助", true, R.mipmap.arrow_back, false,0);
        dealBack(this);
    }

    @Override
    public void dealLogicBeforeInitView() {
        tv_go_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("打开浏览器");
            }
        });

    }

    @Override
    public void initView() {

    }

    @Override
    public void dealLogicAfterInitView() {

    }

    @Override
    public void onClickEvent(View view) {

    }

    @Override
    public void onNetChanged(boolean oldStatus, boolean newStatus) {

    }
}
