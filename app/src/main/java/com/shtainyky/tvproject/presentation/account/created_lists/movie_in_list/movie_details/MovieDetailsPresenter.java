package com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.movie_details;

import android.util.Log;

import com.shtainyky.tvproject.data.exceptions.ConnectionException;
import com.shtainyky.tvproject.data.models.movie.MovieItem;
import com.shtainyky.tvproject.utils.Constants;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;


/**
 * Created by Bell on 21.06.2017.
 */

public class MovieDetailsPresenter implements MovieDetailsContract.MovieDetailsPresenter {

    private MovieDetailsContract.MovieDetailsView mView;
    private CompositeDisposable compositeDisposable;
    private MovieDetailsContract.MovieDetailsModel mModel;
    private boolean isMovieAddedToList;
    private int movieID;
    private ArrayList<MovieItem> moviesInList;

    public MovieDetailsPresenter(MovieDetailsContract.MovieDetailsView view,
                                 MovieDetailsContract.MovieDetailsModel model,
                                 int movieID, ArrayList<MovieItem> movies) {
        mView = view;
        mModel = model;
        this.movieID = movieID;
        this.moviesInList = movies;
        mView.setPresenter(this);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {
        Log.e("myLog", "movieID " + movieID);
        mView.showProgressMain();
        compositeDisposable.add(mModel.getMovieDetails(movieID)
                .subscribe(response -> {
                    prepareIsMovieAddedToList();
                    mView.hideProgress();
                    mView.setupUI(response);
                    mView.setupButton(isMovieAddedToList);
                }, throwable -> {
                    mView.hideProgress();
                    throwable.printStackTrace();
                    if (throwable instanceof ConnectionException) {
                        mView.showPlaceholder(Constants.PlaceholderType.NETWORK);
                    } else {
                        mView.showPlaceholder(Constants.PlaceholderType.UNKNOWN);
                    }
                }));
    }

    private void prepareIsMovieAddedToList() {
        for (MovieItem item : moviesInList) {
            if (item.id == movieID) {
                isMovieAddedToList = true;
                break;
            }
        }
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void buttonClicked(int listID) {
        Log.e("myLog", "movieID " + movieID);
        Log.e("myLog", "listID " + listID);
        mView.showProgressPagination();
        if (!isMovieAddedToList)
            compositeDisposable.add(mModel.addMovie(listID, movieID)
                    .subscribe(response -> {
                        mView.hideProgress();
                        mView.showMessage(Constants.MessageType.NEW_MOVIE_ADDED_SUCCESSFULLY);
                        isMovieAddedToList = true;
                        mView.setupButton(true);
                    }, throwableConsumer));
        else {
            compositeDisposable.add(mModel.deleteMovie(listID, movieID)
                    .subscribe(response -> {
                        mView.hideProgress();
                        mView.showMessage(Constants.MessageType.NEW_MOVIE_REMOVED_SUCCESSFULLY);
                        isMovieAddedToList = false;
                        mView.setupButton(false);
                    }, throwableConsumer));
        }
    }

    private Consumer<Throwable> throwableConsumer = throwable -> {
        throwable.printStackTrace();
        mView.hideProgress();
        if (throwable instanceof ConnectionException) {
            mView.showMessage(Constants.MessageType.CONNECTION_PROBLEMS);
        } else {
            mView.showMessage(Constants.MessageType.UNKNOWN);
        }
    };
}
