package com.homework.hw8mvp;

import com.homework.hw8mvp.pojo.ListWeather;
import com.homework.hw8mvp.pojo.MainWeather;

import java.util.List;

public interface ListWeatherView {
    void showData(List<ListWeather> listWeathers, MainWeather mainWeather);
    void showError();
}
