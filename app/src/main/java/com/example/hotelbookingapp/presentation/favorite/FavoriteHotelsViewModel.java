package com.example.hotelbookingapp.presentation.favorite;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hotelbookingapp.data.dto.hotel.SingleHotelItem;
import com.example.hotelbookingapp.data.repository.FavoriteHotelsRepository;
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
public class FavoriteHotelsViewModel extends ViewModel {

    private final FavoriteHotelsRepository repository;
    private final MutableLiveData<List<SingleHotelItem>> hotelsList = new MutableLiveData<>();
    private final MutableLiveData<String> responseError = new MutableLiveData<>();

    @Inject
    public FavoriteHotelsViewModel(FavoriteHotelsRepository repository) {
        this.repository = repository;
        getFavoriteHotels();
    }

    public MutableLiveData<List<SingleHotelItem>> getHotelsList() {
        return hotelsList;
    }

    public MutableLiveData<String> getResponseError() {
        return responseError;
    }

    void refresh() {
        getFavoriteHotels();
    }

    private void getFavoriteHotels() {
        repository.getFavoriteHotels(new ApiCallback<>() {
            @Override
            public void onSuccess(List<SingleHotelItem> responseBody) {
                hotelsList.postValue(responseBody);
            }

            @Override
            public void onFailure(String errorMessage) {
                responseError.postValue("No saved hotels");
            }
        });
    }

    public void deleteHotelFromFav(String hotelId) {
        Log.d("deleteHotel", hotelId);
        repository.removeFromFavorite(hotelId, new ApiCallback<>() {
            @Override
            public void onSuccess(String responseBody) {
                refresh();
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.d("FavResponse", errorMessage);
            }
        });
        getFavoriteHotels();
    }
}
