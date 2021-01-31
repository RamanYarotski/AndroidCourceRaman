package com.homework.hw8mvvm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.homework.hw8mvvm.pojo.ListWeather;

import java.util.List;

import io.reactivex.annotations.NonNull;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    List<ListWeather> listWeathers;
    MainActivity mainActivity;
    private String imageUri = "https://openweathermap.org/img/wn/%S@2x.png";
    private String T;

    public WeatherAdapter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public List<ListWeather> getListWeathers() {
        return listWeathers;
    }

    public void setListWeathers(List<ListWeather> listWeathers, String T) {
        this.listWeathers = listWeathers;
        this.T = T;
        notifyDataSetChanged();
    }

    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.weather_item, viewGroup, false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder weatherViewHolder, int i) {
        if (i < listWeathers.size() - 1) {
            i++;
        }
        ListWeather listWeather = listWeathers.get(i);
        weatherViewHolder.temperatureTV.setText(listWeather.getMain().getTemp() + T);
        weatherViewHolder.descriptionTV.setText(String.valueOf(listWeather.getWeather().get(0).getDescription()));
        weatherViewHolder.timeTV.setText(listWeather.getDtTxt());
        Glide
                .with(mainActivity)
                .load(String.format(imageUri, listWeather.getWeather().get(0).getIcon()).toLowerCase())
                .into(weatherViewHolder.weatherImageView);
    }

    @Override
    public int getItemCount() {
        return listWeathers.size();
    }

    class WeatherViewHolder extends RecyclerView.ViewHolder {

        private TextView temperatureTV;
        private TextView descriptionTV;
        private TextView timeTV;
        private ImageView weatherImageView;

        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            temperatureTV = itemView.findViewById(R.id.textViewTemperature);
            descriptionTV = itemView.findViewById(R.id.textViewDescription);
            timeTV = itemView.findViewById(R.id.textViewTime);
            weatherImageView = itemView.findViewById(R.id.weatherImageView);
        }
    }
}
