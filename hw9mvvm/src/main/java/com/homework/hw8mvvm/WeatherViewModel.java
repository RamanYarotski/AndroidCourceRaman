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

    @SafeVarargs
    private final void insertWeatherThread(List<ListWeather>... lists) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (lists != null && lists.length > 0) {
                        db.listWeatherDao().InsertWeathers(lists[0]);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private void deleteAllWeathersThread() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    db.listWeatherDao().deleteAllWeathers();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
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
                    deleteAllWeathersThread();
                    insertWeatherThread(mainWeather.getListWeathers());
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
                    deleteAllWeathersThread();
                    insertWeatherThread(mainWeather.getListWeathers());
                }, throwable -> error.setValue(throwable));
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }
}
