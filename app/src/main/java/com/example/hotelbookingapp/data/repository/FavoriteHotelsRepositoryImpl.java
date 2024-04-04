package com.example.hotelbookingapp.data.repository;

import com.example.hotelbookingapp.data.api.FavoriteHotelsApiService;
import com.example.hotelbookingapp.domain.repository.FavoriteHotelsRepository;
import com.example.hotelbookingapp.helper.AuthManager;

import javax.inject.Inject;

public class FavoriteHotelsRepositoryImpl{

    private final FavoriteHotelsApiService favoriteHotelsApiService;
    private final AuthManager authManager;

    @Inject
    public FavoriteHotelsRepositoryImpl(FavoriteHotelsApiService favoriteHotelsApiService, AuthManager authManager) {
        this.favoriteHotelsApiService = favoriteHotelsApiService;
        this.authManager = authManager;
    }



}
