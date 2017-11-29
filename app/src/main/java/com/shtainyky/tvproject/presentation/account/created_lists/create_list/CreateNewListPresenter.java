package com.shtainyky.tvproject.presentation.account.created_lists.create_list;

import android.util.Log;

import com.shtainyky.tvproject.data.exceptions.ConnectionException;
import com.shtainyky.tvproject.utils.AnalyticManager;
import com.shtainyky.tvproject.utils.Constants;
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
    public void createNewList(String title, String description) {
        Log.e("myLog", "createNewList ");
        if (title.isEmpty())
            view.showTitleError();
        else {
            view.hideError();
            view.showProgress();
            сompositeDisposable.add(model.createList(userManager.getSessionId(), title, description)
                    .subscribe(response -> {
                        view.clearInput();
                        view.hideProgress();
                        AnalyticManager.trackCustomEvent(Constants.AnalyticCustomEvent.ADDED_NEW_LIST);
                        view.updateTargetFragment(response.list_id, title, description);
                    }, throwable -> {
                        view.hideProgress();
                        if (throwable instanceof ConnectionException) {
                            view.updateTargetFragment(Constants.ERROR_CODE_CONNECTION_LOST, title, description);
                        } else {
                            view.updateTargetFragment(Constants.ERROR_CODE_UNKNOWN, title, description);
                        }
                        Log.e("myLog", "throwable " + throwable.getMessage());
                    }));

        }
    }
}
