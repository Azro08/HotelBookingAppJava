package com.example.hotelbookingapp.data.api;

import com.example.hotelbookingapp.data.dto.hotel_booking.BookingDetails;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BookingApiService {
    @POST("booking/book_hotel")
    Call<String> bookHotel(
            @Body BookingDetails bookingDetails
    );

    @GET("booking/get_booked_hotels")
    Call<List<BookingDetails>> getBookedHotels(
            @Query("userId") int userId
    );

    @GET("booking/get_booking_details")
    Call<BookingDetails> getBookingDetails(
            @Query("bookingId") int bookingId
    );

    @POST("booking/cancel_booking")
    Call<String> cancelBooking(
            @Query("bookingId") int bookingId
    );

    @POST("booking/approve_booking")
    Call<String> approveBooking(
            @Query("bookingId") int bookingId
    );

}
