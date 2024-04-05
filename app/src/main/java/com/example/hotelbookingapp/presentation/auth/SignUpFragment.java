package com.example.hotelbookingapp.presentation.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.hotelbookingapp.data.dto.user.User;
import com.example.hotelbookingapp.databinding.FragmentSignUpBinding;
import com.example.hotelbookingapp.domain.model.UserRole;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SignUpFragment extends Fragment {
    private FragmentSignUpBinding binding;
    private SignupViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        binding.buttonSignup.setOnClickListener(v -> signup());
        viewModel = new ViewModelProvider(this).get(SignupViewModel.class);
    }

    private void signup() {
        String email = binding.editTextSignupEmail.getText().toString();
        String password = binding.editTextSignupPassword.getText().toString();
        String rePassword = binding.editTextSignupRepPassword.getText().toString();

        if (!password.equals(rePassword)) {
            binding.editTextSignupPassword.setError("Check password");
            binding.editTextSignupRepPassword.setError("Check password");
        } else {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setPassword(password);
            newUser.setRole(UserRole.ROLE_USER.name());
            viewModel.signup(newUser);
            viewModel.getSignUpResponse().observe(getViewLifecycleOwner(), signupResponse -> {
                if (signupResponse != null) {
                    Toast.makeText(getContext(), "Signup Success", Toast.LENGTH_SHORT).show();
                    NavHostFragment.findNavController(this).popBackStack();
                } else {
                    binding.editTextSignupEmail.setError("Signup Failed");
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
