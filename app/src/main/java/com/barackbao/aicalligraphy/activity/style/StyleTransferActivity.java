package com.barackbao.aicalligraphy.activity.style;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.barackbao.aicalligraphy.R;
import com.barackbao.aicalligraphy.network.HttpConstants;
import com.barackbao.aicalligraphy.util.PhotoUtil;
import com.barackbao.aicalligraphy.util.RequestPermission;
import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.github.lizhangqu.coreprogress.ProgressHelper;
import io.github.lizhangqu.coreprogress.ProgressUIListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by BarackBao on 2019-07-14;
 * <p>
 * 书法作品风格转换
 */
public class StyleTransferActivity extends AppCompatActivity {
    private static final int USE_PHOTO = 1001;
    private static final int START_CAMERA = 1002;
    private String cameraImgPath;


    private static final String TAG = "StyleTransfer.class";


    private Toolbar toolbar;
    private TextView toolbarTitleTv;
    private ImageView cameraImg;
    private Button startCameraBtn;
    private Button getFromAlbumBtn;

    private RadioButton shanshuiRb;
    private RadioButton xingyeRb;
    private RadioButton qinglvRb;

    private static final String TYPE_STYLE_SHANSHUI = "shanshui";
    private static final String TYPE_STYLE_XINGYE = "xingye";
    private static final String TYPE_STYLE_QINGLV = "shanshui1";

    private static final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(1000, TimeUnit.MINUTES)
            .readTimeout(1000, TimeUnit.MINUTES)
            .writeTimeout(1000, TimeUnit.MINUTES)
            .build();

    private ProgressBar uploadPb;

    private File preImg;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_styletrans);

        toolbar = findViewById(R.id.toolbar);
        toolbarTitleTv = findViewById(R.id.toolbar_title_tv);
        if (null != toolbar) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (null != toolbarTitleTv) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            toolbarTitleTv.setText("作品风格转换");
        }


        //申请拍照权限
        RequestPermission.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA},
                new RequestPermission.OnPermissionRequestListener() {

                    @Override
                    public void onGranted() {

                    }

                    @Override
                    public void onDenied(List<String> deniedList) {
                        Toast.makeText(StyleTransferActivity.this, "拒绝权限将无法正常使用",
                                Toast.LENGTH_SHORT).show();
                    }
                });

        initView();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        boolean isShanshuiChecked = shanshuiRb.isChecked();
        boolean isXingyeChecked = xingyeRb.isChecked();
        boolean isQinglvChecked = qinglvRb.isChecked();
        String styleType = null;
        if (isShanshuiChecked) {
            styleType = TYPE_STYLE_SHANSHUI;
        } else if (isXingyeChecked) {
            styleType = TYPE_STYLE_XINGYE;
        } else if (isQinglvChecked) {
            styleType = TYPE_STYLE_QINGLV;
        }


        //获取拍照后的图像
        if (requestCode == START_CAMERA && resultCode == Activity.RESULT_OK) {
            Glide.with(this).load(cameraImgPath).into(cameraImg);
            boolean imgExist = imgIsExit(cameraImgPath);
            if (imgExist) {
//                Toast.makeText(this, "开始上传" + preImg.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                // todo

                upload(preImg, styleType);
                try {
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
//            Bitmap bitmap = PhotoUtil.getScaleBitmap(cameraImgPath);

//            Toast.makeText(this, "label： " + res.get(0) + " the prob is：" + res.get(1), Toast.LENGTH_SHORT).show();
        } else if (requestCode == USE_PHOTO && resultCode == Activity.RESULT_OK) {
            // 从相册获取
            if (data == null) {
                return;
            }
            Uri imgUri = data.getData();
            Log.e(TAG, "resultData: " + imgUri);
            Glide.with(this).load(imgUri).into(cameraImg);
            String imgPath = PhotoUtil.get_path_from_URI(this, imgUri);
            Bitmap bitmap = PhotoUtil.getScaleBitmap(imgPath);
            Log.e(TAG, "photo url: " + imgPath);
            boolean imgExist = imgIsExit(imgPath);
            if (imgExist) {
//                Toast.makeText(this, "开始上传" + preImg.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                upload(preImg, styleType);
            }


        }
    }

    private void upload(File img, String type) {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
        Request.Builder builder = new Request.Builder();
        builder.header("Authorization", "Client-ID " + "123");
        builder.url(HttpConstants.STYLE);

        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder();
        bodyBuilder.setType(MultipartBody.FORM)
                .addFormDataPart("styleType", type)
                .addFormDataPart("file", img.getName(), RequestBody.create(MediaType.parse("image/*"), img));
//        bodyBuilder.addFormDataPart("test", img.getName(), RequestBody.create(null, img));
        MultipartBody build = bodyBuilder.build();
        RequestBody requestBody = ProgressHelper.withProgress(build, new ProgressUIListener() {

            //if you don't need this method, don't override this methd. It isn't an abstract method, just an empty method.
            @Override
            public void onUIProgressStart(long totalBytes) {
                super.onUIProgressStart(totalBytes);
                Log.e(TAG, "onUIProgressStart:" + totalBytes);
                Toast.makeText(getApplicationContext(), "开始上传：" + totalBytes, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUIProgressChanged(long numBytes, long totalBytes, float percent, float speed) {
                Log.e(TAG, "=============start===============");
                Log.e(TAG, "numBytes:" + numBytes);
                Log.e(TAG, "totalBytes:" + totalBytes);
                Log.e(TAG, "percent:" + percent);
                Log.e(TAG, "speed:" + speed);
                Log.e(TAG, "============= end ===============");
                uploadPb.setProgress((int) (100 * percent));

            }

            //if you don't need this method, don't override this methd. It isn't an abstract method, just an empty method.
            @Override
            public void onUIProgressFinish() {
                super.onUIProgressFinish();
                Log.e(TAG, "onUIProgressFinish:");
                Toast.makeText(getApplicationContext(), "结束上传", Toast.LENGTH_SHORT).show();
            }
        });


        builder.post(requestBody);

        Call call = okHttpClient.newCall(builder.build());

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "=============onFailure=============== :" + e.toString());
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e(TAG, "=============onResponse===============");
                Log.e(TAG, "request headers:" + response.request().headers());

                byte[] bytes = response.body().bytes();
                final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(StyleTransferActivity.this).load(bitmap).into(cameraImg);
                    }
                });
            }
        });
    }


    private boolean imgIsExit(String imgPath) {
        try {
            preImg = new File(imgPath);
            if (!preImg.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private void initView() {
        cameraImg = findViewById(R.id.style_camera_img);
        startCameraBtn = findViewById(R.id.style_start_camera_btn);
        getFromAlbumBtn = findViewById(R.id.style_get_from_album);
        uploadPb = findViewById(R.id.upload_progress);
        shanshuiRb = findViewById(R.id.shanshui_rb);
        xingyeRb = findViewById(R.id.xingye_rb);
        qinglvRb = findViewById(R.id.qinglv_rb);

        //拍照
        startCameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraImgPath = PhotoUtil.start_camera(StyleTransferActivity.this, START_CAMERA);
            }
        });

        //从相册获取
        getFromAlbumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhotoUtil.use_photo(StyleTransferActivity.this, USE_PHOTO);
            }
        });

    }
}
