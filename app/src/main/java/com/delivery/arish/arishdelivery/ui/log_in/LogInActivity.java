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

    private ProgressDialog mLoading;
    private AlertDialog.Builder mBuilder;
    private static final String PROTOCOL_CHARSET = "utf-8";

    private AnimationDrawable mAnimationDrawable;

    Context mContext;
    BaseApiService mApiService;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        Objects.requireNonNull(getSupportActionBar()).hide();

        ButterKnife.bind(this);

        mAnimationDrawable=MyAnimation.animateBackground(mRelativeLayout);

        if (SharedPrefManager.getInstance( getApplicationContext() ).isUserLoggedIn()) {
            // PostUser is already logged in. Take him to main activity
            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        mContext = this;
        mApiService = UtilsApi.getAPIService(); // meng-init yang ada di package apihelper
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
                mLoading = ProgressDialog.show(mContext, null,  getResources().getString( R.string.loging_in), true, false);
                requestLogin();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, RegisterActivity.class));
            }
        });

    }

    private void requestLogin(){


        mApiService.loginRequest(mEtEmail.getText().toString(), mEtPassword.getText().toString(),SharedPrefManager.getInstance( this ).getDeviceToken())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            Log.i("debug", "onResponse:start ");

                            mLoading.dismiss();
                            try {
                                String remoteResponse=response.body().string();

                                Log.d("JSON", remoteResponse);

                                JSONObject jsonRESULTS = new JSONObject(remoteResponse);
                                if (jsonRESULTS.getString("error").equals("false")){

                                    SharedPrefManager.getInstance( getApplicationContext() ).setLoginUser(true);

                                    Toast.makeText(mContext, jsonRESULTS.getString("error_msg"), Toast.LENGTH_SHORT).show();
                                    String id = jsonRESULTS.getString("id");
                                    String uid = jsonRESULTS.getString("uid");
                                    String name = jsonRESULTS.getJSONObject("user").getString("name");
                                    String email = jsonRESULTS.getJSONObject("user").getString("email");
                                    String phone = jsonRESULTS.getJSONObject("user").getString("phone");
                                    String imageURl = jsonRESULTS.getJSONObject("user").getString("image");
                                    String created_at = jsonRESULTS.getJSONObject("user").getString("time");
                                    String error_message = jsonRESULTS.getString("error_msg");

                                    Log.i("tagconvertstr", "["+ jsonRESULTS.getString("error_msg")+"]");


                                    Log.e("debug", "succeess: ERROR > "+error_message );
                                    SharedPrefManager.getInstance( getApplicationContext() ).saveUserId( id );
                                    SharedPrefManager.getInstance( getApplicationContext() ).saveNamesOfUsers( name );
                                    SharedPrefManager.getInstance( getApplicationContext() ).saveEmailOfUsers( email );
                                    SharedPrefManager.getInstance( getApplicationContext() ).savePhonefUsers( phone );
                                    SharedPrefManager.getInstance( getApplicationContext() ).saveImagefUsers( imageURl );
                                    Intent intent=new Intent(mContext, MainActivity
                                            .class);
                                    startActivity(intent);

                                } else {
                                    // Jika login gagal
                                    Log.e("debug", "onFailure: ERROR > " );
                                    String error_message = jsonRESULTS.getString("error_msg");

                                    Log.e("debug", "noAcount: ERROR > "+error_message );

                                    Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            mLoading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        Toast.makeText(mContext, "خطا بالاتصال بالانترنت", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void gooResetPass(View view) {
        Intent intent=new Intent(LogInActivity.this, ResetPasswordActivity.class);
        startActivity(intent);

    }

}
