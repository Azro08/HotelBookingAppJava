package com.example.hotelbookingapp.di;

import com.example.hotelbookingapp.data.api.AuthService;
import com.example.hotelbookingapp.data.api.HotelDetailsApi;
import com.example.hotelbookingapp.data.api.HotelsListApi;
import com.example.hotelbookingapp.data.api.LocationGeoIdApi;
import com.example.hotelbookingapp.helper.Constants;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

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
    @Singleton
    public static LocationGeoIdApi provideGeoIdApi() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(LocationGeoIdApi.class);
    }

    @Provides
    @Singleton
    public static HotelsListApi provideHotelsListApi() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(HotelsListApi.class);
    }

    @Provides
    @Singleton
    public static HotelDetailsApi provideHotelDetailsApi() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(HotelDetailsApi.class);
    }

    @Provides
    @Singleton
    public static AuthService provideAuthService() {
        return new Retrofit.Builder()
                .baseUrl("http://192.168.100.38:8082")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .build()
                .create(AuthService.class);
    }

}
