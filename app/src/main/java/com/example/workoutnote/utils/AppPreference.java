package com.example.workoutnote.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreference {
    private static SharedPreferences mPreference;
    private static final String APP_PREFERENCE = "shared_pref";
    private static final String URL_KEY = "key_pref";

    public static void initPref(Context context) {
        mPreference = context.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE);
    }

    public static void saveUrl(String url) {
        mPreference.edit()
                .putString(URL_KEY, url)
                .apply();
    }

    public static String getUrl() {
        return mPreference.getString(URL_KEY, "");
    }
}
