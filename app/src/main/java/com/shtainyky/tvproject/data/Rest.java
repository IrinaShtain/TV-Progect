package com.shtainyky.tvproject.data;

import android.util.Log;

import com.github.aurae.retrofit2.LoganSquareConverterFactory;
import com.shtainyky.tvproject.TVProjectApplication;
import com.shtainyky.tvproject.data.exceptions.ConnectionException;
import com.shtainyky.tvproject.data.exceptions.TimeoutException;
import com.shtainyky.tvproject.data.services.AccountService;
import com.shtainyky.tvproject.data.services.LoginService;
import com.shtainyky.tvproject.utils.Constants;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EBean;

import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by Bell on 23.05.2017.
 */

@EBean(scope = EBean.Scope.Singleton)
public class Rest {

    @App
    protected TVProjectApplication application;


    private Retrofit retrofit;
    private LoginService userService;
    private AccountService accountService;


    public Rest() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                .connectTimeout(Constants.TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Constants.TIMEOUT_READ, TimeUnit.SECONDS)
                .writeTimeout(Constants.TIMEOUT_WRITE, TimeUnit.SECONDS)
                .addInterceptor(chain -> {
                    try {
                        if (!application.hasInternetConnection()) {
                            throw new ConnectionException();
                        } else {
                            Request request = chain.request().newBuilder()
                                    .build();

                            return chain.proceed(request);
                        }
                    } catch (SocketTimeoutException e) {
                        throw new TimeoutException();
                    }
                });

        Log.e("myLog","Rest called ");
        retrofit = new Retrofit.Builder()
                .addConverterFactory(LoganSquareConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(clientBuilder.build())
                .baseUrl(Constants.BASE_URL)
                .build();

    }

    public LoginService getUserService() {
        return userService == null ? userService = retrofit.create(LoginService.class) : userService;
    }

    public AccountService getAccountService() {
        return accountService == null ? accountService = retrofit.create(AccountService.class) : accountService;
    }

}
