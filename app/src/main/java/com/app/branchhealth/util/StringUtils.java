package com.app.branchhealth.util;

import android.content.Context;

import java.util.regex.Pattern;

public class StringUtils {
    public final static Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9]{8,32}$");
    public final static Pattern PASSWORD_PATTERN = Pattern.compile("((?=.*\\d)(?=.*[a-zA-Z0-9_-]).{8,32})");
    public final static Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+");

    public static boolean checkEmail(String email){
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }

    public static String capitalizeWord(String word){
        String rsWord = word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
        return rsWord;
    }

    public static int getStringIdentifier(Context context, String name){
        return context.getResources().getIdentifier(name, "string", context.getPackageName());
    }
}
