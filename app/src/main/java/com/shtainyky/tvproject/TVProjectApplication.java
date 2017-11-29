package com.shtainyky.tvproject;

import android.app.Application;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatDelegate;

import com.facebook.stetho.Stetho;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.shtainyky.tvproject.data.Rest;
import com.shtainyky.tvproject.utils.AnalyticManager;
import com.shtainyky.tvproject.utils.ScreensCallback;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EApplication;
import org.androidannotations.annotations.SystemService;

import okhttp3.OkHttpClient;

/**
 * Created by Bell on 23.05.2017.
 */


@EApplication
public class TVProjectApplication extends Application {

    @Bean
    protected Rest rest;
    @SystemService
    protected ConnectivityManager connectivityManager;
    private ScreensCallback lifecycleCallbacks;

    @Override
    public void onCreate() {
        super.onCreate();
        initPicasso();
        Stetho.initializeWithDefaults(this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        AnalyticManager.init(this);
        lifecycleCallbacks = new ScreensCallback(this);

        registerActivityLifecycleCallbacks(lifecycleCallbacks);
    }

    private void initPicasso() {
        Picasso picasso = new Picasso.Builder(this)
                .downloader(new OkHttp3Downloader(new OkHttpClient()))
                .build();
        Picasso.setSingletonInstance(picasso);
    }

    public boolean hasInternetConnection() {
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

}
