package com.delivery.arish.arishdelivery.util;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;

import java.util.Objects;

public class LangUtil {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getCurrentLanguage(Context context){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        InputMethodSubtype ims = Objects.requireNonNull(imm).getCurrentInputMethodSubtype();
        return ims.getLocale();
    }


}
