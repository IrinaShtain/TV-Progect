package com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list;

import android.util.Log;

import com.shtainyky.tvproject.data.exceptions.ConnectionException;
import com.shtainyky.tvproject.data.models.movie.MovieItem;
import com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.adapter.MovieItemDH;
import com.shtainyky.tvproject.utils.Constants;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Irina Shtain on 17.11.2017.
 */

public class MoviesInListPresenter implements MoviesInListContract.MoviesInListPresenter {

    private CompositeDisposable compositeDisposable;
    private MoviesInListContract.MoviesInListView view;
    private MoviesInListContract.MoviesInListModel model;
    private int listID;
    private ArrayList<MovieItem> movieItems;
    private boolean mIsFabOpen = false;

    public MoviesInListPresenter(MoviesInListContract.MoviesInListView view,
                                 MoviesInListContract.MoviesInListModel model,
                                 int listID) {
        this.view = view;
        this.model = model;
        this.listID = listID;

        compositeDisposable = new CompositeDisposable();
        view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        view.showProgressMain();
        loadMovies();

    }

    private void loadMovies() {
        compositeDisposable.add(model.getMovies(listID)
                .subscribe(moviesList -> {
                    Log.e("myLog", "getMovies " + listID);
                    view.hideProgress();
                    movieItems = moviesList.movies;
                    if (!movieItems.isEmpty())
                        view.setLists(prepareList(movieItems));
                    else
                        view.showPlaceholder(Constants.PlaceholderType.EMPTY);
                }, throwable -> {
                    view.hideProgress();
                    if (movieItems != null)
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
                    Log.e("myLog", "throwable " + throwable.getMessage());
                }));
    }

    private ArrayList<MovieItemDH> prepareList(ArrayList<MovieItem> items) {
        ArrayList<MovieItemDH> dhs = new ArrayList<>();
        for (MovieItem item : items) {
            dhs.add(new MovieItemDH(item));
        }
        return dhs;
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void onRefresh() {
        if (mIsFabOpen)
            view.closeFabMenu();
        loadMovies();
    }

    @Override
    public void showDetails(int moviesID) {
        mIsFabOpen = false;
        view.openMovieDetails(moviesID, movieItems);
    }

    @Override
    public void onMainFABClick() {
        if (mIsFabOpen) { //if user touch and FAB is open, close all menu
            view.closeFabMenu();
            mIsFabOpen =false;
        } else {//if user touch and FAB is not open, open all menu
            view.openFabMenu();
            mIsFabOpen = true;
        }
    }

    @Override
    public void onFabFindUsingTitleClick() {
        mIsFabOpen = false;
        view.openSearchScreen(listID, movieItems);
    }

    @Override
    public void onFabFindUsingGenreClick() {

    }

    @Override
    public void onFabFindPopularClick() {

    }

    @Override
    public void menuPressed() {
        view.showAlert();
    }

    @Override
    public void deleteList(int listID) {
        Log.e("myLog", "deleteItem listID = " + listID);
        compositeDisposable.add(model.deleteList(listID, model.getSignedUserManager().getSessionId())
                .subscribe(responseMessage -> {
                    view.hideProgress();
                    view.showMessage(Constants.MessageType.LIST_WAS_DELETED);
                    view.closeFragment();
                }, throwable -> {
                    Log.e("myLog", "throwable " + throwable.getLocalizedMessage());
                    view.hideProgress();
                    if (throwable.getMessage().equals("HTTP 500 Internal Server Error")) { // backend's bug :(
                        view.showMessage(Constants.MessageType.LIST_WAS_DELETED);
                        view.closeFragment();
                    } else if (throwable instanceof ConnectionException) {
                        view.showMessage(Constants.MessageType.CONNECTION_PROBLEMS);
                    } else {
                        view.showMessage(Constants.MessageType.UNKNOWN);
                    }
                }));
    }
}
