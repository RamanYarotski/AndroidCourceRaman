package com.homework.hw8mvvm.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MainWeather {

    @SerializedName("list")
    @Expose
    private List<ListWeather> listWeathers = null;

    public List<ListWeather> getListWeathers() {
        return listWeathers;
    }
}