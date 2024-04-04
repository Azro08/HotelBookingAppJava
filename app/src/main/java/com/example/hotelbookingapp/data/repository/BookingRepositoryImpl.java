package com.example.hotelbookingapp.data.repository;

import android.util.Log;

import com.example.hotelbookingapp.data.api.BookingApiService;
import com.example.hotelbookingapp.data.dto.hotel_booking.BookingDetails;
import com.example.hotelbookingapp.domain.repository.BookingRepository;
import com.example.hotelbookingapp.helper.ApiCallback;
import com.example.hotelbookingapp.helper.AuthManager;
import com.example.hotelbookingapp.helper.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingRepositoryImpl implements BookingRepository {

    private final BookingApiService bookingApiService;

    @Inject
    public BookingRepositoryImpl(BookingApiService bookingApiService) {
        this.bookingApiService = bookingApiService;
    }

    @Override
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

    @Override
    public void getBookingHistory(int userId, ApiCallback<List<BookingDetails>> callback) {
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

    @Override
    public void removeBookedHotel(int bookId, ApiCallback<String> callback) {
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
}
