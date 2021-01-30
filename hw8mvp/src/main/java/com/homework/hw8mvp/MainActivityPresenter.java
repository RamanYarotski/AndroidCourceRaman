package com.homework.hw8mvp;

import com.homework.hw8mvp.api.ApiFactory;
import com.homework.hw8mvp.api.ApiService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivityPresenter {
    private CompositeDisposable compositeDisposable;
    ListWeatherView view;

    public MainActivityPresenter(ListWeatherView view) {
        this.view = view;
    }

    public void showInfoCelsius() {
        ApiFactory apiFactory = ApiFactory.getInstance();
        ApiService apiService = apiFactory.getApiService();
        compositeDisposable = new CompositeDisposable();
        Disposable disposable = apiService
                .getMainWeatherCels()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mainWeather -> view.showData(mainWeather.getListWeathers(),
                        mainWeather), throwable -> view.showError());
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
                .subscribe(mainWeather -> view.showData(mainWeather.getListWeathers(),
                        mainWeather), throwable -> view.showError());
        compositeDisposable.add(disposable);
    }

    public void disposeDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }
}
