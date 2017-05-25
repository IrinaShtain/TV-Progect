package com.shtainyky.tvproject.data.services;

import com.shtainyky.tvproject.data.models.loginization.LoginSession;
import com.shtainyky.tvproject.data.models.loginization.LoginToken;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Bell on 24.05.2017.
 */

public interface LoginService {
    @GET("/3/authentication/token/new?")
    Observable<LoginToken> getToken(@Query("api_key") String api_key);

    @GET("/3/authentication/token/validate_with_login")
    Observable<LoginToken> validateToken(@Query("api_key") String api_key,
                                         @Query("username") String username,
                                         @Query("password") String password,
                                         @Query("request_token") String request_token);

    @GET("3/authentication/session/new")
    Observable<LoginSession> getSessionId(@Query("api_key") String api_key,
                                          @Query("request_token") String request_token);
}
