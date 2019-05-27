package com.fyp.kitchenpal.Repository;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {
    private static SharedPrefs sharedPrefs;
    protected Context mContext;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mSharedPreferencesEditor;

    public SharedPrefs(Context context) {
        mContext = context;
        mSharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        mSharedPreferencesEditor = mSharedPreferences.edit();
    }

    public static synchronized SharedPrefs getInstance(Context context) {
        if (sharedPrefs == null) {
            sharedPrefs = new SharedPrefs(context.getApplicationContext());
        }
        return sharedPrefs;
    }

    public void loginAsUser(boolean value) {
        mSharedPreferencesEditor.putBoolean("loginAsUser", value);
        mSharedPreferencesEditor.commit();
    }

    public void saveEmail(String email) {
        mSharedPreferencesEditor.putString("email", email);
        mSharedPreferencesEditor.commit();
    }

    public void savePassword(String password) {
        mSharedPreferencesEditor.putString("password", password);
        mSharedPreferencesEditor.commit();
    }

    public void saveName(String name) {
        mSharedPreferencesEditor.putString("name", name);
        mSharedPreferencesEditor.commit();
    }

    public String getEmail() {
        return mSharedPreferences.getString("email", "");
    }

    public String getPassword() {
        return mSharedPreferences.getString("password", "");
    }

    public String getName() {
        return mSharedPreferences.getString("name", "");
    }

    public boolean isLoggedInAsUser() {
        return mSharedPreferences.getBoolean("loginAsUser", false);
    }

    public void loginAsAdmin(boolean value) {
        mSharedPreferencesEditor.putBoolean("loginAsAdmin", value);
        mSharedPreferencesEditor.commit();
    }

    public boolean isLoggedInAsAdmin() {
        return mSharedPreferences.getBoolean("loginAsAdmin", false);
    }


    public void clearPrefrences() {
        mSharedPreferencesEditor.clear().commit();
    }
}
