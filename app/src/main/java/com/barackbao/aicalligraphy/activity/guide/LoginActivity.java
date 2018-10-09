package com.barackbao.aicalligraphy.activity.guide;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.barackbao.aicalligraphy.R;
import com.barackbao.aicalligraphy.activity.HomeActivity;
import com.barackbao.aicalligraphy.activity.base.BaseActivity;
import com.barackbao.aicalligraphy.network.RequestCenter;
import com.barackbao.aicalligraphy.util.StaticClass;
import com.barackbao.baselib.okhttp.listener.DisposeDataListener;
import com.barackbao.baselib.okhttp.request.RequestParams;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText usernameEdt;
    private EditText passwordEdt;
    private Button loginInBtn;
    private TextView loginForgetPasswordTv;
    private ProgressBar loginRb;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        usernameEdt = findViewById(R.id.login_username_edt);
        passwordEdt = findViewById(R.id.login_password_edt);
        loginInBtn = findViewById(R.id.login_in_btn);
        loginForgetPasswordTv = findViewById(R.id.login_forget_password_tv);
        loginRb = findViewById(R.id.login_rb);
        loginInBtn.setOnClickListener(this);
        loginForgetPasswordTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_in_btn:
                final String username = usernameEdt.getText().toString().trim();
                String password = passwordEdt.getText().toString().trim();
                if (!TextUtils.isEmpty(username) & !TextUtils.isEmpty(password)) {
                    loginRb.setVisibility(View.VISIBLE);
                    Map<String, String> map = new HashMap<>();
                    map.put("account", username);
                    map.put("password", password);
                    RequestParams params = new RequestParams(map);
                    RequestCenter.login(new DisposeDataListener() {
                        @Override
                        public void onSuccess(Object responseObj) {
                            loginRb.setVisibility(View.GONE);
                            StaticClass.currentUserName = username;
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onFailure(Object responseObj) {
                            loginRb.setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                        }
                    }, params);
                } else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.login_forget_password_tv:
                break;
        }
    }
}
