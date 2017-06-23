package com.shtainyky.tvproject.presentation.account.find_star;

import com.shtainyky.tvproject.data.models.movie.SearchMovieResponse;
import com.shtainyky.tvproject.data.models.response.ResponseMessage;
import com.shtainyky.tvproject.data.models.star.StarResponse;
import com.shtainyky.tvproject.presentation.account.movie.search_movie.SearchMovieContract;
import com.shtainyky.tvproject.presentation.account.movie.search_movie.SearchMovieDH;
import com.shtainyky.tvproject.presentation.base.BasePresenter;
import com.shtainyky.tvproject.presentation.base.BaseView;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by Bell on 02.06.2017.
 */

public class SearchStarContract {
    interface SearchStarView extends BaseView<SearchStarContract.SearchStarPresenter> {
        void getInputText();
        void setList(ArrayList<StarDH> starDHs);
        void addList(ArrayList<StarDH> starDHs);
        void showMessage(String message);
    }

    interface SearchStarPresenter extends BasePresenter {
        void onSearchClick();
        void makeSearch(String movieTitle);
        void getNextPage();
    }

    public interface SearchStarModel {
        Observable<StarResponse> getStars(String name, int page);
    }
}
