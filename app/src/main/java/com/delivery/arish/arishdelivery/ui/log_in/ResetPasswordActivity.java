package com.delivery.arish.arishdelivery.ui.log_in;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import com.delivery.arish.arishdelivery.R;

import java.util.Objects;

import butterknife.ButterKnife;

@SuppressLint("Registered")
public class ResetPasswordActivity extends AppCompatActivity {

    /*@SuppressWarnings("WeakerAccess")
    @BindView(R.id.rv_container_register)
    protected RelativeLayout mRelativeLayout;
*/

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Objects.requireNonNull(getSupportActionBar()).hide();
        ButterKnife.bind(this);
    }

}
