package com.delivery.arish.arishdelivery.util;

import android.graphics.drawable.AnimationDrawable;
import android.widget.RelativeLayout;

public class MyAnimation {

    public static AnimationDrawable animateBackground(RelativeLayout mRelativeLayout) {
        // init constraintLayout
        AnimationDrawable animationDrawable = (AnimationDrawable) mRelativeLayout.getBackground();// initializing animation drawable by getting background from constraint layout
        animationDrawable.setEnterFadeDuration(5000);// setting enter fade animation duration to 5 seconds
        animationDrawable.setExitFadeDuration(2000);// setting exit fade animation duration to 2 seconds

        return animationDrawable;
    }
}
