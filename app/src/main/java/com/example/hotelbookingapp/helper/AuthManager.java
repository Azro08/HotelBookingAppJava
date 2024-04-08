package com.example.hotelbookingapp.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class AuthManager {

    private final Context context;

    public AuthManager(Context context) {
        this.context = context;
    }

    public boolean isLoggedIn() {
        String authToken = getUser();
        return !authToken.isEmpty();
    }

    // Returns Email
    private String getUser() {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constants.USER_KEY, "");
    }

    public String getUserRole() {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constants.ROLE_KEY, "");
    }

    public int getUserId() {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(Constants.USER_ID_KEY, 0);
    }

    public void saveToken(String token) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(Constants.TOKEN_KEY, token).apply();
    }

    public String getToken() {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constants.TOKEN_KEY, "");
    }

    public void removeToken() {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().remove(Constants.TOKEN_KEY).apply();
    }

    public void saveUser(String email, int id, String role) {
        Log.d("AuthManager", "saveUser: " + email + "  " + id + "  " + role);
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.USER_KEY, email);
        editor.putString(Constants.ROLE_KEY, role);
        editor.putInt(Constants.USER_ID_KEY, id);
        editor.apply();
    }

    public void removeUser() {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(Constants.USER_KEY);
        editor.remove(Constants.USER_ID_KEY);
        editor.apply();
    }
}
