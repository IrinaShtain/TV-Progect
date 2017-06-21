package com.shtainyky.tvproject.presentation.account.created_lists;

import android.util.Log;

import com.shtainyky.tvproject.data.models.account.ListItem;
import com.shtainyky.tvproject.data.models.account.User;
import com.shtainyky.tvproject.utils.SignedUserManager;

import java.util.ArrayList;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Bell on 25.05.2017.
 */

public class CreateListsPresenter implements CreatedListsContract.CreatedListsPresenter {
    private CreatedListsContract.CreatedListsView view;
    private SignedUserManager userManager;
    private CompositeSubscription compositeSubscription;
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
        compositeSubscription = new CompositeSubscription();
        view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        Log.e("myLog", "subscribe");
        user = userManager.getCurrentUser();
        Log.e("myLog", "user.id" + user.id);
        Log.e("myLog", "userManager.getSessionId" + userManager.getSessionId());
        loadPage(1);
    }

    @Override
    public void getNextPage() {
        if (current_page < total_pages)
            loadPage(current_page + 1);
    }
    @Override
    public void loadPage(int pageNumber) {
        compositeSubscription.add(model.getLists(user.id, userManager.getSessionId(), pageNumber)
                .subscribe(userListResponse -> {
                    total_pages = userListResponse.total_pages;
                    current_page = pageNumber;
                    if (pageNumber == 1) {
                        view.setLists(prepareList(userListResponse.lists));
                    } else {
                        // TODO: 26.05.2017 Backend returns duplicate
                        view.addLists(prepareList(userListResponse.lists));
                    }
                    view.dismissRefreshing();
                    Log.e("myLog", "userListResponse.page = " + pageNumber);
                    Log.e("myLog", "userListResponse.lists.size() = " + userListResponse.lists.size());
                    Log.e("myLog", "userListResponse.total_results = " + userListResponse.total_results);
                    Log.e("myLog", "userListResponse.total_results.id = " + userListResponse.lists.get(0).id);

                }, throwable -> {
                    Log.e("myLog", "throwable " + throwable.getMessage());
                }));
    }

    private ArrayList<CreatedListsDH> prepareList(ArrayList<ListItem> items) {
        ArrayList<CreatedListsDH> list = new ArrayList<>();
        for (ListItem item : items) {
            list.add(new CreatedListsDH(item));
        }
        return list;
    }

    @Override
    public void showDialog() {
        view.showMessage();
    }


    @Override
    public void unsubscribe() {
        if (compositeSubscription.hasSubscriptions()) compositeSubscription.clear();
    }


}
