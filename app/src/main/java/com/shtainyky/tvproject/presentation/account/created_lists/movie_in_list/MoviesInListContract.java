package com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list;

import com.shtainyky.tvproject.data.models.movie.MovieItem;
import com.shtainyky.tvproject.data.models.movie.MoviesResponse;
import com.shtainyky.tvproject.data.models.response.ResponseMessage;
import com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.adapter.MovieItemDH;
import com.shtainyky.tvproject.presentation.base.BaseView;
import com.shtainyky.tvproject.presentation.base.content.ContentView;
import com.shtainyky.tvproject.presentation.base.refreshable_content.RefreshablePresenter;

import java.util.ArrayList;

import io.reactivex.Observable;

/**
 * Created by Irina Shtain on 17.11.2017.
 */

public interface MoviesInListContract {
    interface MoviesInListView extends BaseView<MoviesInListContract.MoviesInListPresenter>, ContentView {
        void setLists(ArrayList<MovieItemDH> itemDHS);
        void openMovieDetails(int listID, ArrayList<MovieItem> movieItems);
        void openSearchByTitleScreen(int listID, ArrayList<MovieItem> movieItems);
        void openSearchByGenreScreen(int listID, ArrayList<MovieItem> movieItems);
        void openLatestSearchScreen(int listID, ArrayList<MovieItem> movieItems);
        void openPopularSearchScreen(int listID, ArrayList<MovieItem> movieItems);
        void closeFragment();
        void showAlert();
        void closeFabMenu();
        void openFabMenu();
    }

    interface MoviesInListPresenter extends RefreshablePresenter {
        void showDetails(int lisID);
        void deleteList(int listID);
        void onMainFABClick();
        void menuPressed();
        void onFabFindUsingTitleClick();
        void onFabFindUsingGenreClick();
        void onFabFindPopularClick();
        void onFabFindLatestClick();
    }

    interface MoviesInListModel {
        Observable<MoviesResponse> getMovies(int listID);
        Observable<ResponseMessage> deleteList(int listID);
    }
}
