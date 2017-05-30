package com.shtainyky.tvproject.presentation.account.movie;

import android.media.tv.TvContract;

import com.shtainyky.tvproject.data.models.account.User;
import com.shtainyky.tvproject.data.models.movie.GenreItem;
import com.shtainyky.tvproject.data.models.movie.GenresResponse;
import com.shtainyky.tvproject.data.models.movie.MoviesResponse;
import com.shtainyky.tvproject.data.models.movie.ResponseMessage;
import com.shtainyky.tvproject.presentation.base.BasePresenter;
import com.shtainyky.tvproject.presentation.base.BaseView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by Bell on 29.05.2017.
 */

public class MovieContract {
    interface MovieView extends BaseView<MovieContract.MoviePresenter> {
        void setLists(ArrayList<MovieDH> movieDHs);
        void showDialogWithExplanation(int itemID);
        void notifyAdapter(int itemPosition);
    }

    interface MoviePresenter extends BasePresenter {
        void deleteItem();
        void onItemClick(int listID, int position);
    }

    public interface MovieModel {
        Observable<GenresResponse> getGenres();
        Observable<MoviesResponse> getMovies(int listID);
        Observable<ResponseMessage> deleteMovie(int listID, int movieID, String sesionID);

    }
}
