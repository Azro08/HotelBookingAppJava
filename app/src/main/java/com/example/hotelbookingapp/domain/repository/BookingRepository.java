package com.example.hotelbookingapp.domain.repository;

import com.example.hotelbookingapp.data.dto.hotel_booking.BookingDetails;
import com.example.hotelbookingapp.helper.ApiCallback;

import java.util.List;

import io.reactivex.Observable;

public interface BookingRepository {

    void bookHotel(BookingDetails bookingDetails, ApiCallback<String> callback);

    void getBookingHistory(int userId, ApiCallback<List<BookingDetails>> callback);

    void removeBookedHotel(int bookId, ApiCallback<String> callback);
}
