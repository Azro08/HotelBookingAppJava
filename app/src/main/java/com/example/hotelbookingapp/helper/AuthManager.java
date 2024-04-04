package com.example.hotelbookingapp.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class AuthManager {

    private final Context context;

    public AuthManager(Context context) {
        this.context = context;
    }

    public boolean isLoggedIn() {
        String authToken = getUser();
        return !authToken.isEmpty();
    }

    private String getUser() {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.getString(Constants.USER_KEY, "");
        return sharedPreferences.getString(Constants.USER_KEY, "");
    }

    public String getRole() {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.getString(Constants.ROLE_KEY, "");
        return sharedPreferences.getString(Constants.ROLE_KEY, "");
    }

    public String getToken() {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.getString(Constants.TOKEN_KEY, "");
        return sharedPreferences.getString(Constants.TOKEN_KEY, "");
    }

    public int getUserId() {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.getInt(Constants.USER_ID_KEY, 0);
        return sharedPreferences.getInt(Constants.USER_ID_KEY, 0);
    }

    public void saveUser(String email, String role, int id, String jwtToke) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit()
                .putString(Constants.USER_KEY, email)
                .putInt(Constants.USER_ID_KEY, id)
                .putString(Constants.TOKEN_KEY, jwtToke)
                .putString(Constants.ROLE_KEY, role)
                .apply();
    }

    public void saveRole(String role) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(Constants.USER_KEY, role).apply();
    }

    public void removeUser() {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit()
                .remove(Constants.USER_KEY)
                .remove(Constants.ROLE_KEY)
                .remove(Constants.TOKEN_KEY)
                .remove(Constants.USER_ID_KEY)
                .apply();
    }
}
