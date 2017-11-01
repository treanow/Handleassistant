package com.letv.handleassistant.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

@SuppressWarnings("rawtypes")
public class SkipUtils {

    public static void start(Context context, Class clazz) {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
    }

    public static void start(Activity activity, Class clazz) {
        Intent intent = new Intent(activity, clazz);
        activity.startActivity(intent);
    }

    public static void start(Context context, Class clazz, Bundle bundle) {
        Intent intent = new Intent(context, clazz);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void start(Activity activity, Class clazz, Bundle bundle) {
        Intent intent = new Intent(activity, clazz);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    public static void startAndFinish(Activity activity, Class clazz) {
        Intent intent = new Intent(activity, clazz);
        activity.startActivity(intent);
        activity.finish();
    }

    public static void startAndFinish(Activity activity, Class clazz, Bundle bundle) {
        Intent intent = new Intent(activity, clazz);
        intent.putExtras(bundle);
        activity.startActivity(intent);
        activity.finish();
    }

    public static void startForResult(Activity activity, Class clazz) {
        Intent intent = new Intent(activity, clazz);
        activity.startActivityForResult(intent, 0);
    }

    public static void startForResult(Activity activity, Class clazz, int code) {
        Intent intent = new Intent(activity, clazz);
        activity.startActivityForResult(intent, code);
    }

    public static void startForResult(Activity activity, Class clazz, Bundle bundle) {
        Intent intent = new Intent(activity, clazz);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, 0);
    }

    public static void startForResult(Activity activity, Class clazz, Bundle bundle, int code) {
        Intent intent = new Intent(activity, clazz);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, code);
    }

}
