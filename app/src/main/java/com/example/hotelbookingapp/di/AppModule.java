package com.example.hotelbookingapp.di;

import android.content.Context;

import com.example.hotelbookingapp.data.api.FavoriteHotelsApiService;
import com.example.hotelbookingapp.data.repository.FavoriteHotelsRepository;
import com.example.hotelbookingapp.helper.AuthManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class AppModule {

//    @Provides
//    @Singleton
//    public static HotelsListRepository provideHotelsListRepository(HotelsListApi api) {
//        return new HotelsListRepository(api);
//    }
//
//    @Singleton
//    @Provides
//    public static HotelDetailsRepository provideHotelDetailsRepository(HotelDetailsApi api) {
//        return new HotelDetailsRepository(api);
//    }
//
//    @Singleton
//    @Provides
//    public static BookingRepository provideBookingRepository(BookingApiService bookingApiService, AuthManager authManager) {
//        return new BookingRepository(bookingApiService, authManager);
//    }
//
//    @Provides
//    @Singleton
//    public static RegionIdRepository provideGeoIdRepository(LocationGeoIdApi api) {
//        return new RegionIdRepository(api);
//    }

    @Provides
    @Singleton
    public static AuthManager provideAuthManager(@ApplicationContext Context context) {
        return new AuthManager(context);
    }

    @Provides
    @Singleton
    public static FavoriteHotelsRepository provideFavoriteHotelsRepository(FavoriteHotelsApiService favoriteHotelsApiService, AuthManager authManager) {
        return new FavoriteHotelsRepository(favoriteHotelsApiService, authManager);
    }
}
