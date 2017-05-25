package com.shtainyky.tvproject.data.services;

import com.shtainyky.tvproject.data.models.account.User;
import com.shtainyky.tvproject.utils.Constants;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Bell on 25.05.2017.
 */

public interface AccountService {
    @GET(Constants.GET_USER_ACCOUNT)
    Observable<User> getDetails(@Query("api_key") String api_key,
                                @Query("session_id") String sessionId);

}
