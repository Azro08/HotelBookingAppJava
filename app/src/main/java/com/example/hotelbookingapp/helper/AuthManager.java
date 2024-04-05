package com.example.hotelbookingapp.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class AuthManager {

    private final Context context;

    public AuthManager(Context context) {
        this.context = context;
    }

    public boolean isLoggedIn() {
        String email = getUserEmail();
        return !email.isEmpty();
    }

    private String getUserEmail() {
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
        // Retrieve the integer value from SharedPreferences
        int userId = sharedPreferences.getInt(Constants.USER_ID_KEY, 0); // Default value is 0 if not found
        // Return the retrieved value
        return userId;
    }



    public void saveUserEmail(String email) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit()
                .putString(Constants.USER_KEY, email)
                .apply();
    }

    public void saveUserId(int id) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit()
                .putInt(Constants.USER_ID_KEY, id)
                .apply();
    }


    public void saveUserToken(String jwtToken) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit()
                .putString(Constants.TOKEN_KEY, jwtToken)
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
