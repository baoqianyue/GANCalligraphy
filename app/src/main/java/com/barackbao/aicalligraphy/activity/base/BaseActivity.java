package com.barackbao.aicalligraphy.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.barackbao.aicalligraphy.R;

/**
 * <pre>
 *     author : baoqianyue
 *     time   : 2018/09/23
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public abstract class BaseActivity extends AppCompatActivity {

    /**
     * 日志tag
     */
    public String TAG;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getComponentName().getShortClassName();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        if (null != getToolbar() && isShowBacking()) {
//            onBackPressed();
//        }
    }

//
//    public void setToolBarTitle(CharSequence title) {
//        if (mToolbarTitleTv != null) {
//            mToolbarTitleTv.setText(title);
//        } else {
//            getToolbar().setTitle(title);
//            setSupportActionBar(getToolbar());
//        }
//    }

    @Override
    protected void onStop() {
        super.onStop();
    }

//    public TextView getToolbarTitle() {
//        return mToolbarTitleTv;
//    }

//    protected boolean isShowBacking() {
//        return true;
//    }

//    public Toolbar getToolbar() {
//        return findViewById(R.id.toolbar);
//    }
}
