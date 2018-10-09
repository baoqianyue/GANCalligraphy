package com.barackbao.aicalligraphy.activity.guide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.barackbao.aicalligraphy.R;
import com.barackbao.aicalligraphy.network.RequestCenter;
import com.barackbao.baselib.okhttp.listener.DisposeDataListener;
import com.barackbao.baselib.okhttp.request.RequestParams;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "RegisterActivity";

    private Toolbar mToolbar;
    private TextView mToolbarTitleTv;
    private EditText usernameEdt;
    private EditText passwordEdt;
    private EditText passwordAgainEdt;
    private Button registeredBtn;
    private RadioGroup userAgeRbg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mToolbar = findViewById(R.id.toolbar);
        mToolbarTitleTv = findViewById(R.id.toolbar_title_tv);

        if (null != mToolbar) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (null != mToolbarTitleTv) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            mToolbarTitleTv.setText("注册");
        }

        initView();
    }

    private void initView() {
        usernameEdt = findViewById(R.id.username_edt);
        passwordEdt = findViewById(R.id.password_edt);
        passwordAgainEdt = findViewById(R.id.password_again_edt);
        registeredBtn = findViewById(R.id.registered_btn);
        userAgeRbg = findViewById(R.id.user_age_rdp);
        registeredBtn.setOnClickListener(this);
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
            case R.id.registered_btn:
                String username = usernameEdt.getText().toString().trim();
                String password = passwordEdt.getText().toString().trim();
                String password_again = passwordAgainEdt.getText().toString().trim();
                RadioButton selected = findViewById(userAgeRbg.getCheckedRadioButtonId());
                String userage = (String) selected.getText();
                //判空
                if (!TextUtils.isEmpty(username) & !TextUtils.isEmpty(password)
                        & !TextUtils.isEmpty(password_again) & !TextUtils.isEmpty(userage)) {
                    //判断两次输入的密码是否一致
                    if (!password.equals(password_again)) {
                        Toast.makeText(this, "两次输入的密码不一致，请重新输入", Toast.LENGTH_SHORT).show();
                        passwordEdt.setText("");
                        passwordAgainEdt.setText("");
                    } else {
                        Map<String, String> map = new HashMap<>();
                        map.put("account", username);
                        map.put("password", password);
                        map.put("age", userage);
                        map.put("avatar", null);
                        RequestParams params = new RequestParams(map);
                        RequestCenter.register(new DisposeDataListener() {
                            @Override
                            public void onSuccess(Object responseObj) {
                                Log.e(TAG, "onSuccess: " + responseObj.toString());
                                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void onFailure(Object responseObj) {
                                Toast.makeText(RegisterActivity.this, "注册失败: " + responseObj.toString(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }, params);
                    }
                } else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}

