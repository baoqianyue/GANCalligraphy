package com.barackbao.aicalligraphy.activity.genbook;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class TestWordActivity extends AppCompatActivity {

    private static final String TAG = "TestWordActivity";

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
//    private PieChart mChart;

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

        testCommitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton selected = findViewById(authorRbg.getCheckedRadioButtonId());
                if (null != currentWord) {
                    if (null != selected) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(TestWordActivity.this);
                        builder.setTitle("结果");
                        builder.setCancelable(false);
                        LayoutInflater inflater = TestWordActivity.this.getLayoutInflater();
                        View view1 = inflater.inflate(R.layout.chart_probability, null);
                        PieChart chart = view1.findViewById(R.id.word_proba_pc);
                        chart.setHoleRadius(60f);
                        chart.setTransparentCircleRadius(64f);
                        chart.setDrawHoleEnabled(true);
                        chart.setRotationEnabled(true);
                        chart.setUsePercentValues(true);
                        chart.setCenterText("相似程度");
                        chart.setData(getPieData(currentWord.getFindAuthorName(),
                                Float.parseFloat(currentWord.getProbability().substring(0,
                                        currentWord.getProbability().length() - 1))));
                        Legend legend = chart.getLegend();
                        legend.setXEntrySpace(7f);
                        legend.setYEntrySpace(5f);
                        chart.animateXY(1000, 1000);
                        builder.setView(view1)
                                .setPositiveButton("继续", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        TestWord word = changeWord();
                                        Log.e(TAG, "current img" + word.getFindWordsId());
                                        RequestOptions options = new RequestOptions()
                                                .error(R.mipmap.errorimg)
                                                .placeholder(R.mipmap.placeholder);
                                        Glide.with(TestWordActivity.this)
                                                .load(word.getWordsUrl())
                                                .apply(options)
                                                .into(testWordImg);
                                        yanzhenqinRb.setChecked(false);
                                        ouyangxunRb.setChecked(false);
                                        liugongquanRb.setChecked(false);
                                        zhaomengfuRb.setChecked(false);
//                                        chart.setData(getPieData(word.getFindAuthorName(),
//                                                Float.parseFloat(word.getProbability().substring(0,
//                                                        word.getProbability().length() - 1))));
//                                        chart.invalidate();
                                    }
                                })
                                .setNegativeButton("退出", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        finish();
                                    }
                                }).show();

                    } else {
                        Toast.makeText(TestWordActivity.this, "请选择一位书法家", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });
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

    private TestWord changeWord() {
        int index = dataList.indexOf(currentWord);
        if (index + 1 < dataList.size() - 1) {
            currentWord = dataList.get(index + 1);
            return currentWord;
        } else {
            currentWord = dataList.get(0);
            return currentWord;
        }
    }


    private PieData getPieData(String author, float proba) {
        ArrayList<String> xValues = new ArrayList<>();
        xValues.add(author);
        xValues.add("其他");
        ArrayList<Entry> yValues = new ArrayList<>();

        float one = proba;
        float two = 100 - proba;

        yValues.add(new Entry(one, 0));
        yValues.add(new Entry(two, 1));

        PieDataSet pieDataSet = new PieDataSet(yValues, "书法家");
        pieDataSet.setSliceSpace(0f);
        ArrayList<Integer> colors = new ArrayList<>();

        colors.add(Color.rgb(205, 205, 205));
        colors.add(Color.rgb(114, 188, 223));
        pieDataSet.setColors(colors);

        PieData pieData = new PieData(xValues, pieDataSet);
        return pieData;
    }


    private void requestData() {
        RequestCenter.requestTestWords(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                dataList.clear();
                JSONArray jsonArray = (JSONArray) responseObj;
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        TestWord word = new Gson().fromJson(jsonArray.get(i).toString(), TestWord.class);
                        dataList.add(word);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.e(TAG, " url: " + dataList.get(0).getWordsUrl());
                RequestOptions options = new RequestOptions()
                        .error(R.mipmap.errorimg)
                        .placeholder(R.mipmap.placeholder);
                Glide.with(getApplicationContext())
                        .load(dataList.get(0).getWordsUrl())
                        .into(testWordImg);
                currentWord = dataList.get(0);
            }

            @Override
            public void onFailure(Object responseObj) {
                Toast.makeText(TestWordActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
