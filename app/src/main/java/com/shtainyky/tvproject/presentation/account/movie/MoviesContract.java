package com.shtainyky.tvproject.presentation.account.movie;

import com.shtainyky.tvproject.data.models.movie.GenresResponse;
import com.shtainyky.tvproject.data.models.movie.MoviesResponse;
import com.shtainyky.tvproject.data.models.response.ResponseMessage;
import com.shtainyky.tvproject.presentation.base.BasePresenter;
import com.shtainyky.tvproject.presentation.base.BaseView;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by Bell on 29.05.2017.
 */

public class MoviesContract {
    interface MovieView extends BaseView<MoviesContract.MoviePresenter> {
        void setLists(ArrayList<MovieDH> movieDHs);
        void showDialogWithExplanation(int itemID);
        void notifyAdapter(int itemPosition);
        void dismissRefreshing();
        void setEmptyMessage();
        void close();
    }

    interface MoviePresenter extends BasePresenter {
        void deleteItem();
        void loadMovies();
        void onItemClick(int listID, int position);
        void deleteList(int listID);
    }

    public interface MovieModel {
        Observable<GenresResponse> getGenres();
        Observable<MoviesResponse> getMovies(int listID);
        Observable<ResponseMessage> deleteMovie(int listID, int movieID, String sesionID);
        Observable<ResponseMessage> deleteList(int listID, String sessionID);

    }
}
