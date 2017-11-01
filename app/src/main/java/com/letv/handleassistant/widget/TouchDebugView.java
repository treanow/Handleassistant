package com.letv.handleassistant.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.letv.handleassistant.R;
import com.letv.handleassistant.constant.Constant;
import com.letv.handleassistant.framework.spfs.SPHelper;
import com.letv.handleassistant.ui.bean.BtnBean;
import com.letv.handleassistant.utils.LogUtil;

import java.util.ArrayList;

/**
 * Debug测试手柄按键的SurfaceView
 */
public class TouchDebugView extends SurfaceView implements SurfaceHolder.Callback {
    public boolean isTest = false;
    private static final int MAX_TOUCHPOINTS = 10;
    private Paint paint;
    private ArrayList<BtnBean> btnList = new ArrayList<>();
    private ArrayList<BtnBean> tempList = new ArrayList<>();

    private int width, height;//SurfaceView的宽和高
    private float scale = 1.0f;
    private Canvas canvas;
    private int pointerCount = 0;//同时onTouch事件的 个数
    //按钮bean start
    private BtnBean XBean;
    private BtnBean YBean;
    private BtnBean LeftJSBean;
    private BtnBean RightJSBean;
    //按钮bean end
    //控件对象start
    public ImageView ivBtnX;
    public ImageView ivBtnY;
    public ProgressBar progressbar_l2;
    public ProgressBar progressbar_r2;
    public TextView tvProgressL2;
    public TextView tvProgressR2;
    //控件对象end

    public TouchDebugView(Context context) {
        super(context);
        init();
    }

    public TouchDebugView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setZOrderOnTop(true);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        holder.setFormat(PixelFormat.TRANSLUCENT);
        setFocusable(true); // 确保我们的View能获得输入焦点
        setFocusableInTouchMode(true); // 确保能接收到触屏事件

        paint = new Paint();
        paint.setColor(Color.BLUE);
        //先用2个 测试 以后换数据库
        //按键的bean start
        XBean = new BtnBean(KeyEvent.KEYCODE_BUTTON_X,
                SPHelper.getInstance().getBtnXx(), SPHelper.getInstance().getBtnXy());
        YBean = new BtnBean(KeyEvent.KEYCODE_BUTTON_Y,
                SPHelper.getInstance().getBtnYx(), SPHelper.getInstance().getBtnYy());
        LeftJSBean = new BtnBean(Constant.LEFT_JS_CODE, SPHelper.getInstance().getLeftJSx(),
                SPHelper.getInstance().getLeftJSy());//左摇杆
        RightJSBean = new BtnBean(Constant.RIGHT_JS_CODE, SPHelper.getInstance().getRightJSx(),
                SPHelper.getInstance().getRightJSy());//右摇杆
        //按键的bean end
    }

    /*
     * 处理触屏事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        describeEvent(this, event);
        // 获得屏幕触点数量
        pointerCount = event.getPointerCount();
        if (pointerCount > MAX_TOUCHPOINTS) {
            pointerCount = MAX_TOUCHPOINTS;
        }
        // 锁定Canvas,开始进行相应的界面处理
        canvas = getHolder().lockCanvas();
        if (canvas != null) {
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            if (event.getAction() == MotionEvent.ACTION_UP) {
                // 当手离开屏幕时，清屏
            } else {
                // 先在屏幕上画一个十字，然后画一个圆
                for (int i = 0; i < pointerCount; i++) {//重绘:相当于把以前的点带着一起画
                    // 获取一个触点的坐标，然后开始绘制
                    int id = event.getPointerId(i);
                    float x = event.getX(i);
                    float y = event.getY(i);
                    drawCrosshairsAndText(x, y, paint, canvas);
                }
                for (int i = 0; i < pointerCount; i++) {
                    int id = event.getPointerId(i);
                    float x = event.getX(i);
                    float y = event.getY(i);
                    drawCircle(x, y, paint, canvas);
                }
            }
            // 画完后，unlock
            getHolder().unlockCanvasAndPost(canvas);
        }
        return true;
    }

    /**
     * 画十字及坐标信息
     *
     * @param x
     * @param y
     * @param paint
     * @param c
     */
    private void drawCrosshairsAndText(float x, float y, Paint paint, Canvas c) {
        c.drawLine(0, y, width, y, paint);//横线
        c.drawLine(x, 0, x, height, paint);//竖线
    }

    /**
     * 画圆
     *
     * @param x
     * @param y
     * @param paint
     * @param c
     */
    private void drawCircle(float x, float y, Paint paint, Canvas c) {
        c.drawCircle(x, y, 15 * scale, paint);
    }

    /*
     * 进入程序时背景画成黑色，然后把"START_TEXT"写到屏幕
     */
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        this.width = width;
        this.height = height;
        if (width > height) {
            this.scale = width / 480f;
        } else {
            this.scale = height / 480f;
        }
    }

    public void surfaceCreated(SurfaceHolder holder) {
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
    }


    //画点和十字
    private void drawBtnCanvas() {
        canvas = getHolder().lockCanvas();
        if (canvas != null) {
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            // 先在屏幕上画一个十字，然后画一个圆
            for (int i = 0; i < btnList.size(); i++) {
                drawCrosshairsAndText(btnList.get(i).x, btnList.get(i).y, paint, canvas);
                drawCircle(btnList.get(i).x, btnList.get(i).y, paint, canvas);
            }
            // 画完后，unlock
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    //清除画布
    private void clearBtnCanvas() {
        canvas = getHolder().lockCanvas();
        if (canvas != null) {
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            // 画完后，unlock
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    //btnList添加按键
    private void addBtnBean(int keyCode, BtnBean bean) {
        if (keyCode == bean.keyCode) {
            if (!btnList.contains(bean))
                btnList.add(bean);
            doTestBtn(keyCode);
        }
    }

    private void doTestBtn(int keyCode) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BUTTON_X:
                hideBtn(ivBtnX);
                break;

            case KeyEvent.KEYCODE_BUTTON_Y:
                hideBtn(ivBtnY);
                break;

            default:
                break;
        }
    }

    private void hideBtn(ImageView iv) {
        if (iv != null && isTest && iv.getVisibility() == View.VISIBLE)
            iv.setVisibility(View.INVISIBLE);
    }

    //btnList删除按键
    private void removeBtnBean(int keyCode, BtnBean bean) {
        if (keyCode == bean.keyCode) {
            if (btnList.contains(bean))
                btnList.remove(bean);
        }
    }

    //打印log start
    private void describeEvent(View view, MotionEvent event) {
        StringBuilder sb = new StringBuilder(300);

        sb.append("Action: ").append(event.getAction()).append("\n");// 获取触控动作比如ACTION_DOWN
        sb.append("相对坐标: ").append(event.getX()).append("  *  ").append(event.getY()).append("   ");
        sb.append("绝对坐标: ").append(event.getRawX()).append("  *  ").append(event.getRawY()).append("\n");

        if (event.getX() < 0 || event.getX() > view.getWidth() || event.getY() < 0 || event.getY() > view.getHeight()) {
            sb.append("未点击在View范围内");
        }
        sb.append("Edge flags: ").append(event.getEdgeFlags()).append("  ");// 边缘标记,但是看设备情况,很可能始终返回0
        sb.append("Pressure: ").append(event.getPressure()).append("  ");// 压力值,0-1之间,看情况,很可能始终返回1
        sb.append("Size: ").append(event.getSize()).append("\n");// 指压范围
        sb.append("Down time: ").append(event.getDownTime()).append("ms   ");
        sb.append("Event time: ").append(event.getEventTime()).append("ms   ");
        sb.append("Elapsed: ").append(event.getEventTime() - event.getDownTime()).append("ms\n");

        System.out.println("describeEvent:" + sb.toString());
        //sb.toString();
    }
    //打印log end

    //手柄event start    keyCode==4时候 是back
    //KeyEvent.KEYCODE_BUTTON_X   99;
    //KEYCODE_BUTTON_Y = 100
    //KEYCODE_BUTTON_A = 96;
    //KEYCODE_BUTTON_B = 97;
    //KEYCODE_BUTTON_START = 108;
    //KEYCODE_UNKNOWN = 0 -- i键
    //KEYCODE_BACK = 4   (Y , B , 手机back键也会触发)
    //KEYCODE_BUTTON_L1 = 102
    //KEYCODE_BUTTON_R1 = 103
    //KEYCODE_BUTTON_L2 = 104 -- 有onGenericMotionEvent属性 0.003921628不变
    //KEYCODE_BUTTON_R2 = 105 -- 有onGenericMotionEvent属性 0.003921628不变
    //KEYCODE_DPAD_DOWN = 20;
    //KEYCODE_DPAD_LEFT = 21;
    //KEYCODE_DPAD_RIGHT = 22;
    //KEYCODE_DPAD_UP = 19;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        LogUtil.log("keyDown keyCode = " + keyCode + ", scanCode = " + event.getScanCode() + "\n");
        InputDevice device = event.getDevice();
//        if (device != null && (device.getSources() + "").length() == 8 && keyCode == KeyEvent.KEYCODE_BACK) {
////小米手柄 16779025  手机 4355//三星手柄 16778513  手机 257//moto    16779025  手机 769
//            return true;
//        }
        //添加Btn对象
        addBtnBean(keyCode, XBean);

        addBtnBean(keyCode, YBean);
        // 锁定Canvas,开始进行相应的界面处理
        drawBtnCanvas();
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        LogUtil.log("keyUp " + keyCode + "\n");
        InputDevice device = event.getDevice();
        if (device != null && (device.getSources() + "").length() == 8 && keyCode == KeyEvent.KEYCODE_BACK) {
            //小米手柄 16779025  手机 4355//三星手柄 16778513  手机 257//moto    16779025  手机 769
            return true;
        }
        removeBtnBean(keyCode, XBean);
        removeBtnBean(keyCode, YBean);
        drawBtnCanvas();
        if (btnList.size() == 0)
            clearBtnCanvas();
        return super.onKeyUp(keyCode, event);
    }

    //摇杆官方示例start
    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
//        LogUtil.log("AXIS_LTRIGGER"+event.getAxisValue(MotionEvent.AXIS_LTRIGGER))

        // Check that the event came from a game controller
        if ((event.getSource() & InputDevice.SOURCE_JOYSTICK) ==
                InputDevice.SOURCE_JOYSTICK &&
                event.getAction() == MotionEvent.ACTION_MOVE) {
            // Process all historical movement samples in the batch
//            final int historySize = event.getHistorySize();
//            //处理 历史事件
//            for (int i = 0; i < historySize; i++) {
//                // Process the event at historical position i
//                processJoystickInput(event, i);
//            }

            // Process the current movement sample in the batch (position -1)
            processJoystickInput(event, -1);

            return true;
        }
        return super.onGenericMotionEvent(event);
    }

    private static float getCenteredAxis(MotionEvent event,
                                         InputDevice device, int axis, int historyPos) {
        final InputDevice.MotionRange range =
                device.getMotionRange(axis, event.getSource());

        // A joystick at rest does not always report an absolute position of
        // (0,0). Use the getFlat() method to determine the range of values
        // bounding the joystick axis center.
        if (range != null) {
            final float flat = range.getFlat();
            final float value =
                    historyPos < 0 ? event.getAxisValue(axis) :
                            event.getHistoricalAxisValue(axis, historyPos);

            // Ignore axis values that are within the 'flat' region of the
            // joystick axis center.
            if (Math.abs(value) > flat) {
                return value;
            }
        }
        return 0;
    }

    private void processJoystickInput(MotionEvent event,
                                      int historyPos) {

        InputDevice mInputDevice = event.getDevice();
        //左摇杆
        float leftx = getCenteredAxis(event, mInputDevice,
                MotionEvent.AXIS_X, historyPos);
        float lefty = getCenteredAxis(event, mInputDevice,
                MotionEvent.AXIS_Y, historyPos);
        //右摇杆
        float rightx = getCenteredAxis(event, mInputDevice,
                MotionEvent.AXIS_Z, historyPos);
        float righty = getCenteredAxis(event, mInputDevice,
                MotionEvent.AXIS_RZ, historyPos);
        //L2(0.0--0.7)
        float l2 = getCenteredAxis(event, mInputDevice,
                MotionEvent.AXIS_BRAKE, historyPos);
        //R2(0.0--1.0)
        float r2 = getCenteredAxis(event, mInputDevice,
                MotionEvent.AXIS_GAS, historyPos);
        //在这里处理 Axis 事件

        doLeftJoystick(leftx, lefty);//左摇杆
        doRightJoystick(rightx, righty);//右摇杆
        doL2(l2);//L2(0.0--0.7)
        doR2(r2);//R2(0.0--1.0)


        // Update the ship object based on the new x and y values
    }


    private void doLeftJoystick(float x, float y) {
        if (Math.abs(x) > 0 || Math.abs(y) > 0) {
            boolean isLeftAdd = false;
            for (int i = 0; i < btnList.size(); i++) {
                if (btnList.get(i).keyCode == Constant.LEFT_JS_CODE) {
                    btnList.get(i).x = SPHelper.getInstance().getLeftJSx() + SPHelper.getInstance().getLeftJSWidth() / 2 * x;
                    btnList.get(i).y = SPHelper.getInstance().getLeftJSy() + SPHelper.getInstance().getLeftJSWidth() / 2 * y;
                    isLeftAdd = true;
                    break;
                }
            }
            if (!isLeftAdd) {
                LeftJSBean = new BtnBean(Constant.LEFT_JS_CODE, SPHelper.getInstance().getLeftJSx(),
                        SPHelper.getInstance().getLeftJSy());//左摇杆
                addBtnBean(Constant.LEFT_JS_CODE, LeftJSBean);
            }

        }

        if (x == 0 && y == 0) {
            for (int i = 0; i < btnList.size(); i++) {
                if (btnList.get(i).keyCode != Constant.LEFT_JS_CODE)
                    tempList.add(btnList.get(i));
            }
            btnList.clear();
            btnList.addAll(tempList);
            tempList.clear();
        }
        drawBtnCanvas();
        if (btnList.size() == 0)
            clearBtnCanvas();
    }

    private void doRightJoystick(float x, float y) {
        if (Math.abs(x) > 0 || Math.abs(y) > 0) {
            boolean isRightAdd = false;
            for (int i = 0; i < btnList.size(); i++) {
                if (btnList.get(i).keyCode == Constant.RIGHT_JS_CODE) {
                    btnList.get(i).x = SPHelper.getInstance().getRightJSx() + SPHelper.getInstance().getRightJSWidth() / 2 * x;
                    btnList.get(i).y = SPHelper.getInstance().getRightJSy() + SPHelper.getInstance().getRightJSWidth() / 2 * y;
                    isRightAdd = true;
                    break;
                }
            }
            if (!isRightAdd) {
                RightJSBean = new BtnBean(Constant.RIGHT_JS_CODE, SPHelper.getInstance().getRightJSx(),
                        SPHelper.getInstance().getRightJSy());//右摇杆
                addBtnBean(Constant.RIGHT_JS_CODE, RightJSBean);
            }

        }

        if (x == 0 && y == 0) {
            for (int i = 0; i < btnList.size(); i++) {
                if (btnList.get(i).keyCode != Constant.RIGHT_JS_CODE)
                    tempList.add(btnList.get(i));
            }
            btnList.clear();
            btnList.addAll(tempList);
            tempList.clear();
        }
        drawBtnCanvas();
        if (btnList.size() == 0)
            clearBtnCanvas();
    }

    private void doL2(float l2) {
        int a = (int) (l2 * 100);
        if (progressbar_l2 != null)
            progressbar_l2.setProgress(a);
        if (tvProgressL2 != null)
            tvProgressL2.setText(a+"");
    }


    private void doR2(float r2) {
        int a = (int) (r2 * 100);
        if (progressbar_r2 != null)
            progressbar_r2.setProgress(a);
        if (tvProgressR2 != null)
            tvProgressR2.setText(a+"");
    }


    //摇杆官方示例end
    //手柄event end
}

