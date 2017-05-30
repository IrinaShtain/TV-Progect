package com.shtainyky.tvproject.presentation.account.created_lists.create_list;

import com.shtainyky.tvproject.data.models.request_body.NewListRequest;
import com.shtainyky.tvproject.data.models.response.ResponseMessage;
import com.shtainyky.tvproject.presentation.base.BasePresenter;
import com.shtainyky.tvproject.presentation.base.BaseView;

import rx.Observable;

/**
 * Created by Bell on 30.05.2017.
 */

public interface CreateNewListContract {
    interface CreateNewListView extends BaseView<CreateNewListContract.CreateNewListPresenter> {
        void validate();
        void clearInput();
        void showMessage();
    }

    interface CreateNewListPresenter extends BasePresenter {
        void onButtonAddClick();
        void createNewList(String title, String description);
    }

    interface CreateNewListModel{
        Observable<ResponseMessage> createList(String sessionID, String listTitle, String listDesc);
    }

}
