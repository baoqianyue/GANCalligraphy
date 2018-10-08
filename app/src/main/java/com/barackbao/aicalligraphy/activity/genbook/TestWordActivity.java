package com.barackbao.aicalligraphy.activity.genbook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.barackbao.aicalligraphy.R;
import com.barackbao.aicalligraphy.model.TestWord;
import com.barackbao.aicalligraphy.network.RequestCenter;
import com.barackbao.baselib.okhttp.listener.DisposeDataListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class TestWordActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView toolbarTitleTv;
    private ImageView testWordImg;
    private RadioButton yanzhenqinRb;
    private RadioButton liugongquanRb;
    private RadioButton zhaomengfuRb;
    private RadioButton ouyangxunRb;
    private Button testCommitBtn;
    private RadioGroup authorRbg;
    private TestWord currentWord;

    private ArrayList<TestWord> dataList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testword);
        toolbar = findViewById(R.id.toolbar);
        toolbarTitleTv = findViewById(R.id.toolbar_title_tv);
        if (null != toolbar) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (null != toolbarTitleTv) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            toolbarTitleTv.setText("辨识字体");
        }

        initView();
    }

    private void initView() {
        testWordImg = findViewById(R.id.test_word_img);
        yanzhenqinRb = findViewById(R.id.yanzhenqin_rb);
        liugongquanRb = findViewById(R.id.liugongquan_rb);
        zhaomengfuRb = findViewById(R.id.zhaomengfu_rb);
        ouyangxunRb = findViewById(R.id.ouyangxun_rb);
        testCommitBtn = findViewById(R.id.test_commit_btn);
        authorRbg = findViewById(R.id.author_rbg);
        requestData();
        monitorRadioGroup();

        testCommitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton selected = findViewById(authorRbg.getCheckedRadioButtonId());
            }
        });
    }

    private void monitorRadioGroup() {
        authorRbg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.yanzhenqin_rb:

                        break;
                    case R.id.liugongquan_rb:

                        break;
                    case R.id.zhaomengfu_rb:

                        break;
                    case R.id.ouyangxun_rb:

                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void requestData() {
        RequestCenter.requestTestWords(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                JSONArray jsonArray = (JSONArray) responseObj;
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        TestWord word = new Gson().fromJson(jsonArray.get(i).toString(), TestWord.class);
                        dataList.add(word);
                        RequestOptions options = new RequestOptions()
                                .error(R.mipmap.errorimg)
                                .placeholder(R.mipmap.placeholder);
                        Glide.with(TestWordActivity.this).load(dataList.get(0)).apply(options).into(testWordImg);
                        currentWord = dataList.get(0);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Object responseObj) {
                Toast.makeText(TestWordActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
