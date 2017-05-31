package com.shtainyky.tvproject.data.services;

import com.shtainyky.tvproject.data.models.movie.SearchMovieResponse;
import com.shtainyky.tvproject.data.models.request_body.ActionRequest;
import com.shtainyky.tvproject.data.models.movie.GenresResponse;
import com.shtainyky.tvproject.data.models.movie.MoviesResponse;
import com.shtainyky.tvproject.data.models.response.ResponseMessage;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Bell on 29.05.2017.
 */

public interface MovieService {
    @GET("/3/genre/movie/list")
    Observable<GenresResponse> getGenres(@Query("api_key") String api_key);

    @GET("/3/list/{list_id}")
    Observable<MoviesResponse> getMovies(@Path("list_id") int list_id, @Query("api_key") String api_key );

    @Headers("content-type: application/json;charset=utf-8")
    @POST("/3/list/{list_id}/remove_item")
    Observable<ResponseMessage> deleteMovie(@Path("list_id") int list_id,
                                            @Query("api_key") String api_key,
                                            @Query("session_id") String session_id,
                                            @Body ActionRequest action);

    @GET("/3/search/movie")
    Observable<SearchMovieResponse> searchMovie(@Query("api_key") String api_key,
                                                @Query("query") String title,
                                                @Query("page") int page);

    @Headers("content-type: application/json;charset=utf-8")
    @POST("/3/list/{list_id}/add_item")
    Observable<ResponseMessage> addMovie(@Path("list_id") int list_id,
                                            @Query("api_key") String api_key,
                                            @Query("session_id") String session_id,
                                            @Body ActionRequest action);
}
