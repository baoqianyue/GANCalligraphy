package com.barackbao.aicalligraphy.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.barackbao.aicalligraphy.R;
import com.barackbao.aicalligraphy.activity.base.BaseActivity;
import com.barackbao.aicalligraphy.activity.genbook.CameraActivity;
import com.barackbao.aicalligraphy.activity.genbook.GenCopybookActivity;
import com.barackbao.aicalligraphy.activity.genbook.SketchPadActivity;
import com.barackbao.aicalligraphy.activity.genbook.TestWordActivity;
import com.barackbao.aicalligraphy.activity.guide.GuideActivity;
import com.barackbao.aicalligraphy.activity.style.StyleTransferActivity;
import com.barackbao.aicalligraphy.view.fragment.home.CopyBookFragment;
import com.barackbao.aicalligraphy.view.fragment.home.GenBookFragment;
import com.barackbao.aicalligraphy.view.fragment.home.GenPaintingFragment;
import com.barackbao.aicalligraphy.view.fragment.home.MineFragment;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import org.jetbrains.anko.ToastsKt;

/**
 * <pre>
 *     author : baoqianyue
 *     time   : 2018/09/23
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private FragmentManager fm;
    private CopyBookFragment mCopyBookFragment;
    private GenBookFragment mGenBookFragment;
    private GenPaintingFragment mGenPaintFragment;
    private MineFragment mMineFragment;
    private Fragment mCurrentFragment;
    private Toolbar toolbar;

    private RelativeLayout copyBookRl;
    private RelativeLayout genBookRl;
    private RelativeLayout genPaintRl;
    private RelativeLayout mineRl;

    private TextView toolbarTextTv;
    private TextView copyBookView;
    private TextView genBookView;
    private TextView genPaintView;
    private TextView mineView;

    private TextView copyBookTv;
    private TextView genBookTv;
    private TextView genPaintTv;
    private TextView mineTv;

    private FloatingActionsMenu genBookFabMenu;
    private FloatingActionButton genBookGenFab;
    private FloatingActionButton genBookPraFab;
    private FloatingActionButton genBookTestFab;
    // 风格迁移按钮
    private FloatingActionButton genBookstyleFab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        genBookFabMenu.setVisibility(View.GONE);
        mCopyBookFragment = new CopyBookFragment();
        mCurrentFragment = mCopyBookFragment;
        fm = getSupportFragmentManager();
        //设置主页
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.content_layout, mCopyBookFragment);
        fragmentTransaction.commit();
    }

    private void initView() {
        genBookFabMenu = findViewById(R.id.genbook_add_fab_menu);
        genBookGenFab = findViewById(R.id.genbook_genbook_fab);
        genBookPraFab = findViewById(R.id.genbook_practice_fab);
        genBookTestFab = findViewById(R.id.genbook_test_fab);
        toolbar = findViewById(R.id.main_toolbar);
        toolbarTextTv = findViewById(R.id.toolbar_text_tv);
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
        genBookstyleFab = findViewById(R.id.genbook_style_fab);
        copyBookRl.setOnClickListener(this);
        genBookRl.setOnClickListener(this);
        genPaintRl.setOnClickListener(this);
        mineRl.setOnClickListener(this);
        genBookGenFab.setOnClickListener(this);
        genBookPraFab.setOnClickListener(this);
        genBookTestFab.setOnClickListener(this);
        genBookstyleFab.setOnClickListener(this);
        setSupportActionBar(toolbar);
        toolbarTextTv.setText("字帖库");

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.copybook_action_search:
                        Toast.makeText(HomeActivity.this, "search", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.copybook_action_collection:
                        if (MineFragment.Companion.isLogin()) {

                        } else {
                            startActivity(new Intent(HomeActivity.this, GuideActivity.class));
                            Toast.makeText(HomeActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        break;
                }
                return true;
            }
        });
    }

    /**
     * 设置toolbar图标
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_copybook, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (mCurrentFragment == mCopyBookFragment) {
            menu.findItem(R.id.copybook_action_search).setVisible(true);
            menu.findItem(R.id.copybook_action_collection).setVisible(true);
        } else if (mCurrentFragment == mGenBookFragment) {
            menu.findItem(R.id.copybook_action_search).setVisible(false);
            menu.findItem(R.id.copybook_action_collection).setVisible(false);
        } else if (mCurrentFragment == mGenPaintFragment) {
            menu.findItem(R.id.copybook_action_search).setVisible(false);
            menu.findItem(R.id.copybook_action_collection).setVisible(false);
        } else {
            menu.findItem(R.id.copybook_action_search).setVisible(false);
            menu.findItem(R.id.copybook_action_collection).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        switch (v.getId()) {
            case R.id.genbook_practice_fab:
                startActivity(new Intent(this, SketchPadActivity.class));
                genBookFabMenu.collapse();
                break;
            case R.id.genbook_genbook_fab:
                startActivity(new Intent(this, GenCopybookActivity.class));
                genBookFabMenu.collapse();
                break;
            case R.id.genbook_test_fab:
                startActivity(new Intent(this, CameraActivity.class));
                genBookFabMenu.collapse();
                break;
            case R.id.genbook_style_fab:
               /* if (MineFragment.Companion.isLogin()) {

                } else {
                    startActivity(new Intent(HomeActivity.this, GuideActivity.class));
                    Toast.makeText(HomeActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                    finish();
                }*/
                startActivity(new Intent(this, StyleTransferActivity.class));
                genBookFabMenu.collapse();
                break;
            case R.id.copybook_layout_view:
                genBookFabMenu.setVisibility(View.GONE);
                toolbarTextTv.setText("字帖库");
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
                    mCurrentFragment = mCopyBookFragment;
                } else {
                    mCurrentFragment = mCopyBookFragment;
                    fragmentTransaction.show(mCopyBookFragment);
                }
                //显示调用onPrepareOptionMenu()方法，改变toolbar 中 的menu
                supportInvalidateOptionsMenu();
                break;
            case R.id.genbook_layout_view:
                genBookFabMenu.setVisibility(View.VISIBLE);
                toolbarTextTv.setText("生成字帖");
                toolbar.hideOverflowMenu();
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
                    mCurrentFragment = mGenBookFragment;
                } else {
                    mCurrentFragment = mGenBookFragment;
                    fragmentTransaction.show(mGenBookFragment);
                }
                supportInvalidateOptionsMenu();
                break;

            case R.id.genpaint_layout_view:
                genBookFabMenu.setVisibility(View.GONE);
                toolbarTextTv.setText("国画生成");
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
                    mCurrentFragment = mGenPaintFragment;
                } else {
                    mCurrentFragment = mGenPaintFragment;
                    fragmentTransaction.show(mGenPaintFragment);
                }
                supportInvalidateOptionsMenu();
                break;

            case R.id.mine_layout_view:
                genBookFabMenu.setVisibility(View.GONE);
                toolbarTextTv.setText("我");
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
                    mCurrentFragment = mMineFragment;
                } else {
                    mCurrentFragment = mMineFragment;
                    fragmentTransaction.show(mMineFragment);
                }
                supportInvalidateOptionsMenu();
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
