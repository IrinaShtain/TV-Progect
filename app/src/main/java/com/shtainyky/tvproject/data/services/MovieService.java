package com.shtainyky.tvproject.data.services;

import com.shtainyky.tvproject.data.models.movie.MovieItem;
import com.shtainyky.tvproject.data.models.movie.SearchMovieResponse;
import com.shtainyky.tvproject.data.models.request_body.ActionRequest;
import com.shtainyky.tvproject.data.models.movie.GenresResponse;
import com.shtainyky.tvproject.data.models.movie.MoviesResponse;
import com.shtainyky.tvproject.data.models.request_body.RateRequest;
import com.shtainyky.tvproject.data.models.response.ResponseMessage;
import com.shtainyky.tvproject.data.models.star.StarResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Created by Bell on 29.05.2017.
 */

public interface MovieService {
    @GET("/3/genre/movie/list")
    Observable<GenresResponse> getGenres();

    @GET("/3/list/{list_id}")
    Observable<MoviesResponse> getMovies(@Path("list_id") int list_id);

    @Headers("content-type: application/json;charset=utf-8")
    @POST("/3/list/{list_id}/remove_item")
    Observable<ResponseMessage> deleteMovie(@Path("list_id") int list_id,
                                            @Body ActionRequest action);

    @GET("/3/search/movie")
    Observable<SearchMovieResponse> searchMovie(@Query("query") String title,
                                                @Query("page") int page);

    @GET("/3/movie/{movie_id}")
    Observable<MovieItem> getMovieDetails(@Path("movie_id") int movie_id);

    @Headers("content-type: application/json;charset=utf-8")
    @POST("/3/list/{list_id}/add_item")
    Observable<ResponseMessage> addMovie(@Path("list_id") int list_id,
                                         @Body ActionRequest action);

    @GET("/3/search/person")
    Observable<StarResponse> searchStar(@Query("query") String title,
                                        @Query("page") int page);


    @Headers("content-type: application/json;charset=utf-8")
    @DELETE("/3/list/{list_id}")
    Observable<ResponseMessage> deleteList(@Path("list_id") int list_id);

    @Headers("content-type: application/json;charset=utf-8")
    @POST("/3/movie/{movie_id}/rating")
    Observable<ResponseMessage> rateMovie(@Path("movie_id") int movie_id,
                                          @Body RateRequest rateRequest);
}
