package com.letv.handleassistant.utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.letv.handleassistant.R;
import com.letv.handleassistant.widget.LoadingDialog;


public class DialogUtil {

    public static LoadingDialog dialog;

    public static LoadingDialog showLoadingDialog(Activity activity) {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }
            dialog = new LoadingDialog(activity);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setTitle("加载中");
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dialog;
    }

    public static LoadingDialog showLoadingDialog(Activity activity, String msg) {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }
            dialog = new LoadingDialog(activity);
            if (StringUtil.isNullOrEmpty(msg)) {
                dialog.setTitle("正在获取数据");
            } else {
                dialog.setTitle(msg);
            }
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dialog;
    }

    public static void dismissDialog() {
        try {
            if (dialog != null && dialog.isShowing() == true) {
                dialog.dismiss();
                dialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     *
     * 普通对话框
     *
     * @param activity
     * @param title
     * @param msg
     * @param left
     * @param right
     * @param onClickListener
     * @return
     */
    public static Dialog createAlertDialog(Activity activity, String title,
                                           String msg, String left, String right,
                                           View.OnClickListener onClickListener) {
        final Dialog dialog = new Dialog(activity, R.style.Theme_dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_normal);
        TextView tv_title = (TextView) dialog.findViewById(R.id.tv_title);
        if (StringUtil.isNullOrEmpty(title)) {
            tv_title.setVisibility(View.GONE);
        } else {
            tv_title.setText(title);
        }

        TextView tv_content = (TextView) dialog.findViewById(R.id.tv_content);
        tv_content.setText(msg);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = DensityUtil.getWidth(activity) * 5 / 6;
        // lp.height = (int) (lp.width / 2);
        window.setAttributes(lp);
        TextView tv_left = (TextView) dialog.findViewById(R.id.tv_left);
        if (StringUtil.isNullOrEmpty(left)) {
            tv_left.setVisibility(View.GONE);
        } else {
            tv_left.setText(left);
            tv_left.setOnClickListener(onClickListener);
        }

        TextView tv_right = (TextView) dialog.findViewById(R.id.tv_right);
        if (StringUtil.isNullOrEmpty(right)) {
            tv_right.setVisibility(View.GONE);
        } else {
            tv_right.setText(right);
            tv_right.setOnClickListener(onClickListener);
        }

        dialog.show();
        return dialog;
    }



}
