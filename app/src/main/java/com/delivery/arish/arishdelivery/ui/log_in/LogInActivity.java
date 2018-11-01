package com.delivery.arish.arishdelivery.ui.log_in;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import com.delivery.arish.arishdelivery.util.LangUtil;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
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

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("Registered")
public class LogInActivity extends BaseActivity {
    private static final String TAG = LogInActivity.class.getSimpleName();

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
    @BindView(R.id.txt_reset_password)
    TextView mTxtResetPass;


    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        if (SharedPrefManager.getInstance(getApplicationContext()).isUserLoggedIn()) {
            Intent myintent = new Intent(LogInActivity.this, MainActivity.class);
            startActivity(myintent);
            finish();

            Log.d(TAG, "LanguageDevice oncreate is  "+LangUtil.getCurentLanguage(this));

        }
    }

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

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "LanguageDevice onRestart is  "+LangUtil.getCurentLanguage(this));

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "LanguageDevice onResume is  "+LangUtil.getCurentLanguage(this));

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "LanguageDevice onStart is  "+LangUtil.getCurentLanguage(this));

    }

    private void onClickViews() {

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
        mTxtResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gooResetPass(view);
            }
        });

    }


    public void gooResetPass(View view) {
        Intent intent = new Intent(LogInActivity.this, ResetPasswordActivity.class);
        startActivity(intent);

    }

}
