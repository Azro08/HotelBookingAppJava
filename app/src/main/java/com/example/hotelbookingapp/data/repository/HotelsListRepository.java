package com.example.hotelbookingapp.data.repository;


import com.example.hotelbookingapp.data.api.HotelsListApi;
import com.example.hotelbookingapp.data.dto.hotel.HotelResponse;
import com.example.hotelbookingapp.helper.Constants;

import javax.inject.Inject;

import io.reactivex.Observable;

public class HotelsListRepository {

    private final HotelsListApi api;

    @Inject
    public HotelsListRepository(HotelsListApi api) {
        this.api = api;
    }

    public Observable<HotelResponse> getHotelsList(String regionId, String checkIn, String checkOut) {
        return api.getHotelsList(
                regionId,
                "en_US",
                checkIn,
                "REVIEW",
                1,
                "US",
                checkOut,
                Constants.API_KEY
        );
    }
}
