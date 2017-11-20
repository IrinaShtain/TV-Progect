package com.shtainyky.tvproject.presentation.account.movie;

import com.shtainyky.tvproject.data.models.movie.GenresResponse;
import com.shtainyky.tvproject.data.models.movie.MoviesResponse;
import com.shtainyky.tvproject.data.models.response.ResponseMessage;
import com.shtainyky.tvproject.presentation.account.movie.adapter.MovieDH;
import com.shtainyky.tvproject.presentation.base.BasePresenter;
import com.shtainyky.tvproject.presentation.base.BaseView;
import com.shtainyky.tvproject.presentation.base.content.ContentView;
import com.shtainyky.tvproject.presentation.base.refreshable_content.RefreshablePresenter;
import com.shtainyky.tvproject.utils.SignedUserManager;

import java.util.ArrayList;

import io.reactivex.Observable;


/**
 * Created by Bell on 29.05.2017.
 */

public class MoviesContract {
    interface MovieView extends BaseView<MoviesContract.MoviePresenter>, ContentView {
        void setLists(ArrayList<MovieDH> movieDHs);
        void showDialogWithExplanation(int itemID);
        void notifyAdapter(int itemPosition);
        void close();
    }

    interface MoviePresenter extends RefreshablePresenter {
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
        SignedUserManager getSignedUserManager();

    }
}
