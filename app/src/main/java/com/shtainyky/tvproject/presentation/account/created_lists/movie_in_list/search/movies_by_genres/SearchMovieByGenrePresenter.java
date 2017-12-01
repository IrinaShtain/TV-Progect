package com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.search.movies_by_genres;

import com.shtainyky.tvproject.data.models.movie.SearchMovieResponse;
import com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.search.SearchMovieContract;
import com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.search.SearchMoviePresenter;

import io.reactivex.Observable;

/**
 * Created by Irina Shtain on 30.11.2017.
 */

public class SearchMovieByGenrePresenter extends SearchMoviePresenter {


    public SearchMovieByGenrePresenter(SearchMovieContract.SearchMovieView view, SearchMovieContract.SearchMovieModel model, int searchType) {
        super(view, model, searchType);
    }

    @Override
    protected Observable<SearchMovieResponse> getMovies(int page) {
        return model.searchMovieByGenre(genreId, page);
    }
}
