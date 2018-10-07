package com.barackbao.aicalligraphy.activity.genbook;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.barackbao.aicalligraphy.R;
import com.barackbao.aicalligraphy.util.DisplayUtil;
import com.barackbao.aicalligraphy.util.GenBook;
import com.bumptech.glide.Glide;


public class GenCopybookActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView genResultImg;
    private ProgressBar genLoadingPb;
    private EditText genInputEdt;
    private Button genBtn;
    private Toolbar toolbar;
    private TextView toolbarTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genbook);
        toolbar = findViewById(R.id.toolbar);
        toolbarTv = findViewById(R.id.toolbar_title_tv);
        if (null != toolbar) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (null != toolbarTv) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            toolbarTv.setText("生成字体");
        }

        initView();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                genLoadingPb.setVisibility(View.GONE);
                String url = (String) msg.obj;
//                RequestOptions options = new RequestOptions()
//                        .error(R.mipmap.errorimg)
//                        .override(50, 80);
                Glide.with(GenCopybookActivity.this).load(url).into(genResultImg);
            }
        }
    };

    private void initView() {
        genResultImg = findViewById(R.id.gen_result_img);
        genLoadingPb = findViewById(R.id.gen_loading_pb);
        genInputEdt = findViewById(R.id.gen_input_edt);
        genBtn = findViewById(R.id.gen_btn);
        genBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.gen_btn:
                final String input = genInputEdt.getText().toString().trim();
                if (TextUtils.isEmpty(input)) {
                    Toast.makeText(this, "输入框为空", Toast.LENGTH_SHORT).show();
                } else if (!DisplayUtil.isChinese(input)) {
                    genInputEdt.setText("");
                    Toast.makeText(this, "输入内容有非法字符,请重新输入", Toast.LENGTH_SHORT).show();
                } else {
                    genLoadingPb.setVisibility(View.VISIBLE);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String url = new GenBook().GetImgURL(input);
                            Message msg = Message.obtain();
                            msg.what = 0x123;
                            msg.obj = url;
                            handler.sendMessage(msg);
                        }
                    }).start();
                }
                break;
        }
    }
}
