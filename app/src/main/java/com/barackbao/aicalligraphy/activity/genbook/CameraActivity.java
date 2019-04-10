package com.barackbao.aicalligraphy.activity.genbook;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.barackbao.aicalligraphy.R;
import com.barackbao.aicalligraphy.classifier.KaiClassifier;
import com.barackbao.aicalligraphy.util.PhotoUtil;
import com.barackbao.aicalligraphy.util.RequestPermission;
import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by BarackBao on 2019/4/10;
 */
public class CameraActivity extends AppCompatActivity {

    private static final int REQUEST_TAKE_PHOTO_CODE = 1;
    private static final int START_CAMERA = 1002;
    private String cameraImgPath;


    private static final String TAG = "CameraActivity.class";

    private KaiClassifier classifier;
    private static final String MODEL_FILE = "file:///android_asset/solve_norm_model.pb";

    private Toolbar toolbar;
    private TextView toolbarTitleTv;
    private ImageView cameraImg;
    private Button startCameraBtn;
    private TextView calligrapherNameTv;
    private TextView probTv;
    private Button getEvalBtn;

    private File imgFile;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        toolbar = findViewById(R.id.toolbar);
        toolbarTitleTv = findViewById(R.id.toolbar_title_tv);
        if (null != toolbar) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (null != toolbarTitleTv) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            toolbarTitleTv.setText("字体评测");
        }

        RequestPermission.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA},
                new RequestPermission.OnPermissionRequestListener() {

                    @Override
                    public void onGranted() {

                    }

                    @Override
                    public void onDenied(List<String> deniedList) {
                        Toast.makeText(CameraActivity.this, "拒绝权限将无法正常使用",
                                Toast.LENGTH_SHORT).show();
                    }
                });

        initView();
    }

    //申请拍照权限

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取拍照后的图像
        if (requestCode == START_CAMERA && resultCode == Activity.RESULT_OK) {
            Glide.with(this).load(cameraImgPath).into(cameraImg);
            Bitmap bitmap = PhotoUtil.getScaleBitmap(cameraImgPath);
            List<Object> res;
            res = classifier.predict(bitmap);
            String calligrapher;
            int label = (int) res.get(0);
            if (label == 0) {
                calligrapher = "柳公权";
            } else if (label == 1) {
                calligrapher = "欧阳询";
            } else if (label == 2) {
                calligrapher = "颜真卿";
            } else {
                calligrapher = "赵孟頫";
            }
            calligrapherNameTv.setText("最相似书法家: " + calligrapher);
            probTv.setText("相似程度：" + res.get(1));
//            Toast.makeText(this, "label： " + res.get(0) + " the prob is：" + res.get(1), Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        cameraImg = findViewById(R.id.camera_img);
        startCameraBtn = findViewById(R.id.start_camera_btn);
        calligrapherNameTv = findViewById(R.id.name_tv);
        probTv = findViewById(R.id.prob_tv);
        getEvalBtn = findViewById(R.id.get_eval_btn);
        startCameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classifier = new KaiClassifier(getAssets(), MODEL_FILE);
                cameraImgPath = PhotoUtil.start_camera(CameraActivity.this, START_CAMERA);
            }
        });
    }


}
