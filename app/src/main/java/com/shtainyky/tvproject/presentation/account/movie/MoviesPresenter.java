package com.shtainyky.tvproject.presentation.account.movie;

import android.util.Log;

import com.shtainyky.tvproject.data.models.movie.GenreItem;
import com.shtainyky.tvproject.data.models.movie.MovieItem;
import com.shtainyky.tvproject.presentation.account.movie.adapter.MovieDH;
import com.shtainyky.tvproject.utils.SignedUserManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;


/**
 * Created by Bell on 29.05.2017.
 */

public class MoviesPresenter implements MoviesContract.MoviePresenter {
    private MoviesContract.MovieView mView;
    private int listID;
    private CompositeDisposable compositeDisposable;
    private MoviesContract.MovieModel model;
    private Map<Integer, String> genreMap;
    private int deleteItemID;
    private int deleteItemPosition;
    private boolean hasGenres;
    private SignedUserManager mUserManager;

    public MoviesPresenter(MoviesContract.MovieView view, int listID, MoviesContract.MovieModel model, SignedUserManager userManager) {
        mView = view;
        mUserManager = userManager;
        this.listID = listID;
        this.model = model;

        mView.setPresenter(this);
        compositeDisposable = new CompositeDisposable();
        genreMap = new HashMap();
    }

    @Override
    public void subscribe() {
        compositeDisposable.add(model.getGenres()
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

    private ArrayList<MovieDH> prepareList(ArrayList<MovieItem> items, boolean hasGenres) {
        ArrayList<MovieDH> list = new ArrayList<>();

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
            list.add(new MovieDH(item));
        }
        return list;
    }

    @Override
    public void loadMovies() {
        compositeDisposable.add(model.getMovies(listID)
                .subscribe(moviesList -> {
                    Log.e("myLog", "getMovies ");
                    if (moviesList.movies.size() > 0)
                        mView.setLists(prepareList(moviesList.movies, hasGenres));
                    else
                        mView.setEmptyMessage();
                    mView.dismissRefreshing();
                }, throwable -> {
                    mView.setEmptyMessage();
                    Log.e("myLog", "throwable " + throwable.getMessage());
                }));
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void deleteItem() {
        compositeDisposable.add(model.deleteMovie(listID, deleteItemID, mUserManager.getSessionId())
                .subscribe(response -> {
                    if (response.status_code == 13)
                        mView.notifyAdapter(deleteItemPosition);
                }, throwable -> {
                    Log.e("myLog", "throwable " + throwable.getMessage());
                }));
    }

    @Override
    public void deleteList(int listID) {
        Log.e("myLog", "deleteItem listID = " + listID);
        compositeDisposable.add(model.deleteList(listID, mUserManager.getSessionId())
                .subscribe(responseMessage -> {
                    mView.close();
                }, throwable -> {
                    Log.e("myLog", "throwable " + throwable.getLocalizedMessage());
                    if (throwable.getMessage().equals("HTTP 500 Internal Server Error"))
                        mView.close();
                }));
    }

    @Override
    public void onItemClick(int listID, int position) {
        mView.showDialogWithExplanation(listID);
        deleteItemID = listID;
        deleteItemPosition = position;
    }
}
