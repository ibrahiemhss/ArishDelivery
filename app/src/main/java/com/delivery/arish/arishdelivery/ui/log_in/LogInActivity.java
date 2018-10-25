package com.delivery.arish.arishdelivery.ui.log_in;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.delivery.arish.arishdelivery.R;
import com.delivery.arish.arishdelivery.data.SharedPrefManager;
import com.delivery.arish.arishdelivery.mvp.presenter.LogInPresenter;
import com.delivery.arish.arishdelivery.ui.Main.MainActivity;
import com.delivery.arish.arishdelivery.util.MyAnimation;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("Registered")
public class LogInActivity extends AppCompatActivity {

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.rv_container_login)
    protected RelativeLayout mRelativeLayout;

    @BindView(R.id.etEmail)
    EditText mEtEmail;
    @BindView(R.id.etPassword)
    EditText mEtPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.btnRegister)
    Button btnRegister;


    private AnimationDrawable mAnimationDrawable;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        ButterKnife.bind(this);
        mAnimationDrawable = MyAnimation.animateBackground(mRelativeLayout);
        if (SharedPrefManager.getInstance(getApplicationContext()).isUserLoggedIn()) {
                Intent intent = new Intent(LogInActivity.this, MainActivity.class);
               startActivity(intent);
               finish();
        }
        initComponents();

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mAnimationDrawable != null && !mAnimationDrawable.isRunning()) {
            // start the animation
            mAnimationDrawable.start();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAnimationDrawable != null && mAnimationDrawable.isRunning()) {
            // stop the animation
            mAnimationDrawable.stop();
        }
    }


    private void initComponents() {

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogInPresenter logInPresenter = new LogInPresenter(LogInActivity.this);
                logInPresenter.requestLogin(mEtEmail.getText().toString(), mEtPassword.getText().toString(), "ytyty");
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this, RegisterActivity.class));
            }
        });

    }


    public void gooResetPass(View view) {
        Intent intent = new Intent(LogInActivity.this, ResetPasswordActivity.class);
        startActivity(intent);

    }

}
