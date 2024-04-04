package com.example.hotelbookingapp.presentation.history;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hotelbookingapp.data.dto.hotel_booking.BookingDetails;
import com.example.hotelbookingapp.data.repository.BookingRepository;
import com.example.hotelbookingapp.helper.ApiCallback;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

@HiltViewModel
public class HistoryViewModel extends ViewModel {

    private final BookingRepository bookingRepository;
    private final MutableLiveData<List<BookingDetails>> bookingListResponse =
            new MutableLiveData<>();
    private final MutableLiveData<String> responseError = new MutableLiveData<>();
    private final MutableLiveData<String> cancelBookingState = new MutableLiveData<>();

    @Inject
    public HistoryViewModel(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public MutableLiveData<List<BookingDetails>> getBookingListResponse() {
        return bookingListResponse;
    }

    public MutableLiveData<String> getCancelBookingState() {
        return cancelBookingState;
    }

    public MutableLiveData<String> getResponseError() {
        return responseError;
    }

    private void getBookingHistory() {
        bookingRepository.getBookingHistory(new ApiCallback<List<BookingDetails>>() {
            @Override
            public void onSuccess(List<BookingDetails> responseBody) {
                bookingListResponse.postValue(responseBody);
            }

            @Override
            public void onFailure(String errorMessage) {
                responseError.postValue(errorMessage);
            }
        });
    }

    public void refresh() {
        getBookingHistory();
    }

    public void removeBookedHotel(int bookId) {
        bookingRepository.cancelBooking(bookId, new ApiCallback<String>() {
            @Override
            public void onSuccess(String responseBody) {
                cancelBookingState.postValue(responseBody);
            }

            @Override
            public void onFailure(String errorMessage) {
            }
        });
    }
}
