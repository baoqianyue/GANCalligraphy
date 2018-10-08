package com.barackbao.aicalligraphy.util;

import android.content.Context;
import android.util.DisplayMetrics;

public class DisplayUtil {

    private static Context mContext;
    private static int mScreenWidth;
    private static int mScreenHeight;
    private static float mDensity;
    //全屏显示图片
    public static int EXACT_SCREEN_HEIGHT;
    public static int EXACT_SCREEN_WIDTH;

    public static void init(Context context) {
        DisplayUtil.mContext = context;
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        mScreenWidth = dm.widthPixels;
        mScreenHeight = dm.heightPixels;
        mDensity = dm.density;
    }

    public static int getmScreenWidth() {
        return mScreenWidth;
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

    /**
     * 判断字符串是否全为汉子
     *
     * @param str
     * @return
     */
    public static boolean isChinese(String str) {
        int n = 0;
        for (int i = 0; i < str.length(); i++) {
            n = (int) str.charAt(i);
            if (!(19968 <= n && n < 40869)) {
                return false;
            }
        }
        return true;
    }
}
