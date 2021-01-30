package com.homework.hw8mvvm.api;

import com.homework.hw8mvvm.pojo.MainWeather;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {
    @GET("forecast?q=Minsk&units=metric&cnt=25&appid=7656c003e68cfc7d0e9cb9a7b41bff61")
    Observable<MainWeather> getMainWeatherCels();

    @GET("forecast?q=Minsk&units=imperial&cnt=25&appid=7656c003e68cfc7d0e9cb9a7b41bff61")
    Observable<MainWeather> getMainWeatherFarenhate();
}
