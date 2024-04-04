package com.example.hotelbookingapp.presentation.booking;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hotelbookingapp.data.dto.hotel_booking.BookingDetails;
import com.example.hotelbookingapp.data.repository.BookingRepository;
import com.example.hotelbookingapp.helper.ApiCallback;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class BookingViewModel extends ViewModel {

    private final BookingRepository repository;

    private final MutableLiveData<String> bookingResponse = new MutableLiveData<>();

    String bookingError = "";

    LiveData<String> getBookingResponse() {
        return bookingResponse;
    }


    @Inject
    public BookingViewModel(BookingRepository repository) {
        this.repository = repository;
    }

    public void bookHotel(BookingDetails bookingDetails) {
        repository.bookHotel(bookingDetails, new ApiCallback<String>() {
            @Override
            public void onSuccess(String responseBody) {
                bookingResponse.postValue(responseBody);
            }

            @Override
            public void onFailure(String errorMessage) {
                bookingError = errorMessage;
            }
        });
    }
}
