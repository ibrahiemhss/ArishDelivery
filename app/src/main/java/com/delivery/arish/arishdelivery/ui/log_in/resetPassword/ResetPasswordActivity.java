package com.delivery.arish.arishdelivery.ui.log_in.resetPassword;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.delivery.arish.arishdelivery.R;
import com.delivery.arish.arishdelivery.base.BaseActivity;

import java.util.Objects;

import butterknife.ButterKnife;

@SuppressLint("Registered")
public class ResetPasswordActivity extends AppCompatActivity {

    /*@SuppressWarnings("WeakerAccess")
    @BindView(R.id.rv_container_register)
    protected RelativeLayout mRelativeLayout;
*/
    private Fragment mContentListFragment;
    private String mLocale;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_paswword);

        mContentListFragment = new FragmentSenEmail();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.contents_containerdddddd, mContentListFragment)
                .commit();
    }


}
