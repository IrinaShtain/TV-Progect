package com.shtainyky.tvproject.data.services;

import com.shtainyky.tvproject.data.models.account.CreatedListsData;
import com.shtainyky.tvproject.data.models.account.User;
import com.shtainyky.tvproject.data.models.request_body.ActionRequest;
import com.shtainyky.tvproject.data.models.request_body.NewListRequest;
import com.shtainyky.tvproject.data.models.response.ResponseMessage;
import com.shtainyky.tvproject.utils.Constants;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
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


    @Headers("content-type: application/json;charset=utf-8")
    @POST("/3/list")
    Observable<ResponseMessage> createList(@Query("api_key") String api_key,
                                           @Query("session_id") String sessionId,
                                           @Body NewListRequest request);

}
