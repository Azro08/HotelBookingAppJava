package com.example.hotelbookingapp.data.api;

import com.example.hotelbookingapp.data.dto.hotel.SingleHotelItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FavoriteHotelsApiService {

    @POST("favorites/save_hotel")
    @Headers(
            "Content-Type: application/json"
    )
    Call<String> saveHotel(
//            @Header("Authorization") String token,
            @Body SingleHotelItem favoriteHotel
            );

    @GET("favorites/get_saved_hotels")
    @Headers(
            "Content-Type: application/json"
    )
    Call<List<SingleHotelItem>> getSavedHotels(
            @Header("Authorization") String token,
            @Query("userId") int userId
    );

    @DELETE("favorites/remove_saved_hotel/{hotelId}/{userId}")
    @Headers(
            "Content-Type: application/json"
    )
    Call<String> removeSavedHotel(
            @Header("Authorization") String token,
            @Path("hotelId") String hotelId,
            @Path("userId") int userId
    );
}
