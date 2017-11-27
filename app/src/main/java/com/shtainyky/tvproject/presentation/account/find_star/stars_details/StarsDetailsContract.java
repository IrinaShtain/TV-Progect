package com.shtainyky.tvproject.presentation.account.find_star.stars_details;

import com.shtainyky.tvproject.data.models.star.StarItem;
import com.shtainyky.tvproject.presentation.account.find_star.stars_details.adapter.FamousForDH;
import com.shtainyky.tvproject.presentation.base.BasePresenter;
import com.shtainyky.tvproject.presentation.base.BaseView;
import com.shtainyky.tvproject.presentation.base.content.ContentView;

import java.util.List;

/**
 * Created by Bell on 21.06.2017.
 */

public class StarsDetailsContract {
    interface StarsDetailsView extends BaseView<StarsDetailsContract.StarsDetailsPresenter>, ContentView{
        void setStarDetail(StarItem starItem);
        void setFamousForDH(List<FamousForDH> famousForDHs);
        void showNoFamousForMovies();
    }

    interface StarsDetailsPresenter extends BasePresenter {

    }

}
