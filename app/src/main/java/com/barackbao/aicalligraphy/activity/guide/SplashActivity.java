package com.barackbao.aicalligraphy.activity.guide;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.barackbao.aicalligraphy.R;
import com.barackbao.aicalligraphy.activity.HomeActivity;
import com.barackbao.aicalligraphy.util.StaticClass;

public class SplashActivity extends AppCompatActivity {

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case StaticClass
                        .HANDLER_SPLASH:
                    if (isFirst()) {
                        startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                    } else if (null != StaticClass.currentUserName) {
                        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                    } else {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    }
                    finish();
                    break;
            }
        }
    };

    /**
     * 判断是否为第一次使用
     * 在用户注销当前账号时，需要将此标签设置为false
     *
     * @return
     */
    private boolean isFirst() {
        SharedPreferences sp = this.getSharedPreferences(StaticClass.IS_FIRST_USE, MODE_PRIVATE);
        boolean isFirst = sp.getBoolean(StaticClass.IS_FIRST_USE, true);
        SharedPreferences.Editor editor = sp.edit();
        if (isFirst) {
            editor.putBoolean(StaticClass.IS_FIRST_USE, false);
            editor.commit();
            return true;
        } else {
            return false;
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH, 2000);
    }
}
