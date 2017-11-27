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

    private int totalPages = Integer.MAX_VALUE;
    private int currentPage;
    private int totalResults;

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
        user = userManager.getCurrentUser();
        Log.e("myLog", "userManager.getSessionId" + userManager.getSessionId());
        view.showProgressMain();
        loadPage(1);
    }

    @Override
    public void getNextPage() {
        if (currentPage < totalPages) {
            view.showProgressPagination();
            loadPage(currentPage + 1);
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
                            totalPages = userListResponse.total_pages;
                            currentPage = pageNumber;
                            totalResults = userListResponse.total_results;
                            if (pageNumber == 1) {
                                if (userListResponse.lists.isEmpty())
                                    view.showPlaceholder(Constants.PlaceholderType.EMPTY);
                                else
                                    view.setLists(prepareList(userListResponse.lists));
                            } else {
                                // TODO: 26.05.2017 Backend returns duplicate
                                view.addLists(prepareList(userListResponse.lists));
                            }
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
    public void showDetails(int lisID, CreatedListsDH item) {
        view.openListDetails(lisID, item.getListsName());
    }

    @Override
    public void removeList(CreatedListsDH item, int pos) {
        view.showProgressPagination();
        compositeDisposable.add(model.deleteList(item.getListsID(), userManager.getSessionId())
                .subscribe(responseMessage -> {
                    view.hideProgress();
                    view.deleteItem(pos);
                    --totalResults;
                    checkEmptyList();
                }, throwable -> {
                    view.hideProgress();
                    Log.e("myLog", "throwable " + throwable.getLocalizedMessage());
                    if (throwable.getMessage().equals("HTTP 500 Internal Server Error")) {// backend bug :(
                        view.deleteItem(pos);
                        --totalResults;
                        checkEmptyList();
                    } else if (throwable instanceof ConnectionException) {
                        view.showMessage(Constants.MessageType.CONNECTION_PROBLEMS);
                    } else {
                        view.showMessage(Constants.MessageType.UNKNOWN);
                    }
                }));
    }

    private void checkEmptyList() {
        if (totalResults == 0)
            view.showPlaceholder(Constants.PlaceholderType.EMPTY);
    }

    @Override
    public void showResult(int resultID, String title, String description) {
        switch (resultID) {
            case Constants.ERROR_CODE_CONNECTION_LOST:
                showConnectionError();
                break;
            case Constants.ERROR_CODE_UNKNOWN:
                showUnknownError();
                break;
            default:
                view.showMessage(Constants.MessageType.NEW_LIST_CREATED_SUCCESSFULLY);
                ++totalResults;
                ListItem listItem = new ListItem();
                listItem.id = resultID;
                listItem.description = description;
                listItem.name = title;
                view.addItem(new CreatedListsDH(listItem));
        }
    }

    private void showConnectionError() {
        if (totalPages != Integer.MAX_VALUE)
            view.showMessage(Constants.MessageType.CONNECTION_PROBLEMS);
        else
            view.showPlaceholder(Constants.PlaceholderType.NETWORK);
    }

    private void showUnknownError() {
        if (totalPages != Integer.MAX_VALUE)
            view.showMessage(Constants.MessageType.UNKNOWN);
        else
            view.showPlaceholder(Constants.PlaceholderType.UNKNOWN);
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    private Consumer<Throwable> throwableConsumer = throwable -> {
        Log.d("myLogs", "Error! " + throwable.getMessage());
        throwable.printStackTrace();
        view.hideProgress();
        if (totalPages != Integer.MAX_VALUE)
            if (throwable instanceof ConnectionException) {
                view.showMessage(Constants.MessageType.CONNECTION_PROBLEMS);
            } else {
                view.showMessage(Constants.MessageType.UNKNOWN);
            }
        else if (throwable instanceof ConnectionException) {
            view.showPlaceholder(Constants.PlaceholderType.NETWORK);
        } else {
            view.showPlaceholder(Constants.PlaceholderType.UNKNOWN);
        }
    };
}
