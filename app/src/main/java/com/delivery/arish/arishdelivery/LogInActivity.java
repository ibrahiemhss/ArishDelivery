package com.delivery.arish.arishdelivery;

import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.delivery.arish.arishdelivery.util.MyAnimation;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogInActivity extends AppCompatActivity {

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.rv_container_login)
    protected RelativeLayout mRelativeLayout;


    private AnimationDrawable mAnimationDrawable;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        Objects.requireNonNull(getSupportActionBar()).hide();

        ButterKnife.bind(this);

        mAnimationDrawable=MyAnimation.animateBackground(mRelativeLayout);

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
}
