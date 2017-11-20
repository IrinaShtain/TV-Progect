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
    private ArrayList<MovieItemDH> dhs;

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
                    if (!moviesList.movies.isEmpty())
                        view.setLists(prepareList(moviesList.movies));
                    else
                        view.showPlaceholder(Constants.PlaceholderType.EMPTY);
                }, throwable -> {
                    view.hideProgress();
                    if (dhs != null)
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
        dhs = new ArrayList<>();
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
        loadMovies();
    }

    @Override
    public void showDetails(int lisID) {

    }

    @Override
    public void showResult(int resultID, String title, String description) {

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
