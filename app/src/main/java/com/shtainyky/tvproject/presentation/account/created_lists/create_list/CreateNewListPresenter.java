package com.shtainyky.tvproject.presentation.account.created_lists.create_list;

import android.util.Log;

import com.shtainyky.tvproject.utils.SignedUserManager;

import io.reactivex.disposables.CompositeDisposable;


/**
 * Created by Bell on 30.05.2017.
 */

public class CreateNewListPresenter implements CreateNewListContract.CreateNewListPresenter {
    private CreateNewListContract.CreateNewListView view;
    private SignedUserManager userManager;
    private CompositeDisposable сompositeDisposable;
    private CreateNewListContract.CreateNewListModel model;

    public CreateNewListPresenter(CreateNewListContract.CreateNewListView view,
                                  SignedUserManager userManager,
                                  CreateNewListContract.CreateNewListModel model) {
        this.view = view;
        this.userManager = userManager;
        this.model = model;
        сompositeDisposable = new CompositeDisposable();
        view.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        сompositeDisposable.clear();
    }

    @Override
    public void onButtonAddClick() {
        view.validate();
    }

    @Override
    public void createNewList(String title, String description) {
        Log.e("myLog", "createNewList ");

        сompositeDisposable.add(model.createList(userManager.getSessionId(), title, description)
                .subscribe(response -> {
                    Log.e("myLog", "createList ");
                    view.clearInput();
                    view.showMessage();
                }, throwable -> {
                    Log.e("myLog", "throwable " + throwable.getMessage());
                }));
    }
}
