package com.shtainyky.tvproject.presentation.account.movie.movie_details;

import android.util.Log;

import com.shtainyky.tvproject.utils.SignedUserManager;

import io.reactivex.disposables.CompositeDisposable;


/**
 * Created by Bell on 21.06.2017.
 */

public class MovieDetailsPresenter implements MovieDetailsContract.MovieDetailsPresenter {

    private MovieDetailsContract.MovieDetailsView mView;
    private CompositeDisposable compositeDisposable;
    private MovieDetailsContract.MovieDetailsModel mModel;
    private SignedUserManager mUserManager;

    public MovieDetailsPresenter(MovieDetailsContract.MovieDetailsView view,
                                 MovieDetailsContract.MovieDetailsModel model,
                                 SignedUserManager userManager) {
        mView = view;
        mModel=model;
        mUserManager = userManager;
        mView.setPresenter(this);
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
    public void addOnlineClicked(int listID, int movieID) {
        compositeDisposable.add(mModel.addMovie(listID, movieID, mUserManager.getSessionId())
                .subscribe(response -> {
                    Log.e("myLog", "movieID " + movieID);
                    mView.showMessage("Movie is successfully added");
                }, throwable -> {
                    mView.showMessage("Error.  Something went wrong");
                    Log.e("myLog", "throwable " + throwable.getMessage());
                }));
    }

    @Override
    public void closeClicked() {
        mView.close();
    }

    @Override
    public void addOfflineClicked() {

    }


}
