package com.shtainyky.tvproject.presentation.account.details_account;

import com.shtainyky.tvproject.data.models.account.User;
import com.shtainyky.tvproject.presentation.base.BasePresenter;
import com.shtainyky.tvproject.presentation.base.BaseView;

import rx.Observable;

/**
 * Created by Bell on 25.05.2017.
 */

public interface DetailsContract {
    interface DetailsView extends BaseView<DetailsContract.DetailsPresenter> {
        void setUserNick(String name);
        void setUserName(String name);
        void setAdultPermission(boolean hasPermission);
        void openMyLists();
        void openFindingStars();
    }
    interface DetailsPresenter extends BasePresenter {

    }
    interface DetailsModel {
        Observable<User> getUserDetails(String sessionID);

    }
}
