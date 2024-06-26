package com.example.hotelbookingapp.data.repository;

import androidx.annotation.NonNull;

import com.example.hotelbookingapp.data.api.AuthService;
import com.example.hotelbookingapp.data.dto.user.User;
import com.example.hotelbookingapp.helper.ApiCallback;

import java.io.IOException;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {
    private final AuthService authService;

    @Inject
    public AuthRepository(AuthService authService) {
        this.authService = authService;
    }

    public void login(User user, ApiCallback<User> callback) {
        Call<User> call = authService.login(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    try {
                        callback.onFailure("Login in failed. Error: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                        callback.onFailure("Login in failed. Error: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callback.onFailure("Login in failed. Error: " + t.getMessage());
            }
        });
    }

    public void signup(User user, ApiCallback<String> callback) {
        Call<String> call = authService.signup(user);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    try {
                        callback.onFailure("Sign up failed. Error: " + response.errorBody().string());
                    } catch (IOException e) {
                        callback.onFailure("Sign up failed. Error: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callback.onFailure("Sign up failed. Error: " + t.getMessage());
            }
        });
    }

    public void authenticate(User user, ApiCallback<String> callback) {
        Call<String> call = authService.authenticate(user);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse( Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    try {
                        callback.onFailure("Sign up failed. Error: " + response.errorBody().string());
                    } catch (IOException e) {
                        callback.onFailure("Sign up failed. Error: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callback.onFailure("Authentication Error: " + t.getMessage());
            }
        });
    }

}
