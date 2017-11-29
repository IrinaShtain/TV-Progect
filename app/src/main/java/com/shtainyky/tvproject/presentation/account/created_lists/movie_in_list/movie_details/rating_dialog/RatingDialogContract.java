package com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.movie_details.rating_dialog;

import com.shtainyky.tvproject.data.models.response.ResponseMessage;
import com.shtainyky.tvproject.presentation.base.BasePresenter;
import com.shtainyky.tvproject.presentation.base.BaseView;

import io.reactivex.Observable;

/**
 * Created by Irina Shtain on 29.11.2017.
 */

public interface RatingDialogContract {
    interface RatingDialogView extends BaseView<RatingDialogContract.RatingDialogPresenter> {
        void updateTargetFragment(int resultCode);
        void showProgress();
        void hideProgress();
        void showRatingError();
        void hideRatingError();
    }

    interface RatingDialogPresenter extends BasePresenter {
        void sendRating(float rating);
    }

    interface RatingDialogModel {
        Observable<ResponseMessage> rateMovie(float rating, int movieID);
    }
}
