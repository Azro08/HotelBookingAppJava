package com.example.hotelbookingapp.presentation.auth;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hotelbookingapp.data.dto.user.User;
import com.example.hotelbookingapp.data.repository.AuthRepository;
import com.example.hotelbookingapp.helper.ApiCallback;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LoginViewModel extends ViewModel {
    private final AuthRepository authRepository;
    private final MutableLiveData<User> userResponse = new MutableLiveData<>();
    private final MutableLiveData<String> auth_token = new MutableLiveData<>();
    private String loginError = "";

    @Inject
    public LoginViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public LiveData<String> getAuth_token() {
        return auth_token;
    }

    public LiveData<User> getUseResponse() {
        return userResponse;
    }

    public String getLoginErrorResponse() {
        return loginError;
    }

    public void login(User user) {
        authRepository.login(user, new ApiCallback<>() {
            @Override
            public void onSuccess(User responseBody) {
                userResponse.postValue(responseBody);
                Log.d("LoginVM",responseBody.toString());
                authRepository.authenticate(user, new ApiCallback<>() {
                    @Override
                    public void onSuccess(String responseBody) {
                        auth_token.postValue(responseBody);
                        Log.d("LoginVM",responseBody);
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        loginError = errorMessage;
                        Log.d("LoginVM",errorMessage);
                    }
                });
            }

            @Override
            public void onFailure(String errorMessage) {
                loginError = errorMessage;
                Log.d("LoginVM",errorMessage);
            }
        });
    }
}
