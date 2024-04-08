package com.example.hotelbookingapp.presentation.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.hotelbookingapp.R;
import com.example.hotelbookingapp.helper.AuthManager;
import com.example.hotelbookingapp.presentation.auth.AuthenticationActivity;
import com.example.hotelbookingapp.presentation.history.HistoryFragment;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AdminActivity extends AppCompatActivity {
    @Inject
    AuthManager authManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu resource
        getMenuInflater().inflate(R.menu.admin_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.itemLogout) {
            authManager.removeUser();
            authManager.removeToken();
            startActivity(new Intent(this, AuthenticationActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}