package com.shtainyky.tvproject.presentation.account.created_lists;

import com.shtainyky.tvproject.data.models.account.CreatedListsData;
import com.shtainyky.tvproject.data.models.response.ResponseMessage;
import com.shtainyky.tvproject.presentation.account.created_lists.adapter.CreatedListsDH;
import com.shtainyky.tvproject.presentation.base.BaseView;
import com.shtainyky.tvproject.presentation.base.content.ContentView;
import com.shtainyky.tvproject.presentation.base.refreshable_content.RefreshablePresenter;

import java.util.ArrayList;

import io.reactivex.Observable;


/**
 * Created by Bell on 25.05.2017.
 */

public interface CreatedListsContract {

    interface CreatedListsView extends BaseView<CreatedListsContract.CreatedListsPresenter>, ContentView {
        void setLists(ArrayList<CreatedListsDH> createdListsDHs);
        void addLists(ArrayList<CreatedListsDH> createdListsDHs);
        void openListDetails(int lisID, String listsName);
        void deleteItem(int pos);
        void addItem(CreatedListsDH createdListsDH);
    }

    interface CreatedListsPresenter extends RefreshablePresenter {
        void getNextPage();
        void loadPage(int pageNumber);
        void showDetails(int lisID, CreatedListsDH item);
        void removeList(CreatedListsDH item, int pos);
        void showResult(int resultID, String title, String description);
    }

    interface CreatedListsModel {
        Observable<CreatedListsData> getLists(int userID, String sessionID, int page);
        Observable<ResponseMessage> deleteList(int listID, String sessionID);
    }
}
