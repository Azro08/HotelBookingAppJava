package com.example.hotelbookingapp.presentation.auth;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.hotelbookingapp.MainActivity;
import com.example.hotelbookingapp.R;
import com.example.hotelbookingapp.data.dto.user.User;
import com.example.hotelbookingapp.databinding.FragmentLoginBinding;
import com.example.hotelbookingapp.domain.model.UserRole;
import com.example.hotelbookingapp.helper.AuthManager;
import com.example.hotelbookingapp.presentation.admin.AdminActivity;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginFragment extends Fragment {
    @Inject
    AuthManager authManager;
    private FragmentLoginBinding binding;
    private LoginViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        binding.textViewSignup.setOnClickListener(v ->
                NavHostFragment.findNavController(this).navigate(R.id.nav_to_signup));

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        binding.buttonLogin.setOnClickListener(v -> login());
    }

    private void login() {
        String email = binding.editTextLoginEmail.getText().toString();
        String password = binding.editTextLoginPassword.getText().toString();

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        viewModel.login(user);
        if (!Objects.equals(viewModel.getLoginErrorResponse(), "")) {
            Toast.makeText(requireContext(), viewModel.getLoginErrorResponse(), Toast.LENGTH_SHORT).show();
        }
        viewModel.getUseResponse().observe(getViewLifecycleOwner(), userResponse -> {
            Log.d("Login", userResponse.getRole());
            viewModel.getAuth_token().observe(getViewLifecycleOwner(), authTokenResponse -> {
                if (userResponse.getRole().equals(UserRole.ROLE_USER.name())) {
                    Log.d("LoginUser", userResponse.toString());
                    saveUser(email, userResponse.getRole(), userResponse.getId(), authTokenResponse);
                    startActivity(new Intent(getActivity(), MainActivity.class));
                } else if (userResponse.getRole().equals(UserRole.ROLE_ADMIN.name())) {
                    Log.d("LoginAdmin", userResponse.toString());
                    saveUser(email, userResponse.getRole(), userResponse.getId(), authTokenResponse);
                    startActivity(new Intent(getActivity(), AdminActivity.class));
                } else {
                    Log.d("LoginFailed", userResponse.toString());
                    Toast.makeText(requireContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            });

        });
    }

    private void saveUser(String email, String role, int id, String authToken) {
        Log.d("LoginSavedUser", email + " " + role + " " + id + " " + authToken);
        authManager.saveUser(email, id, role);
        authManager.saveToken(authToken);
    }


    //            switch (userRoleResponse){
//                case "USER_ROLE":
//                    authManager.saveUser(email);
//                    authManager.saveRole(userRoleResponse);
//                    startActivity(new Intent(getActivity(), MainActivity.class));
//                    break;
//                case "ADMIN_ROLE":
//                    authManager.saveUser(email);
//                    authManager.saveRole(userRoleResponse);
//                    startActivity(new Intent(getActivity(), AdminActivity.class));
//                    break;
//                default:
//                    Log.d("Login", userRoleResponse.toString());
//                    Toast.makeText(requireContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
//                    break;
//
//            }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
