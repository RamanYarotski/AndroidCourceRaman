package com.homework.hw8mvp.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListWeather {

    @SerializedName("dt")
    @Expose
    private int dt;

    @SerializedName("dt_txt")
    @Expose
    private String dtTxt;

    @SerializedName("weather")
    @Expose
    private List<Weather> weather = null;

    @SerializedName("main")
    @Expose
    private Main main;

    public String getDtTxt() {
        return dtTxt;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public Main getMain() {
        return main;
    }

}
