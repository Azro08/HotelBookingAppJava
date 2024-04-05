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
public class SignupViewModel extends ViewModel {
    private final AuthRepository authRepository;
    private final MutableLiveData<String> signUpResponse = new MutableLiveData<>();
    private final MutableLiveData<String> loginError = new MutableLiveData<>();

    public LiveData<String> getSignUpResponse() {
        return signUpResponse;
    }

    @Inject
    public SignupViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }


    public void signup(User user) {
        authRepository.signup(user, new ApiCallback<String>() {
            @Override
            public void onSuccess(String responseBody) {
                signUpResponse.postValue(responseBody);
            }

            @Override
            public void onFailure(String errorMessage) {
                loginError.postValue(errorMessage);
            }
        });
    }

}
