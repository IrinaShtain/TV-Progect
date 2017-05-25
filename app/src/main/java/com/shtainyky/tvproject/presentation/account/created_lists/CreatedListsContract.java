package com.shtainyky.tvproject.presentation.account.created_lists;

import com.shtainyky.tvproject.data.models.account.User;
import com.shtainyky.tvproject.presentation.account.details_account.DetailsContract;
import com.shtainyky.tvproject.presentation.base.BasePresenter;
import com.shtainyky.tvproject.presentation.base.BaseView;

import rx.Observable;

/**
 * Created by Bell on 25.05.2017.
 */

public interface CreatedListsContract {

    interface CreatedListsView extends BaseView<CreatedListsContract.CreatedListsPresenter> {

        void setupID(int id);
    }
    interface CreatedListsPresenter extends BasePresenter {


    }
    interface CreatedListsModel {
        Observable<User> getUserDetails(String sessionID);

    }
}
