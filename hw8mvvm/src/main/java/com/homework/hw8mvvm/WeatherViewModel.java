package com.homework.hw8mvvm;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.homework.hw8mvvm.api.ApiFactory;
import com.homework.hw8mvvm.api.ApiService;
import com.homework.hw8mvvm.data.AppDataBase;
import com.homework.hw8mvvm.pojo.ListWeather;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WeatherViewModel extends AndroidViewModel {
    private static AppDataBase db;
    private LiveData<List<ListWeather>> listWeathers;
    private CompositeDisposable compositeDisposable;
    private MutableLiveData<Throwable> error;

    public WeatherViewModel(@NonNull Application application) {
        super(application);
        db = AppDataBase.getInstance(application);
        listWeathers = db.listWeatherDao().getAllWeathers();
        error = new MutableLiveData();
    }

    public LiveData<List<ListWeather>> getListWeathers() {
        return listWeathers;
    }

    public LiveData<Throwable> getError() {
        return error;
    }

    public void clearErrors() {
        error.setValue(null);
    }

    public void insertWeathers(List<ListWeather> listWeathers) {
        new insertWeatherTask().execute(listWeathers);
    }

    private static class insertWeatherTask extends AsyncTask<List<ListWeather>, Void, Void> {

        @Override
        protected Void doInBackground(List<ListWeather>... lists) {
            if (lists != null && lists.length > 0) {
                db.listWeatherDao().InsertWeathers(lists[0]);
            }
            return null;
        }
    }

    public void deleteAllWeathers() {
        new deleteAllWeathersTask().execute();
    }

    private static class deleteAllWeathersTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            db.listWeatherDao().deleteAllWeathers();
            return null;
        }
    }

    public void showInfoCelsius() {
        ApiFactory apiFactory = ApiFactory.getInstance();
        ApiService apiService = apiFactory.getApiService();
        compositeDisposable = new CompositeDisposable();
        Disposable disposable = apiService
                .getMainWeatherCels()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mainWeather -> {
                    deleteAllWeathers();
                    insertWeathers(mainWeather.getListWeathers());
                }, throwable -> error.setValue(throwable));
        compositeDisposable.add(disposable);
    }

    public void showInfoFahrenheat() {
        ApiFactory apiFactory = ApiFactory.getInstance();
        ApiService apiService = apiFactory.getApiService();
        compositeDisposable = new CompositeDisposable();
        Disposable disposable = apiService
                .getMainWeatherFarenhate()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mainWeather -> {
                    deleteAllWeathers();
                    insertWeathers(mainWeather.getListWeathers());
                }, throwable -> error.setValue(throwable));
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }
}
