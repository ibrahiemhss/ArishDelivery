package com.delivery.arish.arishdelivery.ui.log_in;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.delivery.arish.arishdelivery.R;
import com.delivery.arish.arishdelivery.base.BaseActivity;
import com.delivery.arish.arishdelivery.data.SharedPrefManager;
import com.delivery.arish.arishdelivery.mvp.presenter.LogInPresenter;
import com.delivery.arish.arishdelivery.ui.Main.MainActivity;
import com.delivery.arish.arishdelivery.ui.log_in.resetPassword.ResetPasswordActivity;

import butterknife.BindView;

@SuppressLint("Registered")
public class LogInActivity extends BaseActivity {
    // --Commented out by Inspection (03/11/18 02:00 م):private static final String TAG = LogInActivity.class.getSimpleName();

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.rv_container_login)
    protected RelativeLayout mRelativeLayout;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.etEmail)
    EditText mEtEmail;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.etPassword)
    EditText mEtPassword;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.btnRegister)
    Button btnRegister;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.txt_reset_password)
    TextView mTxtResetPass;


    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        if (SharedPrefManager.getInstance(getApplicationContext()).isUserLoggedIn()) {
            Intent myIntent = new Intent(LogInActivity.this, MainActivity.class);
            startActivity(myIntent);
            finish();

        }
    }
    @SuppressWarnings("SameReturnValue")
    @Override
    protected int getResourceLayout() {
        return R.layout.activity_log_in;
    }

    @Override
    protected void init() {
    }

    @Override
    protected void setListener() {
        onClickViews();

    }


    private void onClickViews() {

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
        mTxtResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gooResetPass();
            }
        });

    }


    private void gooResetPass() {
        Intent intent = new Intent(LogInActivity.this, ResetPasswordActivity.class);
        startActivity(intent);

    }

}
