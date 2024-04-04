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
import com.example.hotelbookingapp.helper.AuthManager;
import com.example.hotelbookingapp.presentation.admin.AdminActivity;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginFragment extends Fragment {
    @Inject
    FirebaseAuth firebaseAuth;
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
//        user.setRole("ADMIN_ROLE");
        viewModel.login(user);
        viewModel.getUseResponse().observe(getViewLifecycleOwner(), userResponse -> {
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
            Log.d("Login", viewModel.getLoginErrorResponse());
            String auth_token = viewModel.getAuth_token();
            if (userResponse.getRole().equals("ROLE_USER")) {
                Log.d("Login", userResponse.toString());
                authManager.saveUser(email, userResponse.getRole(), userResponse.getId(), auth_token);
                startActivity(new Intent(getActivity(), MainActivity.class));
            } else if (userResponse.getRole().equals("ROLE_ADMIN")) {
                Log.d("Login", userResponse.toString());
                authManager.saveUser(email, userResponse.getRole(), userResponse.getId(), auth_token);
                startActivity(new Intent(getActivity(), AdminActivity.class));
            } else {
                Log.d("Login", userResponse.toString());
                Toast.makeText(requireContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
