package com.barackbao.aicalligraphy.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.barackbao.aicalligraphy.R;
import com.barackbao.aicalligraphy.activity.base.BaseActivity;
import com.barackbao.aicalligraphy.view.fragment.home.CopyBookFragment;
import com.barackbao.aicalligraphy.view.fragment.home.GenBookFragment;
import com.barackbao.aicalligraphy.view.fragment.home.GenPaintingFragment;
import com.barackbao.aicalligraphy.view.fragment.home.MineFragment;

/**
 * <pre>
 *     author : baoqianyue
 *     time   : 2018/09/23
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener {

    private FragmentManager fm;
    private CopyBookFragment mCopyBookFragment;
    private GenBookFragment mGenBookFragment;
    private GenPaintingFragment mGenPaintFragment;
    private MineFragment mMineFragment;
    private Fragment mCurrentFragment;

    private RelativeLayout copyBookRl;
    private RelativeLayout genBookRl;
    private RelativeLayout genPaintRl;
    private RelativeLayout mineRl;

    private TextView copyBookView;
    private TextView genBookView;
    private TextView genPaintView;
    private TextView mineView;

    private TextView copyBookTv;
    private TextView genBookTv;
    private TextView genPaintTv;
    private TextView mineTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        mCopyBookFragment = new CopyBookFragment();
        fm = getSupportFragmentManager();
        //设置主页
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.content_layout, mCopyBookFragment);
        fragmentTransaction.commit();
    }

    private void initView() {
        copyBookRl = findViewById(R.id.copybook_layout_view);
        copyBookView = findViewById(R.id.copybook_image_view);
        copyBookTv = findViewById(R.id.copybook_image_tv);
        genBookRl = findViewById(R.id.genbook_layout_view);
        genBookView = findViewById(R.id.genbook_image_view);
        genBookTv = findViewById(R.id.genbook_image_tv);
        genPaintRl = findViewById(R.id.genpaint_layout_view);
        genPaintView = findViewById(R.id.genpaint_image_view);
        genPaintTv = findViewById(R.id.genpaint_image_tv);
        mineRl = findViewById(R.id.mine_layout_view);
        mineView = findViewById(R.id.mine_image_view);
        mineTv = findViewById(R.id.mine_image_tv);
        copyBookRl.setOnClickListener(this);
        genBookRl.setOnClickListener(this);
        genPaintRl.setOnClickListener(this);
        mineRl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        switch (v.getId()) {
            case R.id.copybook_layout_view:
                copyBookView.setBackgroundResource(R.drawable.comui_tab_copybook_selected);
                genBookView.setBackgroundResource(R.drawable.comui_tab_genbook);
                genPaintView.setBackgroundResource(R.drawable.comui_tab_genpaint);
                mineView.setBackgroundResource(R.drawable.comui_tab_mine);
                copyBookTv.setTextColor(Color.parseColor("#fb7299"));
                genBookTv.setTextColor(Color.BLACK);
                genPaintTv.setTextColor(Color.BLACK);
                mineTv.setTextColor(Color.BLACK);

                //将其他fg都隐藏掉
                hideFragment(fragmentTransaction, mGenBookFragment);
                hideFragment(fragmentTransaction, mGenPaintFragment);
                hideFragment(fragmentTransaction, mMineFragment);
                if (null == mCopyBookFragment) {
                    mCopyBookFragment = new CopyBookFragment();
                    fragmentTransaction.add(R.id.content_layout, mCopyBookFragment);
                } else {
                    mCurrentFragment = mCopyBookFragment;
                    fragmentTransaction.show(mCopyBookFragment);
                }
                break;
            case R.id.genbook_layout_view:

                copyBookView.setBackgroundResource(R.drawable.comui_tab_copybook);
                genBookView.setBackgroundResource(R.drawable.comui_tab_genbook_selected);
                genPaintView.setBackgroundResource(R.drawable.comui_tab_genpaint);
                mineView.setBackgroundResource(R.drawable.comui_tab_mine);
                copyBookTv.setTextColor(Color.BLACK);
                genBookTv.setTextColor(Color.parseColor("#fb7299"));
                genPaintTv.setTextColor(Color.BLACK);
                mineTv.setTextColor(Color.BLACK);

                hideFragment(fragmentTransaction, mCopyBookFragment);
                hideFragment(fragmentTransaction, mGenBookFragment);
                hideFragment(fragmentTransaction, mMineFragment);

                if (null == mGenBookFragment) {
                    mGenBookFragment = new GenBookFragment();
                    fragmentTransaction.add(R.id.content_layout, mGenBookFragment);
                } else {
                    mCurrentFragment = mGenBookFragment;
                    fragmentTransaction.show(mGenBookFragment);
                }
                break;

            case R.id.genpaint_layout_view:
                copyBookView.setBackgroundResource(R.drawable.comui_tab_copybook);
                genBookView.setBackgroundResource(R.drawable.comui_tab_genbook);
                genPaintView.setBackgroundResource(R.drawable.comui_tab_genpaint_selected);
                mineView.setBackgroundResource(R.drawable.comui_tab_mine);
                copyBookTv.setTextColor(Color.BLACK);
                genBookTv.setTextColor(Color.BLACK);
                genPaintTv.setTextColor(Color.parseColor("#fb7299"));
                mineTv.setTextColor(Color.BLACK);

                hideFragment(fragmentTransaction, mCopyBookFragment);
                hideFragment(fragmentTransaction, mGenBookFragment);
                hideFragment(fragmentTransaction, mMineFragment);

                if (null == mGenPaintFragment) {
                    mGenPaintFragment = new GenPaintingFragment();
                    fragmentTransaction.add(R.id.content_layout, mGenPaintFragment);
                } else {
                    mCurrentFragment = mGenPaintFragment;
                    fragmentTransaction.show(mGenPaintFragment);
                }
                break;

            case R.id.mine_layout_view:
                copyBookView.setBackgroundResource(R.drawable.comui_tab_copybook);
                genBookView.setBackgroundResource(R.drawable.comui_tab_genbook);
                genPaintView.setBackgroundResource(R.drawable.comui_tab_genpaint);
                mineView.setBackgroundResource(R.drawable.comui_tab_mine_selected);
                copyBookTv.setTextColor(Color.BLACK);
                genBookTv.setTextColor(Color.BLACK);
                genPaintTv.setTextColor(Color.BLACK);
                mineTv.setTextColor(Color.parseColor("#fb7299"));

                hideFragment(fragmentTransaction, mCopyBookFragment);
                hideFragment(fragmentTransaction, mGenBookFragment);
                hideFragment(fragmentTransaction, mGenPaintFragment);

                if (null == mMineFragment) {
                    mMineFragment = new MineFragment();
                    fragmentTransaction.add(R.id.content_layout, mMineFragment);
                } else {
                    mCurrentFragment = mMineFragment;
                    fragmentTransaction.show(mMineFragment);
                }
                break;
        }
        fragmentTransaction.commit();
    }

    private void hideFragment(FragmentTransaction fragmentTransaction, Fragment fragment) {
        if (fragment != null) {
            fragmentTransaction.hide(fragment);
        }
    }
}
