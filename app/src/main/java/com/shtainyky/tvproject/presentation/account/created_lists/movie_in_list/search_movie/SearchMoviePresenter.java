package com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.search_movie;

import android.util.Log;

import com.shtainyky.tvproject.data.exceptions.ConnectionException;
import com.shtainyky.tvproject.data.models.movie.MovieItem;
import com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.adapter.MovieItemDH;
import com.shtainyky.tvproject.utils.Constants;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;


/**
 * Created by Bell on 30.05.2017.
 */

public class SearchMoviePresenter implements SearchMovieContract.SearchMoviePresenter {
    private SearchMovieContract.SearchMovieView view;
    private CompositeDisposable compositeDisposable;
    private SearchMovieContract.SearchMovieModel model;
    private int current_page;
    private int total_pages = Integer.MAX_VALUE;
    private String movieTitle;

    public SearchMoviePresenter(SearchMovieContract.SearchMovieView view,
                                SearchMovieContract.SearchMovieModel model) {
        this.view = view;
        this.model = model;
        view.setPresenter(this);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void onSearchClick(String title) {
        if (!title.isEmpty()){
            this.movieTitle = title;
            view.showProgressMain();
            loadMovies();
        }
        else
            view.showMessage(Constants.MessageType.INPUT_MOVIE_TITLE);
    }


    @Override
    public void onRefresh() {
        loadMovies();
    }

    private void loadMovies() {
        current_page = 1;
        loadPage(current_page);
    }

    @Override
    public void getNextPage() {
        Log.e("myLog", "current_page" + current_page);
        view.showProgressPagination();
        if (current_page < total_pages)
            loadPage(current_page + 1);
    }

    private void loadPage(int pageNumber) {
        compositeDisposable.add(model.getMovies(movieTitle, pageNumber)
                .subscribe(response -> {
                    view.hideProgress();
                    current_page = pageNumber;
                    total_pages = response.total_pages;
                    if (current_page == 1)
                        if (!response.movies.isEmpty())
                            view.setList(prepareList(response.movies));
                        else
                            view.showPlaceholder(Constants.PlaceholderType.EMPTY);
                    else
                        view.addList(prepareList(response.movies));

                }, throwable -> {
                    view.hideProgress();
                    if (total_pages == Integer.MAX_VALUE)
                        if (throwable instanceof ConnectionException) {
                            view.showMessage(Constants.MessageType.CONNECTION_PROBLEMS);
                        } else {
                            view.showMessage(Constants.MessageType.UNKNOWN);
                        }
                    else if (throwable instanceof ConnectionException) {
                        view.showPlaceholder(Constants.PlaceholderType.NETWORK);
                    } else {
                        view.showPlaceholder(Constants.PlaceholderType.UNKNOWN);
                    }
                }));
    }

    private ArrayList<MovieItemDH> prepareList(ArrayList<MovieItem> items) {
        ArrayList<MovieItemDH> list = new ArrayList<>();
        for (MovieItem item : items) {
            list.add(new MovieItemDH(item));
        }
        return list;
    }
}
