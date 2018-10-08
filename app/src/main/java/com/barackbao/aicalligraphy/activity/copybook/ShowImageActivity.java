package com.barackbao.aicalligraphy.activity.copybook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.barackbao.aicalligraphy.R;
import com.barackbao.aicalligraphy.model.CopyBook;
import com.barackbao.aicalligraphy.util.DisplayUtil;
import com.barackbao.aicalligraphy.view.widgets.scrollerimageview.ShowImageDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


import java.util.ArrayList;
import java.util.List;

public class ShowImageActivity extends AppCompatActivity implements View.OnClickListener {

    private CopyBook data;
    private List<String> dataImgs = new ArrayList<>();
    private ImageView copybookCoverImg;
    private TextView copybookAuthorTv;
    private TextView copybookNameTv;
    private Button lookMoreBtn;
    private Button collectionBtn;
    private Toolbar mToolbar;
    private TextView mToolbarTitleTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_images);
        mToolbar = findViewById(R.id.toolbar);
        mToolbarTitleTv = findViewById(R.id.toolbar_title_tv);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (mToolbarTitleTv != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            mToolbarTitleTv.setText("碑帖详情");
            //隐藏默认标题
        }
        getDeviceDensity();
        Intent intent = getIntent();
        data = (CopyBook) intent.getSerializableExtra("data");

        initView();
    }

    private void initView() {
        copybookCoverImg = findViewById(R.id.copy_book_cover_img);
        copybookNameTv = findViewById(R.id.copy_book_name_tv);
        copybookAuthorTv = findViewById(R.id.copy_book_author_tv);
        lookMoreBtn = findViewById(R.id.look_more_btn);
        collectionBtn = findViewById(R.id.collection_btn);
        lookMoreBtn.setOnClickListener(this);
        collectionBtn.setOnClickListener(this);
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.placeholder)
                .error(R.mipmap.errorimg);
        Glide.with(this).
                load(data.getCopy_book_all().get(0).getContentImgUrl())
                .apply(options)
                .into(copybookCoverImg);
        copybookNameTv.setText(data.getCopyBookName());
        copybookAuthorTv.setText(data.getAuthor());
        data.getCopy_book_all().remove(data.getCopy_book_all().get(0));
        for (CopyBook.CopyBookAllBean book :
                data.getCopy_book_all()) {
            dataImgs.add(book.getContentImgUrl());
        }
    }

    protected void getDeviceDensity() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        DisplayUtil.EXACT_SCREEN_HEIGHT = metrics.heightPixels;
        DisplayUtil.EXACT_SCREEN_WIDTH = metrics.widthPixels;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.look_more_btn:
                new ShowImageDialog(ShowImageActivity.this, dataImgs).show();
                break;
            case R.id.collection_btn:
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Toast.makeText(this, "已收藏", Toast.LENGTH_SHORT).show();
        }
    }
}
