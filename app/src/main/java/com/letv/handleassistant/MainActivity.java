package com.letv.handleassistant;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.letv.handleassistant.constant.Constant;
import com.letv.handleassistant.event.BluetoothStateEvent;
import com.letv.handleassistant.framework.activity.BaseActivity;
import com.letv.handleassistant.framework.spfs.SPHelper;
import com.letv.handleassistant.ui.activity.BtnMappingAct;
import com.letv.handleassistant.ui.activity.HelpAct;
import com.letv.handleassistant.ui.activity.NoRootMappingAct;
import com.letv.handleassistant.utils.LogUtil;
import com.letv.handleassistant.utils.SkipUtils;
import com.letv.handleassistant.utils.StringUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Iterator;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    @Bind(R.id.rl_title_bar_right)
    RelativeLayout rl_title_bar_right;
    @Bind(R.id.tv_bt_state)
    TextView tv_bt_state;//手柄连接状态
    @Bind(R.id.iv_mapping_state)
    ImageView iv_mapping_state;//映射开启状态
    @Bind(R.id.tv_start)
    TextView tv_start;//映射开启状态
    private boolean isDeviceOn;
    private boolean hasConnected = SPHelper.getInstance().getConnected();//是否连接上


    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_main_size);
        ButterKnife.bind(this);
        this.setMyTitle(this, "乐视互娱手柄", false, R.mipmap.arrow_back, true, R.mipmap.ic_launcher);
        EventBus.getDefault().register(this);
    }


    @Subscribe
    public void onEventMainThread(BluetoothStateEvent event) {
        hasConnected = event.hasConnected;
        SPHelper.getInstance().setHasConnected(hasConnected);
        if (isDeviceOn && hasConnected)
            tv_bt_state.setText("已连接");
        else
            tv_bt_state.setText("未连接");
    }

    @Override
    protected void onResume() {
        isDeviceOn= isDeviceOn();
        if (isDeviceOn && hasConnected)
            tv_bt_state.setText("已连接");
        else
            tv_bt_state.setText("未连接");
        super.onResume();
    }

    @Override
    public void dealLogicBeforeInitView() {
        tv_bt_state.setOnClickListener(this);
        rl_title_bar_right.setOnClickListener(this);
        iv_mapping_state.setOnClickListener(this);
        tv_start.setOnClickListener(this);
    }

    //触屏映射是否开启的逻辑
    @Override
    public void initView() {


    }


    @Override
    public void dealLogicAfterInitView() {


    }

    @Override
    public void onClickEvent(View view) {
        switch (view.getId()) {
            case R.id.tv_bt_state:
                if (isDeviceOn && hasConnected) {
                    //已经连接  跳转到 测试按键映射act
                    SkipUtils.start(this, BtnMappingAct.class);
                } else
                    startActivity(new
                            Intent(Settings.ACTION_BLUETOOTH_SETTINGS));//直接进入手机中的蓝牙设置界面
                break;
            case R.id.rl_title_bar_right:
                //跳转到帮助说明
                SkipUtils.start(this, HelpAct.class);
                break;
            case R.id.iv_mapping_state:
                //跳转到免root触屏映射说明
                SkipUtils.start(this, NoRootMappingAct.class);
                break;
            case R.id.tv_start:
                //跳转
                SkipUtils.start(this, BtnMappingAct.class);
                break;

            default:
                break;
        }


    }

    @Override
    public void onNetChanged(boolean oldStatus, boolean newStatus) {

    }

    //蓝牙逻辑start
    //手柄是否连接的逻辑
    private boolean isDeviceOn() {
        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);

        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
        //1判断蓝牙是否开  没开,则展示未连接
        if (bluetoothAdapter.isEnabled()) {
            //2得到所有已配对的蓝牙适配器对象
            Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();
            if (devices.size() > 0) {
                BluetoothDevice bindDevice = null;//已配对的手柄设备
                for (Iterator iterator = devices.iterator(); iterator.hasNext(); ) {
                    BluetoothDevice bluetoothDevice = (BluetoothDevice) iterator.next();
                    LogUtil.log("已配对name: " + bluetoothDevice.getName());
                    bindDevice = bluetoothDevice;
                    //得到远程已配对蓝牙设备的mac地址
                    if (StringUtil.isNotNull(bluetoothDevice.getName())
                            && bluetoothDevice.getName().startsWith(Constant.DEVICE_PRE)) {
                        //得到Newgamepad开头的第一个设备
                        bindDevice = bluetoothDevice;
                        break;
                    }

                }
                if (bindDevice != null) {
                    //3获得第一个已配对的Newgamepad开头的设备
                    LogUtil.log("已配对(但不一定已连接)设备: " + bindDevice.getName() + "---" + bindDevice.getAddress());
//                    if (bindDevice.)
                    return true;
                } else {
                    LogUtil.log("没有已配对的Newgamepad设备");
                    return false;
                }
            } else {
                LogUtil.log("没有已配对的蓝牙设备");
                return false;
            }

        } else {
            //蓝牙没开
            return false;
        }
    }

    //蓝牙逻辑end


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
