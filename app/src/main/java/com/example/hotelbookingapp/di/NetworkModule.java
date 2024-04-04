package com.example.hotelbookingapp.di;

import com.example.hotelbookingapp.data.api.AuthService;
import com.example.hotelbookingapp.data.api.BookingApiService;
import com.example.hotelbookingapp.data.api.FavoriteHotelsApiService;
import com.example.hotelbookingapp.data.api.HotelDetailsApi;
import com.example.hotelbookingapp.data.api.HotelsListApi;
import com.example.hotelbookingapp.data.api.LocationGeoIdApi;
import com.example.hotelbookingapp.helper.Constants;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public abstract class NetworkModule {

    @Provides
    public static LocationGeoIdApi provideGeoIdApi() {
        return new Retrofit.Builder()
                .baseUrl(Constants.HOTELS_PROVIDER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(LocationGeoIdApi.class);
    }

    @Provides
    public static HotelsListApi provideHotelsListApi() {
        return new Retrofit.Builder()
                .baseUrl(Constants.HOTELS_PROVIDER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(HotelsListApi.class);
    }

    @Provides
    public static HotelDetailsApi provideHotelDetailsApi() {
        return new Retrofit.Builder()
                .baseUrl(Constants.HOTELS_PROVIDER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(HotelDetailsApi.class);
    }

    @Provides
    public static AuthService provideAuthService() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .build()
                .create(AuthService.class);
    }

    @Provides
    public static BookingApiService provideBookingApiService(){
        return new  Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .build()
                .create(BookingApiService.class);
    }

    @Provides
    public static FavoriteHotelsApiService provideFavoriteHotelsApiService(){
        return new  Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .build()
                .create(FavoriteHotelsApiService.class);
    }

}
