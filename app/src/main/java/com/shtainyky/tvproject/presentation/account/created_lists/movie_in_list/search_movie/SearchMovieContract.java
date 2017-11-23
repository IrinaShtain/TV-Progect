package com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.search_movie;

import com.shtainyky.tvproject.data.models.movie.GenresResponse;
import com.shtainyky.tvproject.data.models.movie.SearchMovieResponse;
import com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.adapter.MovieItemDH;
import com.shtainyky.tvproject.presentation.base.BaseView;
import com.shtainyky.tvproject.presentation.base.content.ContentView;
import com.shtainyky.tvproject.presentation.base.refreshable_content.RefreshablePresenter;

import java.util.ArrayList;

import io.reactivex.Observable;


/**
 * Created by Bell on 30.05.2017.
 */

public class SearchMovieContract {
    interface SearchMovieView extends BaseView<SearchMovieContract.SearchMoviePresenter>, ContentView {
        void setList(ArrayList<MovieItemDH> movieDHs);
        void addList(ArrayList<MovieItemDH> movieDHs);
        void showMessage(String message);
    }

    interface SearchMoviePresenter extends RefreshablePresenter {
        void onSearchClick(String movieTitle);
        void getNextPage();
    }

    public interface SearchMovieModel {
        Observable<SearchMovieResponse> getMovies(String title, int page);
        Observable<GenresResponse> getGenres();
    }
}
