package com.example.hotelbookingapp.data.api;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface FavoriteHotelsApiService {

    @POST("favorites/save_hotel")
    Call<String> saveHotel();

    @GET("favorites/get_saved_hotels")
    Call<String> getSavedHotels();

    @DELETE("favorites/remove_saved_hotel/{hotelId}")
    Call<String> removeSavedHotel(int hotelId);
}
