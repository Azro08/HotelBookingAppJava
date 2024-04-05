package com.example.hotelbookingapp.data.repository;

import com.example.hotelbookingapp.data.api.FavoriteHotelsApiService;
import com.example.hotelbookingapp.data.dto.hotel.SingleHotelItem;
import com.example.hotelbookingapp.helper.ApiCallback;
import com.example.hotelbookingapp.helper.AuthManager;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteHotelsRepository {

    private final FavoriteHotelsApiService favoriteHotelsApiService;
    private final AuthManager authManager;

    @Inject
    public FavoriteHotelsRepository(FavoriteHotelsApiService favoriteHotelsApiService, AuthManager authManager) {
        this.favoriteHotelsApiService = favoriteHotelsApiService;
        this.authManager = authManager;
    }

    public void saveToFavorite(SingleHotelItem singleHotelItem, ApiCallback<String> callback) {
        Call<String> call = favoriteHotelsApiService.saveHotel(authManager.getToken(), singleHotelItem);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure("Failed " + response.message());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    public void removeFromFavorite(String hotelId, ApiCallback<String> callback) {
        int userId = authManager.getUserId();
        Call<String> call = favoriteHotelsApiService.removeSavedHotel(authManager.getToken(), hotelId, userId);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure("Failed " + response.message());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callback.onFailure("Failed " + t.getMessage());
            }
        });
    }

    public void getFavoriteHotels(ApiCallback<List<SingleHotelItem>> callback) {
        int userId = authManager.getUserId();
        Call<List<SingleHotelItem>> call = favoriteHotelsApiService.getSavedHotels(authManager.getToken(), userId);
        call.enqueue(new Callback<List<SingleHotelItem>>() {
            @Override
            public void onResponse(Call<List<SingleHotelItem>> call, Response<List<SingleHotelItem>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure("Failed " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<SingleHotelItem>> call, Throwable t) {
                callback.onFailure("Failed " + t.getMessage());
            }
        });
    }

}
