package com.shtainyky.tvproject.presentation.account.movie;

import android.util.Log;

import com.shtainyky.tvproject.data.models.movie.GenreItem;
import com.shtainyky.tvproject.data.models.movie.MovieItem;
import com.shtainyky.tvproject.utils.SignedUserManager;

import org.androidannotations.annotations.Bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Bell on 29.05.2017.
 */

public class MoviePresenter implements MovieContract.MoviePresenter {
    private MovieContract.MovieView mView;
    private int listID;
    private CompositeSubscription compositeSubscription;
    private MovieContract.MovieModel model;
    private Map<Integer, String> genreMap;
    private int deleteItemID;
    private int deleteItemPosition;
    private SignedUserManager mUserManager;

    public MoviePresenter(MovieContract.MovieView view, int listID, MovieContract.MovieModel model, SignedUserManager userManager) {
        mView = view;
        mUserManager = userManager;
        this.listID = listID;
        this.model = model;

        mView.setPresenter(this);
        compositeSubscription = new CompositeSubscription();
        genreMap = new HashMap();
    }

    @Override
    public void subscribe() {
        compositeSubscription.add(model.getGenres()
                .subscribe(genresList -> {

                    Log.e("myLog", "genresList.size() = " + genresList.genres.size());
                    for (int i = 0; i < genresList.genres.size(); i++) {
                        GenreItem item = genresList.genres.get(i);
                        genreMap.put(item.id, item.name);
                    }
                    loadMovies(true);
                }, throwable -> {
                    loadMovies(false);
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

    private void loadMovies(boolean hasGenres) {
        compositeSubscription.add(model.getMovies(listID)
                .subscribe(moviesList -> {
                    mView.setLists(prepareList(moviesList.movies, hasGenres));
                }, throwable -> {
                    Log.e("myLog", "throwable " + throwable.getMessage());
                }));
    }

    @Override
    public void unsubscribe() {
        if (compositeSubscription.hasSubscriptions()) compositeSubscription.clear();
    }

    @Override
    public void deleteItem() {
        compositeSubscription.add(model.deleteMovie(listID, deleteItemID, mUserManager.getSessionId())
                .subscribe(response -> {
                   if (response.status_code == 13)
                       mView.notifyAdapter(deleteItemPosition);
                }, throwable -> {
                    Log.e("myLog", "throwable " + throwable.getMessage());
                }));
    }

    @Override
    public void onItemClick(int listID, int position) {
        mView.showDialogWithExplanation(listID);
        deleteItemID = listID;
        deleteItemPosition = position;
    }
}
