package com.app.branchhealth.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by eReFeRHa on 29/2/16.
 */
public class AppUserPreference {

    public static final String MY_PREFERENCE = "MyPreference";
    public static final String KEY_HAS_ALREADY_OPEN_APPS = "hasAlreadyOpenApps";
    public static final String KEY_GCM_TOKEN = "gcmToken";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_IS_REMEMBER_ME = "isRememberMe";
    public static final String KEY_IS_LOGIN = "isLogin";

    public static final String KEY_RESPONSE_GET_REGISTER_DATA = "respGetRegisterData";
    public static final String KEY_RESPONSE_NEWS = "respNews";
    public static final String KEY_RESPONSE_PRODUCT = "respProduct";
    public static final String KEY_RESPONSE_FAQ = "respFAQ";
    public static final String KEY_RESPONSE_CONTACT_US = "respContactUs";
    public static final String KEY_RESPONSE_GET_SKU = "respGetSKU";
    public static final String KEY_RESPONSE_GET_POINT = "respGetPoint";
    public static final String KEY_RESPONSE_GET_PROFILE = "respGetProfile";
    public static final String KEY_RESPONSE_GET_POINT_HISTORY = "respGetPointHistory";

    public static final String KEY_TEMP_RESPONSE_GET_REGISTER_DATA = "respTemptGetRegisterData";
    public static final String KEY_TEMP_RESPONSE_NEWS = "respTempNews";
    public static final String KEY_TEMP_RESPONSE_PRODUCT = "respTempProduct";
    public static final String KEY_TEMP_RESPONSE_FAQ = "respTempFAQ";
    public static final String KEY_TEMP_RESPONSE_CONTACT_US = "respTempContactUs";
    public static final String KEY_TEMP_RESPONSE_GET_SKU = "respTempGetSKU";
    public static final String KEY_TEMP_RESPONSE_GET_POINT = "respTempGetPoint";
    public static final String KEY_TEMP_RESPONSE_GET_PROFILE = "respTempGetProfile";
    public static final String KEY_TEMP_RESPONSE_GET_POINT_HISTORY = "respTempGetPointHistory";

    public static SharedPreferences getPreference(Context context){
        return context.getSharedPreferences(MY_PREFERENCE, 0); // 0 - for private mode
    }

    public static void putData(Context context, String key, Object data){
        SharedPreferences.Editor editor = getPreference(context).edit();
        if(data instanceof String){
            editor.putString(key, (String) data);
        }
        else if(data instanceof Integer){
            editor.putInt(key, (Integer) data);
        }
        else if(data instanceof Boolean){
            editor.putBoolean(key, (Boolean) data);
        }
        editor.commit();
    }

    public static void removeData(Context context, String key){
        SharedPreferences.Editor editor = getPreference(context).edit();
        editor.remove(key);
        editor.commit();
    }

    public static String getStringData(Context context, String key){
        SharedPreferences pref = getPreference(context);
        return pref.getString(key, "");
    }

    public static boolean getBooleanData(Context context, String key){
        SharedPreferences pref = getPreference(context);
        return pref.getBoolean(key, false);
    }

    public static int getIntData(Context context, String key){
        SharedPreferences pref = getPreference(context);
        return pref.getInt(key, 0);
    }

    public static void clearAllData(Context context, boolean isSameuserData){
        putData(context, KEY_TEMP_RESPONSE_GET_REGISTER_DATA, getStringData(context, KEY_RESPONSE_GET_REGISTER_DATA));
        putData(context, KEY_TEMP_RESPONSE_NEWS, getStringData(context, KEY_RESPONSE_NEWS));
        putData(context, KEY_TEMP_RESPONSE_PRODUCT, getStringData(context, KEY_RESPONSE_PRODUCT));
        putData(context, KEY_TEMP_RESPONSE_FAQ, getStringData(context, KEY_RESPONSE_FAQ));
        putData(context, KEY_TEMP_RESPONSE_CONTACT_US, getStringData(context, KEY_RESPONSE_CONTACT_US));
        putData(context, KEY_TEMP_RESPONSE_GET_SKU, getStringData(context, KEY_RESPONSE_GET_SKU));

        removeData(context, KEY_RESPONSE_GET_REGISTER_DATA);
        removeData(context, KEY_RESPONSE_NEWS);
        removeData(context, KEY_RESPONSE_PRODUCT);
        removeData(context, KEY_RESPONSE_FAQ);
        removeData(context, KEY_RESPONSE_CONTACT_US);
        removeData(context, KEY_RESPONSE_GET_SKU);

        if(!isSameuserData) {
            putData(context, KEY_TEMP_RESPONSE_GET_POINT, getStringData(context, KEY_RESPONSE_GET_POINT));
            putData(context, KEY_TEMP_RESPONSE_GET_PROFILE, getStringData(context, KEY_RESPONSE_GET_PROFILE));
            putData(context, KEY_TEMP_RESPONSE_GET_POINT_HISTORY, getStringData(context, KEY_RESPONSE_GET_POINT_HISTORY));
        }
        else{
            removeData(context, KEY_TEMP_RESPONSE_GET_POINT);
            removeData(context, KEY_TEMP_RESPONSE_GET_PROFILE);
            removeData(context, KEY_TEMP_RESPONSE_GET_POINT_HISTORY);
        }
        removeData(context, KEY_RESPONSE_GET_POINT);
        removeData(context, KEY_RESPONSE_GET_PROFILE);
        removeData(context, KEY_RESPONSE_GET_POINT_HISTORY);
    }

    public static String getTempKey(String key){
        String tempKey = "";
        if(key != null){
            if(key.equalsIgnoreCase(KEY_RESPONSE_GET_REGISTER_DATA)){
                tempKey = KEY_TEMP_RESPONSE_GET_REGISTER_DATA;
            }
            else if(key.equalsIgnoreCase(KEY_RESPONSE_NEWS)){
                tempKey = KEY_TEMP_RESPONSE_NEWS;
            }
            else if(key.equalsIgnoreCase(KEY_RESPONSE_PRODUCT)){
                tempKey = KEY_TEMP_RESPONSE_PRODUCT;
            }
            else if(key.equalsIgnoreCase(KEY_RESPONSE_FAQ)){
                tempKey = KEY_TEMP_RESPONSE_FAQ;
            }
            else if(key.equalsIgnoreCase(KEY_RESPONSE_CONTACT_US)){
                tempKey = KEY_TEMP_RESPONSE_CONTACT_US;
            }
            else if(key.equalsIgnoreCase(KEY_RESPONSE_GET_SKU)){
                tempKey = KEY_TEMP_RESPONSE_GET_SKU;
            }
            else if(key.equalsIgnoreCase(KEY_RESPONSE_GET_POINT)){
                tempKey = KEY_TEMP_RESPONSE_GET_POINT;
            }
            else if(key.equalsIgnoreCase(KEY_RESPONSE_GET_PROFILE)){
                tempKey = KEY_TEMP_RESPONSE_GET_PROFILE;
            }
            else if(key.equalsIgnoreCase(KEY_RESPONSE_GET_POINT_HISTORY)){
                tempKey = KEY_TEMP_RESPONSE_GET_POINT_HISTORY;
            }
        }

        return tempKey;
    }
}
