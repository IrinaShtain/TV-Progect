package com.shtainyky.tvproject.presentation.account.created_lists;

import com.shtainyky.tvproject.data.models.account.CreatedListsData;
import com.shtainyky.tvproject.data.models.account.User;
import com.shtainyky.tvproject.presentation.account.details_account.DetailsContract;
import com.shtainyky.tvproject.presentation.base.BasePresenter;
import com.shtainyky.tvproject.presentation.base.BaseView;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by Bell on 25.05.2017.
 */

public interface CreatedListsContract {

    interface CreatedListsView extends BaseView<CreatedListsContract.CreatedListsPresenter> {
        void setLists(ArrayList<CreatedListsDH> createdListsDHs);
        void addLists(ArrayList<CreatedListsDH> createdListsDHs);
    }

    interface CreatedListsPresenter extends BasePresenter {
        void getNextPage();
    }

    interface CreatedListsModel {
        Observable<CreatedListsData> getLists(int userID, String sessionID, int page);

    }
}
