package com.shtainyky.tvproject.presentation.account.created_lists.create_list;

import com.shtainyky.tvproject.data.models.response.ResponseMessage;
import com.shtainyky.tvproject.presentation.base.BasePresenter;
import com.shtainyky.tvproject.presentation.base.BaseView;

import io.reactivex.Observable;


/**
 * Created by Bell on 30.05.2017.
 */

public interface CreateNewListContract {
    interface CreateNewListView extends BaseView<CreateNewListContract.CreateNewListPresenter> {
        void clearInput();
        void updateTargetFragment(int resultCode, String title, String description);
        void showTitleError();
        void hideError();
        void showProgress();
        void hideProgress();
    }

    interface CreateNewListPresenter extends BasePresenter {
        void createNewList(String title, String description);
    }

    interface CreateNewListModel{
        Observable<ResponseMessage> createList(String sessionID, String listTitle, String listDesc);
    }

}
