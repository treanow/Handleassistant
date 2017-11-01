package com.letv.handleassistant.ui.activity;

import android.view.View;

import com.letv.handleassistant.R;
import com.letv.handleassistant.framework.activity.BaseActivity;

import butterknife.ButterKnife;

/**
 * 免root触屏映射说明act
 * Created by xinshuai on 2016/11/3.
 */
public class NoRootMappingAct extends BaseActivity{
    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_no_root_mapping);
        ButterKnife.bind(this);
        this.setMyTitle(this, "免Root触屏映射说明", true, R.mipmap.arrow_back, false,0);
        dealBack(this);
    }

    @Override
    public void dealLogicBeforeInitView() {

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
