package com.homework.hw8mvvm.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Main {

    @SerializedName("temp")
    @Expose
    private double temp;

    @SerializedName("feels_like")
    @Expose
    private double feelsLike;

    public double getTemp() {
        return temp;
    }

}
