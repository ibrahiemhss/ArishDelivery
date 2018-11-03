package com.delivery.arish.arishdelivery.util;

import java.util.regex.Pattern;

public class EditTextUtil {

    public static boolean isEmailValid(CharSequence email) {
        return !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static int passCases(String pass) {
        int cases;
        if (pass.length() < 8) {
            cases = 6;

        } else {
            cases = 0;
            //handle your action here toast message/ snackbar or something else
        }

        return cases;
    }

    public static int isNameCase(String name) {
        int cases = 0;
        Pattern regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]");

        if (name.length() < 6) {
            cases = 1;

        } else if (regex.matcher(name).find()) {
            cases = 2;
            //handle your action here toast message/ snackbar or something else
        }

        return cases;
    }

    public static int phoneCases(String phone) {
        int cases;
        if (phone.length() < 10) {
            cases = 10;

        } else {
            cases = 0;
            //handle your action here toast message/ snackbar or something else
        }

        return cases;
    }
}
