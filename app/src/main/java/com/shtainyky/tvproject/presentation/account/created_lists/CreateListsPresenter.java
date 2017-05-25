package com.shtainyky.tvproject.presentation.account.created_lists;

import android.util.Log;

import com.shtainyky.tvproject.utils.SignedUserManager;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Bell on 25.05.2017.
 */

public class CreateListsPresenter implements CreatedListsContract.CreatedListsPresenter {
    private CreatedListsContract.CreatedListsView view;
    private SignedUserManager userManager;
    private CompositeSubscription compositeSubscription;
    private CreatedListsContract.CreatedListsModel model;

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
        view.setupID(userManager.getCurrentUser().id);
        Log.e("myLog", "userManager.getCurrentUser().id " + userManager.getCurrentUser().id);
        Log.e("myLog", "userManager.getSessionId " + userManager.getSessionId());
    }

    @Override
    public void unsubscribe() {

    }
}
