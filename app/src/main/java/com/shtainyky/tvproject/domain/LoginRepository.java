package com.shtainyky.tvproject.domain;


import android.util.Log;

import com.shtainyky.tvproject.data.Rest;
import com.shtainyky.tvproject.data.models.loginization.LoginSession;
import com.shtainyky.tvproject.data.models.loginization.LoginToken;
import com.shtainyky.tvproject.data.services.LoginService;
import com.shtainyky.tvproject.presentation.base.NetworkRepository;
import com.shtainyky.tvproject.presentation.login.LoginContract;
import com.shtainyky.tvproject.utils.Constants;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import rx.Observable;

/**
 * Created by Bell on 24.05.2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class LoginRepository extends NetworkRepository implements LoginContract.LoginModel {
    @Bean
    protected Rest rest;

    private LoginService userService;

    @AfterInject
    protected void initServices() {
        userService = rest.getUserService();
    }


    @Override
    public Observable<LoginToken> getToken() {
        Log.e("myLog","LoginRepository getToken called " );
        return getNetworkObservable(userService.getToken());
    }

    @Override
    public Observable<LoginToken> getValidatedToken(String username, String password, String request_token) {
        Log.e("myLog","LoginRepository getToken called " + "username " + username
        +" password " + password + " request_token " + request_token);
        return getNetworkObservable(userService.validateToken(username, password, request_token ));
    }

    @Override
    public Observable<LoginSession> getSessionID(String validatedToken) {
        return getNetworkObservable(userService.getSessionId(validatedToken));
    }
}
