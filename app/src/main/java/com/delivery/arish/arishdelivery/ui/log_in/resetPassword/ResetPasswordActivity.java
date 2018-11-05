package com.delivery.arish.arishdelivery.ui.log_in.resetPassword;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.delivery.arish.arishdelivery.R;

@SuppressLint("Registered")
public class ResetPasswordActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_paswword);

        Fragment mContentListFragment = new FragmentSenEmail();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.contents_container, mContentListFragment)
                .commit();
    }


}
