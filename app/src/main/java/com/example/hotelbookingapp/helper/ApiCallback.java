package com.example.hotelbookingapp.helper;

public interface ApiCallback<T> {
    void onSuccess(T responseBody);
    void onFailure(String errorMessage);
}