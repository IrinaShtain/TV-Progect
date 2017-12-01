package com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.search;

import android.util.Log;

import com.shtainyky.tvproject.data.exceptions.ConnectionException;
import com.shtainyky.tvproject.data.models.movie.GenreItem;
import com.shtainyky.tvproject.data.models.movie.MovieItem;
import com.shtainyky.tvproject.data.models.movie.SearchMovieResponse;
import com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.adapter.MovieItemDH;
import com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.search.genres_adapter.GenreDH;
import com.shtainyky.tvproject.utils.AnalyticManager;
import com.shtainyky.tvproject.utils.Constants;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;


/**
 * Created by Bell on 30.05.2017.
 */

public abstract class SearchMoviePresenter implements SearchMovieContract.SearchMoviePresenter {
    private SearchMovieContract.SearchMovieView view;
    private CompositeDisposable compositeDisposable;
    protected SearchMovieContract.SearchMovieModel model;
    private int current_page;
    private int total_pages = Integer.MAX_VALUE;
    private boolean hasGenres;
    protected String movieTitle;
    protected int genreId;
    protected int searchType;

    public SearchMoviePresenter(SearchMovieContract.SearchMovieView view,
                                SearchMovieContract.SearchMovieModel model,
                                int searchType) {
        this.view = view;
        this.model = model;
        this.searchType = searchType;
        view.setPresenter(this);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {
        switch (searchType) {
            case Constants.SEARCH_TYPE_LATEST_MOVIES:
            case Constants.SEARCH_TYPE_POPULAR_MOVIES:
                loadMovies();
                break;
            case Constants.SEARCH_TYPE_MOVIES_BY_GENRE:
                view.setupGenresList();
                loadGenres();
                break;
            case Constants.SEARCH_TYPE_MOVIES_BY_TITLE:
                view.setupSearchByTitle();
                break;
        }
    }


    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void onSearchClick(String title) {
        AnalyticManager.trackClick(Constants.AnalyticClick.SEARCH_A_MOVIE);
        if (!title.isEmpty()) {
            this.movieTitle = title;
            loadMovies();
        } else
            view.showMessage(Constants.MessageType.INPUT_MOVIE_TITLE);
    }


    private void loadGenres() {
        view.showProgressMain();
        compositeDisposable.add(model.getGenres()
                .subscribe(response -> {
                    view.hideProgress();
                    view.setGenres(prepareGenres(response.genres));
                }, throwable -> {
                    view.hideProgress();
                    throwable.printStackTrace();
                    Log.e("myLogs", throwable.getLocalizedMessage());
                    if (hasGenres)
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

    private ArrayList<GenreDH> prepareGenres(ArrayList<GenreItem> genreItems) {
        ArrayList<GenreDH> genreDHS = new ArrayList<>();
        for (GenreItem genre : genreItems) {
            genreDHS.add(new GenreDH(genre));
        }
        hasGenres = true;
        return genreDHS;
    }

    @Override
    public void searchByGenre(int id) {
        this.genreId = id;
        loadMovies();
    }

    @Override
    public void onRefresh() {
        loadMovies();
    }

    private void loadMovies() {
        view.showProgressMain();
        current_page = 1;
        loadPage(current_page);
    }

    @Override
    public void getNextPage() {
        Log.e("myLog", "current_page" + current_page);
        if (current_page < total_pages) {
            view.showProgressPagination();
            loadPage(++current_page);
        }
    }

    private void loadPage(int pageNumber) {
        compositeDisposable.add(getMovies(pageNumber)
                .subscribe(response -> {
                    view.hideProgress();
                    current_page = pageNumber;
                    total_pages = response.total_pages;
                    if (current_page == 1)
                        if (!response.movies.isEmpty())
                            view.setList(prepareMovieList(response.movies));
                        else
                            view.showPlaceholder(Constants.PlaceholderType.EMPTY);
                    else
                        view.addList(prepareMovieList(response.movies));

                }, throwable -> {
                    view.hideProgress();
                    throwable.printStackTrace();
                    Log.e("myLogs", throwable.getLocalizedMessage());
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

    protected abstract Observable<SearchMovieResponse> getMovies(int page);

    private ArrayList<MovieItemDH> prepareMovieList(ArrayList<MovieItem> items) {
        ArrayList<MovieItemDH> list = new ArrayList<>();
        for (MovieItem item : items) {
            list.add(new MovieItemDH(item));
        }
        return list;
    }
}
