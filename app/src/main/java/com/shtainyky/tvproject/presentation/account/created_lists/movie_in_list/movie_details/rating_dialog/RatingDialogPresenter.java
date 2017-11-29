package com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.movie_details.rating_dialog;

import android.util.Log;

import com.shtainyky.tvproject.data.exceptions.ConnectionException;
import com.shtainyky.tvproject.presentation.account.created_lists.create_list.CreateNewListContract;
import com.shtainyky.tvproject.utils.AnalyticManager;
import com.shtainyky.tvproject.utils.Constants;
import com.shtainyky.tvproject.utils.SignedUserManager;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Irina Shtain on 29.11.2017.
 */

public class RatingDialogPresenter implements RatingDialogContract.RatingDialogPresenter {
    private RatingDialogContract.RatingDialogView view;
    private CompositeDisposable сompositeDisposable;
    private RatingDialogContract.RatingDialogModel model;
    private int movieID;

    public RatingDialogPresenter(RatingDialogContract.RatingDialogView view, RatingDialogContract.RatingDialogModel model, int movieID) {
        this.view = view;
        this.model = model;
        this.movieID = movieID;
        сompositeDisposable = new CompositeDisposable();

        view.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        сompositeDisposable.clear();
    }

    @Override
    public void sendRating(float rating) {
        if (rating <= 0)
            view.showRatingError();
        else {
            view.showProgress();
            view.hideRatingError();
            сompositeDisposable.add(model.rateMovie(rating, movieID)
                    .subscribe(response -> {
                        view.hideProgress();
                        AnalyticManager.trackCustomEvent(Constants.AnalyticCustomEvent.ADDED_MOVIE_RATING);
                        view.updateTargetFragment(0);
                    }, throwable -> {
                        view.hideProgress();
                        if (throwable instanceof ConnectionException) {
                            view.updateTargetFragment(Constants.ERROR_CODE_CONNECTION_LOST);
                        } else {
                            view.updateTargetFragment(Constants.ERROR_CODE_UNKNOWN);
                        }
                        Log.e("myLog", "throwable " + throwable.getMessage());
                    }));

        }
    }

}
