package com.barackbao.aicalligraphy.activity.genbook;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.barackbao.aicalligraphy.R;
import com.barackbao.aicalligraphy.util.ImagePiece;
import com.barackbao.aicalligraphy.util.PhotoUtil;
import com.bumptech.glide.Glide;
import com.github.chaossss.widget.view.TriBadgedView;
import com.xujiaji.happybubble.BubbleDialog;

import java.util.List;

/**
 * Created by BarackBao on 2019/4/16;
 */
public class EvalActivity extends AppCompatActivity {

    private ImageView badWordImg;
    private ImageView niceWordImg;

    private String badWordImgPath;
    private String niceWordImgPath;

    private Bitmap badBitmap;
    private Bitmap niceBitmap;

    private Toolbar toolbar;
    private TextView toolbarTitleTv;
    private TriBadgedView niceWordLabelTv;

    List<ImagePiece> badBitmaps;
    List<ImagePiece> niceBitmaps;

    private LayoutInflater inflater;
    private static final String TAG = "EvalActivity.class";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eval);
        inflater = LayoutInflater.from(this);

        toolbar = findViewById(R.id.toolbar);
        toolbarTitleTv = findViewById(R.id.toolbar_title_tv);
        if (null != toolbar) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (null != toolbarTitleTv) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            toolbarTitleTv.setText("字体分析");
        }

        badWordImgPath = getIntent().getStringExtra("bad_path");
        Log.e(TAG, "badwordimgpath: " + badWordImgPath);
//        niceWordImgPath = getIntent().getStringExtra("nice_path");
        niceWordImgPath = "/storage/emulated/0/hw/IMG_20190413_122640.png";

        initViews();
    }

    private void initViews() {
        badWordImg = findViewById(R.id.bad_word_img);
        niceWordImg = findViewById(R.id.nice_word_img);
        niceWordLabelTv = findViewById(R.id.nice_word_label_tv);
        Glide.with(this).load(badWordImgPath).into(badWordImg);
        Glide.with(this).load(niceWordImgPath).into(niceWordImg);
        niceWordLabelTv.setBadgeText("生成:");
        niceWordLabelTv.showBadge(true);
        badBitmap = PhotoUtil.getScaleBitmap(badWordImgPath);
        niceBitmap = PhotoUtil.getScaleBitmap(niceWordImgPath);
        Log.e(TAG, "badimg: " + badBitmap.toString() + "niceimg: " + niceBitmap);

        badBitmaps = PhotoUtil.split(badBitmap, 3, 3);
        niceBitmaps = PhotoUtil.split(niceBitmap, 3, 3);

        new Thread(new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i < badBitmaps.size(); i++) {

                    badBitmaps.get(i).diffNums = PhotoUtil.contrastBitmap(badBitmaps.get(i).bitmap, niceBitmaps.get(i).bitmap);
                }
            }
        }).start();

        badWordImg.post(new Runnable() {
            @Override
            public void run() {
                if (badBitmaps.get(1).diffNums > 10) {
                    twoWindowShow(badWordImg);
                }
                if (badBitmaps.get(4).diffNums > 10) {
                    fiveWindowShow(badWordImg);
                }

                if (badBitmaps.get(8).diffNums > 10) {
                    nineWindowShow(badWordImg);
                }
            }
        });

    }

    private void oneWindowShow(View view) {
        BubbleDialog dialog = new BubbleDialog(EvalActivity.this)
                .addContentView(inflater.inflate(R.layout.layout_popup_view, null))
                .setClickedView(view)
                .setPosition(BubbleDialog.Position.LEFT)
                .setTransParentBackground()
                .calBar(true);
        dialog.setCancelable(false);
        dialog.show();
    }

    private void twoWindowShow(View view) {
        View tempView = inflater.inflate(R.layout.layout_popup_view, null);
        TextView tvContent = tempView.findViewById(R.id.tvContent);
        tvContent.setText("起笔不准确");
        BubbleDialog dialog = new BubbleDialog(EvalActivity.this)
                .addContentView(tempView)
                .setClickedView(view)
                .setPosition(BubbleDialog.Position.RIGHT)
                .setTransParentBackground()
                .setOffsetY(-50)
                .calBar(true);


        dialog.setCancelable(false);
        dialog.show();
    }

    private void threeWindowShow(View view) {
        View tempView = inflater.inflate(R.layout.layout_popup_view, null);
        TextView tvContent = tempView.findViewById(R.id.tvContent);
        tvContent.setText("收笔不准确");
        BubbleDialog dialog = new BubbleDialog(EvalActivity.this)
                .addContentView(tempView)
                .setClickedView(view)
                .setPosition(BubbleDialog.Position.RIGHT)
                .setTransParentBackground()
                .calBar(true);

        dialog.setCancelable(false);
        dialog.show();
    }


    private void fiveWindowShow(View view) {
        View tempView = inflater.inflate(R.layout.layout_popup_view, null);
        TextView tvContent = tempView.findViewById(R.id.tvContent);
        tvContent.setText("结构不稳");
        BubbleDialog dialog = new BubbleDialog(EvalActivity.this)
                .addContentView(tempView)
                .setClickedView(view)
                .setTransParentBackground()
                .setPosition(BubbleDialog.Position.LEFT)
                .setOffsetX(120)
                .calBar(true);
        dialog.setCancelable(false);
        dialog.show();
    }

    private void nineWindowShow(View view) {
        View tempView = inflater.inflate(R.layout.layout_popup_view, null);
        TextView tvContent = tempView.findViewById(R.id.tvContent);
        tvContent.setText("收笔不准确");
        BubbleDialog dialog = new BubbleDialog(EvalActivity.this)
                .addContentView(tempView)
                .setClickedView(view)
                .setPosition(BubbleDialog.Position.RIGHT)
                .setTransParentBackground()
                .setOffsetY(50)
                .calBar(true);
        dialog.setCancelable(false);
        dialog.show();
    }

}
