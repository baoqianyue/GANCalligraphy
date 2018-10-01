package com.barackbao.aicalligraphy.util;

import android.content.Context;
import android.util.DisplayMetrics;

public class DisplayUtil {

    private static Context mContext;
    private static int mScreenWidth;
    private static int mScreenHeight;
    private static float mDensity;

    public static void init(Context context) {
        DisplayUtil.mContext = context;
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        mScreenWidth = dm.widthPixels;
        mScreenHeight = dm.heightPixels;
        mDensity = dm.density;
    }

    /**
     * 单位转换dp转px
     */
    public static int dp2px(int dp) {
        return (int) (dp * mDensity + 0.5f);
    }


    public static int getGridWidth() {
        return (DisplayUtil.mScreenWidth - DisplayUtil.dp2px(10 + 10)) / 3;
    }
}
