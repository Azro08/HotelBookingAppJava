package com.example.hotelbookingapp.di;

import android.content.Context;

import com.example.hotelbookingapp.data.api.BookingApiService;
import com.example.hotelbookingapp.data.api.FavoriteHotelsApiService;
import com.example.hotelbookingapp.data.api.HotelDetailsApi;
import com.example.hotelbookingapp.data.api.HotelsListApi;
import com.example.hotelbookingapp.data.api.LocationGeoIdApi;
import com.example.hotelbookingapp.data.repository.BookingRepository;
import com.example.hotelbookingapp.data.repository.FavoriteHotelsRepository;
import com.example.hotelbookingapp.data.repository.HotelDetailsRepository;
import com.example.hotelbookingapp.data.repository.HotelsListRepository;
import com.example.hotelbookingapp.data.repository.RegionIdRepository;
import com.example.hotelbookingapp.helper.AuthManager;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class AppModule {

    @Provides
    public static HotelsListRepository provideHotelsListRepository(HotelsListApi api) {
        return new HotelsListRepository(api);
    }

    @Provides
    public static HotelDetailsRepository provideHotelDetailsRepository(HotelDetailsApi api) {
        return new HotelDetailsRepository(api);
    }

    @Provides
    public static BookingRepository provideBookingRepository(BookingApiService bookingApiService, AuthManager authManager) {
        return new BookingRepository(bookingApiService, authManager);
    }

    @Provides
    public static RegionIdRepository provideGeoIdRepository(LocationGeoIdApi api) {
        return new RegionIdRepository(api);
    }

    @Provides
    public static AuthManager provideAuthManager(@ApplicationContext Context context) {
        return new AuthManager(context);
    }

    @Provides
    public static FavoriteHotelsRepository provideFavoriteHotelsRepository(FavoriteHotelsApiService favoriteHotelsApiService, AuthManager authManager) {
        return new FavoriteHotelsRepository(favoriteHotelsApiService, authManager);
    }
}
