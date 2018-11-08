package com.delivery.arish.arishdelivery.ui.log_in;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.delivery.arish.arishdelivery.R;
import com.delivery.arish.arishdelivery.base.BaseActivity;
import com.delivery.arish.arishdelivery.data.Contract;
import com.delivery.arish.arishdelivery.data.SharedPrefManager;
import com.delivery.arish.arishdelivery.mvp.presenter.LogInPresenter;
import com.delivery.arish.arishdelivery.ui.Main.MainActivity;
import com.delivery.arish.arishdelivery.ui.log_in.resetPassword.ResetPasswordActivity;
import com.google.firebase.iid.FirebaseInstanceId;

import butterknife.BindView;

@SuppressLint("Registered")
public class LogInActivity extends BaseActivity {
    private static final String TAG = "LogInActivity";

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
        /*first thing call SharedPrefManager that
        have sharedpreferences object we save all we want inside it
        here we want be sure if there saved value true in  isUserLoggedIn
         method  this method will save inside it boolean value true when the user
          log in first time and false value when the user log out*/
        if (SharedPrefManager.getInstance(getApplicationContext()).isUserLoggedIn()) {
            Intent myIntent = new Intent(LogInActivity.this, MainActivity.class);
            startActivity(myIntent);
            finish();

        }
        Log.d(TAG,"mytoken "+FirebaseInstanceId.getInstance().getToken());
        Toast.makeText(this, "mytoken "+FirebaseInstanceId.getInstance().getToken(), Toast.LENGTH_LONG).show();

    }


    @SuppressWarnings("SameReturnValue")
    @Override
    protected int getResourceLayout() {//method come from BasActivity initialize setContentView method
        return R.layout.activity_log_in;
    }

    @Override
    protected void init() {//method come from BasActivity
    }

    @Override
    protected void setListener() {//method come from BasActivity
        onClickViews();

    }


    private void onClickViews() {//method onclick


        btnLogin.setOnClickListener(new View.OnClickListener() {//when click On Log in Button
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {


                LogInPresenter logInPresenter = new LogInPresenter(LogInActivity.this);//LogInPresenter

                logInPresenter.requestLogin(//call requestLogin method to make Log in request
                        mEtEmail.getText().toString(), mEtPassword.getText().toString(), FirebaseInstanceId.getInstance().getToken());
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//when click on Register  Button
                startActivity(new Intent(LogInActivity.this, RegisterActivity.class));
            }
        });


        mTxtResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//when click on reset password textView
                Intent intent = new Intent(LogInActivity.this, ResetPasswordActivity.class);
                startActivity(intent);
            }
        });

    }

}
