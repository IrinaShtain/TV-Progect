package com.shtainyky.tvproject.presentation.account.details_account;

import android.util.Log;

import com.shtainyky.tvproject.data.models.account.User;
import com.shtainyky.tvproject.utils.SignedUserManager;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Bell on 25.05.2017.
 */

public class DetailsPresenter implements DetailsContract.DetailsPresenter {

    private DetailsContract.DetailsView view;
    private SignedUserManager userManager;
    private CompositeSubscription compositeSubscription;
    private DetailsContract.DetailsModel model;

    public DetailsPresenter(DetailsContract.DetailsView view, SignedUserManager userManager, DetailsContract.DetailsModel model) {
        this.view = view;
        this.model = model;
        this.userManager = userManager;
        compositeSubscription = new CompositeSubscription();
        view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        if (userManager.getCurrentUser() == null) {
            compositeSubscription.addAll(model.getUserDetails(userManager.getSessionId()).subscribe(
                    user -> {
                        userManager.updateUser(user);
                        view.setUserNick(user.username);
                        view.setUserName(user.name);
                        view.setAdultPermission(user.include_adult);
                        Log.e("myLog", "sessionID " + user.name);

                    }, throwable -> {
                        Log.e("myLog", "DetailsPresenter subscribe throwable ");
                    }
            ));
        }
        else {
            User user = userManager.getCurrentUser();
            view.setUserNick(user.username);
            view.setUserName(user.name);
            view.setAdultPermission(user.include_adult);
        }

    }

    @Override
    public void onButtonListsClick() {

    }

    @Override
    public void unsubscribe() {
        if (compositeSubscription.hasSubscriptions()) compositeSubscription.clear();
    }
}
