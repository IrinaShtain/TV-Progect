package com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.search_movie;

import com.shtainyky.tvproject.data.models.movie.GenresResponse;
import com.shtainyky.tvproject.data.models.movie.SearchMovieResponse;
import com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.search_movie.adapter.SearchMovieDH;
import com.shtainyky.tvproject.presentation.base.BasePresenter;
import com.shtainyky.tvproject.presentation.base.BaseView;

import java.util.ArrayList;

import io.reactivex.Observable;


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
        Observable<GenresResponse> getGenres();
    }
}
