package com.shtainyky.tvproject.presentation.account.movie.search_movie;

import android.util.Log;

import com.shtainyky.tvproject.data.models.movie.GenreItem;
import com.shtainyky.tvproject.data.models.movie.MovieItem;
import com.shtainyky.tvproject.presentation.account.movie.search_movie.adapter.SearchMovieDH;
import com.shtainyky.tvproject.utils.SignedUserManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Bell on 30.05.2017.
 */

public class SearchMoviePresenter implements SearchMovieContract.SearchMoviePresenter {
    private SearchMovieContract.SearchMovieView view;
    private CompositeSubscription compositeSubscription;
    private SearchMovieContract.SearchMovieModel model;
    private int current_page;
    private int total_pages;
    private String movieTitle;
    protected SignedUserManager userManager;
    private Map<Integer, String> genreMap;
    private boolean hasGenres;

    public SearchMoviePresenter(SearchMovieContract.SearchMovieView view,
                                SearchMovieContract.SearchMovieModel model,
                                SignedUserManager userManager) {
        this.view = view;
        this.model = model;
        this.userManager = userManager;
        view.setPresenter(this);
        compositeSubscription = new CompositeSubscription();
        genreMap = new HashMap<>();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        if (compositeSubscription.hasSubscriptions()) compositeSubscription.clear();
    }

    @Override
    public void onSearchClick() {
        view.getInputText();
    }

    @Override
    public void makeSearch(String movieTitle) {
        Log.e("myLog", "makeSearch " + movieTitle);
        this.movieTitle = movieTitle;

        compositeSubscription.add(model.getGenres()
                .subscribe(genresList -> {
                    Log.e("myLog", "genresList.size() = " + genresList.genres.size());
                    for (int i = 0; i < genresList.genres.size(); i++) {
                        GenreItem item = genresList.genres.get(i);
                        genreMap.put(item.id, item.name);
                    }
                    hasGenres = true;
                    loadMovies();
                }, throwable -> {
                    loadMovies();
                    Log.e("myLog", "throwable genresList put" + throwable.getMessage());
                }));

    }

    private void loadMovies() {
        current_page = 1;
        loadPage(current_page);
    }

    @Override
    public void getNextPage() {
        Log.e("myLog", "current_page" + current_page);
        if (current_page < total_pages)
            loadPage(current_page + 1);
    }

    private void loadPage(int pageNumber) {
        compositeSubscription.add(model.getMovies(movieTitle, pageNumber)
                .subscribe(response -> {
                    Log.e("myLog", "response.movies.size() makeSearch " + response.movies.size());
                    Log.e("myLog", "response.movies.size() page " + response.page);
                    Log.e("myLog", "response.movies.size() total_pages " + response.total_pages);
                    Log.e("myLog", "response.movies.size() total_results " + response.total_results);
                    Log.e("myLog", "response.movies.size() total_results " + current_page);
                    current_page = pageNumber;
                    total_pages = response.total_pages;
                    if (current_page == 1)
                        view.setList(prepareList(response.movies));
                    else
                        view.addList(prepareList(response.movies));

                }, throwable -> {
                    Log.e("myLog", "throwable makeSearch" + throwable.getMessage());
                }));
    }

    @Override
    public void addMovie(int movieID, int listID) {
//        compositeSubscription.add(model.addMovie(listID, movieID, userManager.getSessionId())
//                .subscribe(response -> {
//                   view.showMessage("Movie is successfully added");
//                }, throwable -> {
//                    view.showMessage("Error.  Something went wrong");
//                    Log.e("myLog", "throwable " + throwable.getMessage());
//                }));
    }

    private ArrayList<SearchMovieDH> prepareList(ArrayList<MovieItem> items) {
        ArrayList<SearchMovieDH> list = new ArrayList<>();
        for (MovieItem item : items) {
            String genreMovie = "";
            if (hasGenres)
                for (int i = 0; i < item.genre_ids.size(); i++) {
                    genreMovie = genreMovie + genreMap.get(item.genre_ids.get(i));
                    if (i != item.genre_ids.size() - 1) {
                        genreMovie = genreMovie + ", ";
                    }
                }
            else
                genreMovie = "Unknown genres";
            item.setGenres(genreMovie);
            list.add(new SearchMovieDH(item));
        }
        return list;
    }
}
