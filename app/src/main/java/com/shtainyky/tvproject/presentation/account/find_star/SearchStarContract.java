package com.shtainyky.tvproject.presentation.account.find_star;

import com.shtainyky.tvproject.data.models.star.StarResponse;
import com.shtainyky.tvproject.presentation.account.find_star.adapters.StarDH;
import com.shtainyky.tvproject.presentation.base.BaseView;
import com.shtainyky.tvproject.presentation.base.content.ContentView;
import com.shtainyky.tvproject.presentation.base.refreshable_content.RefreshablePresenter;

import java.util.ArrayList;

import io.reactivex.Observable;


/**
 * Created by Bell on 02.06.2017.
 */

public class SearchStarContract {
    interface SearchStarView extends BaseView<SearchStarContract.SearchStarPresenter>, ContentView {
        void setList(ArrayList<StarDH> starDHs);
        void addList(ArrayList<StarDH> starDHs);
    }

    interface SearchStarPresenter extends RefreshablePresenter {
        void onSearchClick(String star);
        void getNextPage();
    }

    public interface SearchStarModel {
        Observable<StarResponse> getStars(String name, int page);
    }
}
