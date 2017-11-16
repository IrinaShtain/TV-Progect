package com.shtainyky.tvproject.presentation.account.created_lists;

import android.util.Log;

import com.shtainyky.tvproject.data.exceptions.ConnectionException;
import com.shtainyky.tvproject.data.models.account.ListItem;
import com.shtainyky.tvproject.data.models.account.User;
import com.shtainyky.tvproject.presentation.account.created_lists.adapter.CreatedListsDH;
import com.shtainyky.tvproject.utils.Constants;
import com.shtainyky.tvproject.utils.SignedUserManager;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;


/**
 * Created by Bell on 25.05.2017.
 */

public class CreateListsPresenter implements CreatedListsContract.CreatedListsPresenter {
    private CreatedListsContract.CreatedListsView view;
    private SignedUserManager userManager;
    private CompositeDisposable compositeDisposable;
    private CreatedListsContract.CreatedListsModel model;
    private User user;

    private int total_pages;
    private int current_page;

    public CreateListsPresenter(CreatedListsContract.CreatedListsView view,
                                SignedUserManager userManager,
                                CreatedListsContract.CreatedListsModel model) {
        this.view = view;
        this.model = model;
        this.userManager = userManager;
        compositeDisposable = new CompositeDisposable();
        view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        Log.e("myLog", "subscribe");
        user = userManager.getCurrentUser();
        Log.e("myLog", "user.id" + user.id);
        Log.e("myLog", "userManager.getSessionId" + userManager.getSessionId());
        view.showProgressMain();
        loadPage(1);
    }

    @Override
    public void getNextPage() {
        if (current_page < total_pages) {
            view.showProgressPagination();
            loadPage(current_page + 1);
        }
    }

    @Override
    public void onRefresh() {
        view.showProgressMain();
        loadPage(1);
    }

    @Override
    public void loadPage(int pageNumber) {
        compositeDisposable.add(
                model.getLists(user.id, userManager.getSessionId(), pageNumber)
                        .subscribe(userListResponse -> {
                            view.hideProgress();
                            total_pages = userListResponse.total_pages;
                            current_page = pageNumber;
                            if (pageNumber == 1) {
                                view.setLists(prepareList(userListResponse.lists));
                            } else {
                                // TODO: 26.05.2017 Backend returns duplicate
                                view.addLists(prepareList(userListResponse.lists));
                            }
                            Log.e("myLog", "userListResponse.page = " + pageNumber);
                            Log.e("myLog", "userListResponse.lists.size() = " + userListResponse.lists.size());
                            Log.e("myLog", "userListResponse.total_results = " + userListResponse.total_results);
                            Log.e("myLog", "userListResponse.total_results.id = " + userListResponse.lists.get(0).id);

                        }, throwableConsumer));
    }

    private ArrayList<CreatedListsDH> prepareList(ArrayList<ListItem> items) {
        ArrayList<CreatedListsDH> list = new ArrayList<>();
        for (ListItem item : items) {
            list.add(new CreatedListsDH(item));
        }
        return list;
    }

    @Override
    public void showDetails() {
        view.openListDetails();
    }

    @Override
    public void removeList(CreatedListsDH item, int pos) {
        Log.e("myLog", "deleteItem listID = " + item.getListsID());
        view.showProgressPagination();
        compositeDisposable.add(model.deleteList(item.getListsID(), userManager.getSessionId())
                .subscribe(responseMessage -> {
                    view.hideProgress();
                    view.deleteItem(pos);
                }, throwable -> {
                    view.hideProgress();
                    Log.e("myLog", "throwable " + throwable.getLocalizedMessage());
                    if (throwable.getMessage().equals("HTTP 500 Internal Server Error")) // backend bug :(
                        view.deleteItem(pos);
                    else if (throwable instanceof ConnectionException) {
                        view.showErrorMessage(Constants.MessageType.CONNECTION_PROBLEMS);
                    } else {
                        view.showErrorMessage(Constants.MessageType.UNKNOWN);
                    }
                }));
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    private Consumer<Throwable> throwableConsumer = throwable -> {
        Log.d("myLogs", "Error! " + throwable.getMessage());
        throwable.printStackTrace();
        view.hideProgress();
        if (throwable instanceof ConnectionException) {
            view.showErrorMessage(Constants.MessageType.CONNECTION_PROBLEMS);
        } else {
            view.showErrorMessage(Constants.MessageType.UNKNOWN);
        }
    };
}
