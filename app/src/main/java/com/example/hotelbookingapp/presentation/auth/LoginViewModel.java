package com.example.hotelbookingapp.presentation.auth;

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
    private final MutableLiveData<String> userRoleResponse = new MutableLiveData<>();
    private String loginError = "";


    @Inject
    public LoginViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public LiveData<String> getUserRoleResponse() {
        return userRoleResponse;
    }
    public String getLoginErrorResponse() {
        return loginError;
    }

    public void login(User user) {
        authRepository.login(user, new ApiCallback() {
            @Override
            public void onSuccess(String responseBody) {
                userRoleResponse.postValue(responseBody);
            }

            @Override
            public void onFailure(String errorMessage) {
                loginError = errorMessage;
            }
        });
    }
}
