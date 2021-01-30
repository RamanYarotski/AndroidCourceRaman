package com.homework.hw8mvp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.homework.hw8mvp.pojo.ListWeather;
import com.homework.hw8mvp.pojo.MainWeather;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ListWeatherView {

    private MainActivityPresenter presenter;
    private WeatherAdapter weatherAdapter;
    private TextView temperatureTV;
    private TextView descriptionTV;
    private TextView timeTV;
    private ImageView imageWeather;
    private boolean temperatureCheck;
    private String T;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        temperatureTV = findViewById(R.id.temperatureTV);
        descriptionTV = findViewById(R.id.descriptionTV);
        timeTV = findViewById(R.id.timeTV);
        imageWeather = findViewById(R.id.weatherImage);

        RecyclerView recyclerViewEmployees = findViewById(R.id.recyclerView);
        weatherAdapter = new WeatherAdapter(this);
        weatherAdapter.setListWeathers(new ArrayList<>(), T);
        recyclerViewEmployees.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewEmployees.setAdapter(weatherAdapter);
        presenter = new MainActivityPresenter(this);
        temperatureCheck = getIntent().getBooleanExtra("TEMPCHECK", false);
        if (temperatureCheck) {
            presenter.showInfoFahrenheat();
        } else {
            presenter.showInfoCelsius();
        }

        findViewById(R.id.floatButton).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
    }

    public void glide(MainWeather mainWeather) {
        String imageUri = "https://openweathermap.org/img/wn/%S@2x.png";
        Glide
                .with(MainActivity.this)
                .load(String.format(imageUri, mainWeather.getListWeathers()
                        .get(0).getWeather().get(0).getIcon()).toLowerCase())
                .into(imageWeather);
    }

    @Override
    public void showData(List<ListWeather> listWeathers, MainWeather mainWeather) {
        double temperature;
        if (!temperatureCheck) {
            T = " C";
            temperature = mainWeather.getListWeathers().get(0).getMain().getTemp();
            temperatureTV.setText(temperature + " C");
        } else {
            T = " F";
            temperature = mainWeather.getListWeathers().get(0).getMain().getTemp();
            temperatureTV.setText(temperature + " F");
        }
        descriptionTV.setText(mainWeather.getListWeathers().get(0).getWeather().get(0).getDescription());
        timeTV.setText(mainWeather.getListWeathers().get(0).getDtTxt());
        weatherAdapter.setListWeathers(mainWeather.getListWeathers(), T);
        glide(mainWeather);
    }

    @Override
    protected void onDestroy() {
        presenter.disposeDisposable();
        super.onDestroy();
    }

    @Override
    public void showError() {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
    }
}