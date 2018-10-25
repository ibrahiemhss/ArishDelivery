package com.delivery.arish.arishdelivery.ui.log_in;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.delivery.arish.arishdelivery.R;
import com.delivery.arish.arishdelivery.data.SharedPrefManager;
import com.delivery.arish.arishdelivery.internet.BaseApiService;
import com.delivery.arish.arishdelivery.internet.UtilsApi;
import com.delivery.arish.arishdelivery.presenter.LogInActivityPresenter;
import com.delivery.arish.arishdelivery.ui.MainActivity;
import com.delivery.arish.arishdelivery.util.MyAnimation;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        // Objects.requireNonNull(getSupportActionBar()).hide();

        ButterKnife.bind(this);
        mAnimationDrawable = MyAnimation.animateBackground(mRelativeLayout);
        if (SharedPrefManager.getInstance(getApplicationContext()).isUserLoggedIn()) {
            //    Intent intent = new Intent(LogInActivity.this, MainActivity.class);
            //   startActivity(intent);
            //   finish();
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
        mEtEmail = (EditText) findViewById(R.id.etEmail);
        mEtPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogInActivityPresenter logInActivityPresenter = new LogInActivityPresenter(LogInActivity.this);
                logInActivityPresenter.requestLogin(mEtEmail.getText().toString(), mEtPassword.getText().toString(), "ytyty");
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
