package com.example.hotelbookingapp.data.api;

import com.example.hotelbookingapp.data.dto.user.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    @POST("/api/auth/signin")
    Call<User> login(@Body User user);

    @POST("/api/auth/signup")
    Call<String> signup(@Body User user);

    @POST("auth/authenticate")
    Call<String> authenticate(@Body User user);

}
