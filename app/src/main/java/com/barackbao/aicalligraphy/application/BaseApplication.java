package com.barackbao.aicalligraphy.application;

import android.app.Application;

/**
 * <pre>
 *     author : baoqianyue
 *     time   : 2018/09/23
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class BaseApplication extends Application {

    private static BaseApplication application = null;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static BaseApplication getInstance() {
        return application;
    }
}
