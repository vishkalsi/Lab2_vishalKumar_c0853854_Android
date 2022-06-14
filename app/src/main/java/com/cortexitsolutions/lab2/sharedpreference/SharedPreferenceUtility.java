package com.cortexitsolutions.lab2.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.cortexitsolutions.lab2.R;


public class SharedPreferenceUtility {

    private final SharedPreferences mSharedPreferences;
    private static final String LIST_UPDATED = "LIST_UPDATED";
    public SharedPreferenceUtility(Context context) {
        mSharedPreferences = context.getSharedPreferences(context.getString( R.string.shared_preference_file),
                Context.MODE_PRIVATE);
    }

    public boolean getListUpdated() {
        return mSharedPreferences.getBoolean(LIST_UPDATED, false);
    }

    public void setListUpdated(boolean status) {
        mSharedPreferences.edit()
                .putBoolean(LIST_UPDATED, status)
                .apply();
    }




}
