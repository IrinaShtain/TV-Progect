package com.shtainyky.tvproject.presentation.account.movie.search_movie;

import com.shtainyky.tvproject.data.models.movie.SearchMovieResponse;
import com.shtainyky.tvproject.data.models.response.ResponseMessage;
import com.shtainyky.tvproject.presentation.base.BasePresenter;
import com.shtainyky.tvproject.presentation.base.BaseView;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by Bell on 30.05.2017.
 */

public class SearchMovieContract {
    interface SearchMovieView extends BaseView<SearchMovieContract.SearchMoviePresenter> {
        void getInputText();
        void setList(ArrayList<SearchMovieDH> movieDHs);
        void addList(ArrayList<SearchMovieDH> movieDHs);
        void showMessage(String message);
    }

    interface SearchMoviePresenter extends BasePresenter {
        void onSearchClick();
        void makeSearch(String movieTitle);
        void getNextPage();
        void addMovie(int movieID, int listID);
    }

    public interface SearchMovieModel {
        Observable<SearchMovieResponse> getMovies(String title, int page);
        Observable<ResponseMessage> addMovie(int listID, int movieID, String sessionID);
    }
}
