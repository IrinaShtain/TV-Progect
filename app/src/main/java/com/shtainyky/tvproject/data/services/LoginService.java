package com.shtainyky.tvproject.data.services;

import com.shtainyky.tvproject.data.models.loginization.LoginSession;
import com.shtainyky.tvproject.data.models.loginization.LoginToken;
import com.shtainyky.tvproject.utils.Constants;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Bell on 24.05.2017.
 */

public interface LoginService {
    @GET(Constants.GET_USER_TOKEN)
    Observable<LoginToken> getToken();

    @GET(Constants.GET_VALIDATED_USER_TOKEN)
    Observable<LoginToken> validateToken(@Query("username") String username,
                                         @Query("password") String password,
                                         @Query("request_token") String request_token);

    @GET(Constants.GET_USER_SESSION_ID)
    Observable<LoginSession> getSessionId(@Query("request_token") String request_token);
}
