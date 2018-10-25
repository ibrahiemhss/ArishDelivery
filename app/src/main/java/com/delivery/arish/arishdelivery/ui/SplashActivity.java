package com.delivery.arish.arishdelivery.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.delivery.arish.arishdelivery.R;
import com.delivery.arish.arishdelivery.ui.log_in.RegisterActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        startActivity(new Intent(this, RegisterActivity.class));
    }
}
