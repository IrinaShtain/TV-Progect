package com.shtainyky.tvproject.presentation.account.details_account;

import android.util.Log;

import com.shtainyky.tvproject.data.models.account.User;
import com.shtainyky.tvproject.utils.SignedUserManager;

import io.reactivex.disposables.CompositeDisposable;


/**
 * Created by Bell on 25.05.2017.
 */

public class DetailsPresenter implements DetailsContract.DetailsPresenter {

    private DetailsContract.DetailsView view;
    private SignedUserManager userManager;
    private CompositeDisposable сompositeDisposable;
    private DetailsContract.DetailsModel model;

    public DetailsPresenter(DetailsContract.DetailsView view, SignedUserManager userManager, DetailsContract.DetailsModel model) {
        this.view = view;
        this.model = model;
        this.userManager = userManager;
        сompositeDisposable = new CompositeDisposable();
        view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        User currentUser = userManager.getCurrentUser();
        if (currentUser == null) {
            сompositeDisposable.addAll(model.getUserDetails(userManager.getSessionId()).subscribe(
                    user -> {
                        userManager.updateUser(user);
                        displayUserData(user);
                    }, throwable -> {
                        Log.e("myLog", "DetailsPresenter subscribe throwable ");
                    }
            ));
        } else {
            displayUserData(currentUser);
        }

    }

    private void displayUserData(User user) {
        view.setUserNick(user.username);
        view.setUserName(user.name);
        view.setAdultPermission(user.include_adult);
    }

    @Override
    public void unsubscribe() {
        сompositeDisposable.clear();
    }

}
