package com.shtainyky.tvproject.presentation.account.find_star.stars_details;

import com.shtainyky.tvproject.data.models.star.FamousForItem;
import com.shtainyky.tvproject.data.models.star.StarItem;
import com.shtainyky.tvproject.presentation.account.find_star.stars_details.adapter.FamousForDH;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bell on 21.06.2017.
 */

public class StarsDetailsPresenter implements StarsDetailsContract.StarsDetailsPresenter{

    private StarsDetailsContract.StarsDetailsView view;
    private StarItem starItem;

    public StarsDetailsPresenter(StarsDetailsContract.StarsDetailsView view, StarItem starItem) {
        this.view = view;
        this.starItem = starItem;

        view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        view.setStarDetail(starItem);
        List<FamousForItem> list = starItem.known_for;
        List<FamousForDH> famousForDHs = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            famousForDHs.add(new FamousForDH(list.get(i)));
        }
        view.setFamousForDH(famousForDHs);
    }

    @Override
    public void unsubscribe() {

    }
}
