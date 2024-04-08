package com.example.hotelbookingapp.data.repository;
import com.example.hotelbookingapp.data.api.BookingApiService;
import com.example.hotelbookingapp.data.dto.hotel_booking.BookingDetails;
import com.example.hotelbookingapp.helper.ApiCallback;
import com.example.hotelbookingapp.helper.AuthManager;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingRepository {

    private final BookingApiService bookingApiService;
    private final AuthManager authManager;
    @Inject
    public BookingRepository(BookingApiService bookingApiService, AuthManager authManager) {
        this.bookingApiService = bookingApiService;
        this.authManager = authManager;
    }

    public void bookHotel(BookingDetails bookingDetails, ApiCallback<String> callback) {
        Call<String> call = bookingApiService.bookHotel(bookingDetails);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    try {
                        callback.onFailure("Error: " + response.errorBody().string());
                    } catch (IOException e) {
                        callback.onFailure("Error: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    public void getBookingHistory(ApiCallback<List<BookingDetails>> callback) {
        int userId = authManager.getUserId();
        Call<List<BookingDetails>> call = bookingApiService.getBookedHotels(userId);
        call.enqueue(new Callback<List<BookingDetails>>() {
            @Override
            public void onResponse(Call<List<BookingDetails>> call, Response<List<BookingDetails>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    try {
                        callback.onFailure("Error: " + response.errorBody().string());
                    } catch (IOException e) {
                        callback.onFailure("Error: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<BookingDetails>> call, Throwable t) {
                callback.onFailure("Error: " + t.getMessage());
            }
        });
    }

    public void cancelBooking(int bookId, ApiCallback<String> callback) {
        Call<String> call = bookingApiService.cancelBooking(bookId);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    try {
                        callback.onFailure("Error: " + response.errorBody().string());
                    } catch (IOException e) {
                        callback.onFailure("Error: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callback.onFailure("Error: " + t.getMessage());
            }
        });
    }

    public void approveBooking(int bookId, ApiCallback<String> callback) {
        Call<String> call = bookingApiService.approveBooking(bookId);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    try {
                        callback.onFailure("Error: " + response.errorBody().string());
                    } catch (IOException e) {
                        callback.onFailure("Error: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callback.onFailure("Error: " + t.getMessage());
            }
        });

    }
}
