package com.homework.hw8mvvm;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.homework.hw8mvvm.pojo.ListWeather;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewEmployees;
    private WeatherAdapter weatherAdapter;
    private TextView temperatureTV;
    private TextView descriptionTV;
    private TextView timeTV;
    private ImageView imageWeather;
    private double temperature;
    private String T = " C";
    private WeatherViewModel weatherVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        temperatureTV = findViewById(R.id.temperatureTV);
        descriptionTV = findViewById(R.id.descriptionTV);
        timeTV = findViewById(R.id.timeTV);
        imageWeather = findViewById(R.id.weatherImage);

        recyclerViewEmployees = findViewById(R.id.recyclerView);
        weatherAdapter = new WeatherAdapter(this);
        weatherAdapter.setListWeathers(new ArrayList<>(), T);
        recyclerViewEmployees.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewEmployees.setAdapter(weatherAdapter);
        boolean temperatureCheck = getIntent().getBooleanExtra("TEMPCHECK", false);
        weatherVM = ViewModelProviders.of(this).get(WeatherViewModel.class);

        weatherVM.getListWeathers().observe(this, listWeathers -> {
            weatherAdapter.setListWeathers(listWeathers, T);
            if (listWeathers.size() != 0) {
                temperatureTV.setText(listWeathers.get(0).getMain().getTemp() + T);
                timeTV.setText(listWeathers.get(0).getDtTxt());
                descriptionTV.setText(listWeathers.get(0).getWeather().get(0).getDescription());
                glide(listWeathers);
            }
        });
        weatherVM.getError().observe(this, throwable -> {
            if (throwable != null) {
                Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_LONG).show();
                weatherVM.clearErrors();
            }
        });

        if (temperatureCheck) {
            T = " F";
            weatherVM.showInfoFahrenheat();
        } else {
            T = " C";
            weatherVM.showInfoCelsius();
        }

        findViewById(R.id.floatButton).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
    }

    public void glide(List<ListWeather> listWeathers) {
        String imageUri = "https://openweathermap.org/img/wn/%S@2x.png";
        Glide
                .with(MainActivity.this)
                .load(String.format(imageUri, listWeathers.get(0).getWeather().get(0).getIcon()).toLowerCase())
                .into(imageWeather);
    }
}