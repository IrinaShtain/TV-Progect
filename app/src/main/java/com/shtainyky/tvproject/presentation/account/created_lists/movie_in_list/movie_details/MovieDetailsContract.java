package com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.movie_details;

import com.shtainyky.tvproject.data.models.response.ResponseMessage;
import com.shtainyky.tvproject.presentation.base.BasePresenter;
import com.shtainyky.tvproject.presentation.base.BaseView;

import io.reactivex.Observable;


/**
 * Created by Bell on 21.06.2017.
 */

public class MovieDetailsContract {
    interface MovieDetailsView extends BaseView<MovieDetailsContract.MovieDetailsPresenter> {
        void close();
        void showMessage(String message);
    }

    interface MovieDetailsPresenter extends BasePresenter {
        void addOnlineClicked(int listID, int movieID);
        void closeClicked();
        void addOfflineClicked();
    }

    public interface MovieDetailsModel {
        Observable<ResponseMessage> addMovie(int listID, int movieID, String sessionID);

    }
}
