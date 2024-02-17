package com.example.hotelbookingapp.helper;

public interface ApiCallback {
    void onSuccess(String responseBody);
    void onFailure(String errorMessage);
}