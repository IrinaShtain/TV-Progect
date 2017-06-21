package com.shtainyky.tvproject.domain;

import com.shtainyky.tvproject.data.Rest;
import com.shtainyky.tvproject.data.models.movie.SearchMovieResponse;
import com.shtainyky.tvproject.data.models.request_body.ActionRequest;
import com.shtainyky.tvproject.data.models.movie.GenresResponse;
import com.shtainyky.tvproject.data.models.movie.MoviesResponse;
import com.shtainyky.tvproject.data.models.response.ResponseMessage;
import com.shtainyky.tvproject.data.services.MovieService;
import com.shtainyky.tvproject.presentation.account.movie.MoviesContract;
import com.shtainyky.tvproject.presentation.account.movie.search_movie.SearchMovieContract;
import com.shtainyky.tvproject.presentation.base.NetworkRepository;
import com.shtainyky.tvproject.utils.Constants;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import rx.Observable;

/**
 * Created by Bell on 29.05.2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class MovieRepository extends NetworkRepository implements MoviesContract.MovieModel,
        SearchMovieContract.SearchMovieModel{

    @Bean
    protected Rest rest;

    private MovieService mMovieService;

    @AfterInject
    protected void initServices() {
        mMovieService = rest.getMovieService();
    }

    @Override
    public Observable<GenresResponse> getGenres() {
        return  getNetworkObservable(mMovieService.getGenres(Constants.KEY_API));
    }

    @Override
    public Observable<MoviesResponse> getMovies(int listID) {
        return getNetworkObservable(mMovieService.getMovies(listID, Constants.KEY_API));
    }

    @Override
    public Observable<ResponseMessage> deleteMovie(int listID, int movieID, String sessionID) {
        return getNetworkObservable(mMovieService.deleteMovie(listID, Constants.KEY_API, sessionID, new ActionRequest(movieID)));
    }

    @Override
    public Observable<SearchMovieResponse> getMovies(String title, int page) {
        return getNetworkObservable(mMovieService.searchMovie(Constants.KEY_API, title, page));
    }

    @Override
    public Observable<ResponseMessage> addMovie(int listID, int movieID, String sessionID) {
        return getNetworkObservable(mMovieService.addMovie(listID, Constants.KEY_API, sessionID, new ActionRequest(movieID)));
    }

    @Override
    public Observable<ResponseMessage> deleteList(int listID, String sessionID) {
        return getNetworkObservable(mMovieService.deleteList(listID, Constants.KEY_API, sessionID));
    }

}
