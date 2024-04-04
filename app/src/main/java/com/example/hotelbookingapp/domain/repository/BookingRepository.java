package com.example.hotelbookingapp.domain.repository;

import com.example.hotelbookingapp.data.dto.hotel_booking.BookingDetails;
import com.example.hotelbookingapp.helper.ApiCallback;

import java.util.List;

import io.reactivex.Observable;

public interface BookingRepository {

    void bookHotel(BookingDetails bookingDetails, ApiCallback callback);

    void getBookingHistory(ApiCallback callback);

    void removeBookedHotel(String hotelName, String bookId, ApiCallback callback);
}
