package com.barackbao.aicalligraphy.activity.guide;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.barackbao.aicalligraphy.R;

public class GuideActivity extends AppCompatActivity {

    private Button guideLoginBtn;
    private Button guideRegisterBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        guideLoginBtn = findViewById(R.id.guide_login_btn);
        guideRegisterBtn = findViewById(R.id.guide_register_btn);

        guideLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GuideActivity.this, LoginActivity.class));
            }
        });

        guideRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GuideActivity.this, RegisterActivity.class));
            }
        });
    }
}
