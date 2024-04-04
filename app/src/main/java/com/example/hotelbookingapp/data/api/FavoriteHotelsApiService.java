package com.example.hotelbookingapp.data.api;

import com.example.hotelbookingapp.data.dto.hotel.SingleHotelItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface FavoriteHotelsApiService {

    @POST("favorites/save_hotel")
    Call<String> saveHotel(
            @Body SingleHotelItem favoriteHotel
            );

    @GET("favorites/get_saved_hotels")
    Call<List<SingleHotelItem>> getSavedHotels(
            @Query("userId") int userId
    );

    @DELETE("favorites/remove_saved_hotel/{hotelId}")
    Call<String> removeSavedHotel(String hotelId);
}
