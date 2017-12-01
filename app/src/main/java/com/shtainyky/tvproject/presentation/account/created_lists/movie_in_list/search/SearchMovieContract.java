package com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.search;

import com.shtainyky.tvproject.data.models.movie.GenresResponse;
import com.shtainyky.tvproject.data.models.movie.SearchMovieResponse;
import com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.adapter.MovieItemDH;
import com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.search.genres_adapter.GenreDH;
import com.shtainyky.tvproject.presentation.base.BaseView;
import com.shtainyky.tvproject.presentation.base.content.ContentView;
import com.shtainyky.tvproject.presentation.base.refreshable_content.RefreshablePresenter;

import java.util.ArrayList;

import io.reactivex.Observable;


/**
 * Created by Bell on 30.05.2017.
 */

public class SearchMovieContract {
    public interface SearchMovieView extends BaseView<SearchMovieContract.SearchMoviePresenter>, ContentView {
        void setList(ArrayList<MovieItemDH> movieDHs);
        void addList(ArrayList<MovieItemDH> movieDHs);
        void setGenres(ArrayList<GenreDH> genreItems);
        void setupSearchByTitle();
        void setupGenresList();
    }

    interface SearchMoviePresenter extends RefreshablePresenter {
        void onSearchClick(String movieTitle);
        void getNextPage();
        void searchByGenre(int id);
    }

    public interface SearchMovieModel {
        Observable<SearchMovieResponse> getMoviesByTitle(String title, int page);
        Observable<SearchMovieResponse> searchMovieByGenre(int genreId, int page);
        Observable<SearchMovieResponse> getLatestMovies(int page);
        Observable<SearchMovieResponse> getPopularMovies(int page);
        Observable<GenresResponse> getGenres();
    }
}
