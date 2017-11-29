package com.shtainyky.tvproject.domain;

import com.shtainyky.tvproject.data.Rest;
import com.shtainyky.tvproject.data.models.movie.GenresResponse;
import com.shtainyky.tvproject.data.models.movie.MovieItem;
import com.shtainyky.tvproject.data.models.movie.MoviesResponse;
import com.shtainyky.tvproject.data.models.movie.SearchMovieResponse;
import com.shtainyky.tvproject.data.models.request_body.ActionRequest;
import com.shtainyky.tvproject.data.models.request_body.RateRequest;
import com.shtainyky.tvproject.data.models.response.ResponseMessage;
import com.shtainyky.tvproject.data.services.MovieService;
import com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.MoviesInListContract;
import com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.movie_details.MovieDetailsContract;
import com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.movie_details.rating_dialog.RatingDialogContract;
import com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.search_movie.SearchMovieContract;
import com.shtainyky.tvproject.presentation.base.NetworkRepository;
import com.shtainyky.tvproject.utils.SignedUserManager;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import io.reactivex.Observable;


/**
 * Created by Bell on 29.05.2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class MovieRepository extends NetworkRepository implements
        SearchMovieContract.SearchMovieModel, MovieDetailsContract.MovieDetailsModel,
        MoviesInListContract.MoviesInListModel, RatingDialogContract.RatingDialogModel {

    @Bean
    protected Rest rest;
    @Bean
    protected SignedUserManager userManager;

    private MovieService mMovieService;

    @AfterInject
    protected void initServices() {
        mMovieService = rest.getMovieService();
    }

    @Override
    public Observable<GenresResponse> getGenres() {
        return getNetworkObservable(mMovieService.getGenres());
    }

    @Override
    public Observable<MoviesResponse> getMovies(int listID) {
        return getNetworkObservable(mMovieService.getMovies(listID));
    }

    @Override
    public Observable<MovieItem> getMovieDetails(int movieID) {
        return getNetworkObservable(mMovieService.getMovieDetails(movieID));
    }

    public Observable<ResponseMessage> deleteMovie(int listID, int movieID) {
        return getNetworkObservable(mMovieService.deleteMovie(listID, new ActionRequest(movieID)));
    }

    @Override
    public Observable<SearchMovieResponse> getMovies(String title, int page) {
        return getNetworkObservable(mMovieService.searchMovie(title, page));
    }

    @Override
    public Observable<ResponseMessage> addMovie(int listID, int movieID) {
        return getNetworkObservable(mMovieService.addMovie(listID, new ActionRequest(movieID)));
    }

    @Override
    public Observable<ResponseMessage> deleteList(int listID, String sessionID) {
        return getNetworkObservable(mMovieService.deleteList(listID));
    }

    @Override
    public SignedUserManager getSignedUserManager() {
        return userManager;
    }

    @Override
    public Observable<ResponseMessage> rateMovie(float rating, int movieID) {
        return getNetworkObservable(mMovieService.rateMovie(movieID, new RateRequest(rating)));
    }
}
