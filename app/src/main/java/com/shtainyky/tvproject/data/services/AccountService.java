package com.shtainyky.tvproject.data.services;

import com.shtainyky.tvproject.data.models.account.CreatedListsData;
import com.shtainyky.tvproject.data.models.account.User;
import com.shtainyky.tvproject.utils.Constants;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Bell on 25.05.2017.
 */

public interface AccountService {
    @GET(Constants.GET_USER_ACCOUNT)
    Observable<User> getDetails(@Query("api_key") String api_key,
                                @Query("session_id") String sessionId);

    @GET("/3/account/{account_id}/lists")
    Observable<CreatedListsData> getLists(@Path("account_id") int account_id,
                                          @Query("api_key") String api_key,
                                          @Query("session_id") String sessionId,
                                          @Query("page") int page);

}
