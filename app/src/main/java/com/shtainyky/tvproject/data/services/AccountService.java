package com.shtainyky.tvproject.data.services;

import com.shtainyky.tvproject.data.models.account.CreatedListsData;
import com.shtainyky.tvproject.data.models.account.User;
import com.shtainyky.tvproject.data.models.request_body.NewListRequest;
import com.shtainyky.tvproject.data.models.response.ResponseMessage;
import com.shtainyky.tvproject.utils.Constants;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Created by Bell on 25.05.2017.
 */

public interface AccountService {
    @GET(Constants.GET_USER_ACCOUNT)
    Observable<User> getDetails(@Query("session_id") String sessionId);

    @GET("/3/account/{account_id}/lists")
    Observable<CreatedListsData> getLists(@Path("account_id") int account_id,
                                          @Query("page") int page);


    @Headers("content-type: application/json;charset=utf-8")
    @POST("/3/list")
    Observable<ResponseMessage> createList(@Body NewListRequest request);

}
