package com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.movie_details;

import com.shtainyky.tvproject.data.models.movie.MovieItem;
import com.shtainyky.tvproject.data.models.response.ResponseMessage;
import com.shtainyky.tvproject.presentation.base.BasePresenter;
import com.shtainyky.tvproject.presentation.base.BaseView;
import com.shtainyky.tvproject.presentation.base.content.ContentView;
import com.shtainyky.tvproject.utils.SignedUserManager;

import io.reactivex.Observable;


/**
 * Created by Bell on 21.06.2017.
 */

public class MovieDetailsContract {
    interface MovieDetailsView extends BaseView<MovieDetailsContract.MovieDetailsPresenter>, ContentView {
        void setupButton(boolean isMovieAdded);
        void setupUI(MovieItem movieItem);
        void showRatingDialog();
    }

    interface MovieDetailsPresenter extends BasePresenter {
        void buttonMovieActionClicked(int listID);
        void fabClicked();
        void showResult(int errorCode);
    }

    public interface MovieDetailsModel {
        Observable<ResponseMessage> addMovie(int listID, int movieID);
        Observable<ResponseMessage> deleteMovie(int listID, int movieID);
        Observable<MovieItem> getMovieDetails(int movieID);
    }
}
