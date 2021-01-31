package com.homework.hw8mvvm.converter;

import androidx.room.TypeConverter;

import com.homework.hw8mvvm.pojo.Main;
import com.homework.hw8mvvm.pojo.Weather;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class Converter {
    @TypeConverter
    public String fromMainToString(Main main) {
        return new Gson().toJson(main);
    }

    @TypeConverter
    public Main fromStringToMain(String stringMain) {
        return new Gson().fromJson(stringMain, Main.class);
    }

    @TypeConverter
    public  String listWeatherToString(List<Weather> weathers) {
        return new Gson().toJson(weathers);
    }

    @TypeConverter
    public  List<Weather> stringToListWeather(String weathersAsString) {
        Type listType = new TypeToken<List<Weather>>() {}.getType();
        return new Gson().fromJson(weathersAsString, listType);
    }

}
