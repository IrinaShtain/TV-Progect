package com.shtainyky.tvproject.presentation.login;

import android.util.Log;

import com.shtainyky.tvproject.data.exceptions.ConnectionException;
import com.shtainyky.tvproject.utils.Constants;
import com.shtainyky.tvproject.utils.SignedUserManager;


import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Bell on 23.05.2017.
 */

public class LoginPresenter implements LoginContract.LoginPresenter {

    private LoginContract.LoginView mView;
    private CompositeDisposable mCompositeDisposable;
    private LoginContract.LoginModel model;
    private SignedUserManager userManager;

    public LoginPresenter(LoginContract.LoginView view, LoginContract.LoginModel model, SignedUserManager userManager) {
        mView = view;
        this.model = model;
        this.userManager = userManager;
        mView.setPresenter(this);
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onSignInClick(String userName, String password) {
        boolean hasPassword = false;
        boolean hasEmail = false;

        if (userName.isEmpty())
            mView.showEmptyNameError();
        else {
            hasEmail = true;
        }

        if (password.isEmpty()) {
            mView.showEmptyPasswordError();
        } else {
            if (!validatePassword(password))
                mView.showNotValidPasswordError();
            else
                hasPassword = true;
        }

        if (hasEmail && hasPassword) {
            mView.setPasswordErrorEnabled(false);
            mView.setUserNameErrorEnabled(false);
            validateTokenAndGetSessoinID(userName, password);
        }

        if (hasEmail)
            mView.setUserNameErrorEnabled(false);

        if (hasPassword)
            mView.setPasswordErrorEnabled(false);
    }

    private boolean validatePassword(String password) {
        return password.length() >= 4;
    }

    private void validateTokenAndGetSessoinID(String userName, String password) {
        mView.showProgressMain();
        mCompositeDisposable.add(
                model.getValidatedToken(userName, password, userManager.getAuthToken())
                        .subscribe(loginResponse -> {
                            userManager.saveAuthToken(loginResponse.request_token);
                            getSessionID(userManager.getAuthToken());
                            Log.e("myLog", "getValidatedToken .auth_token " + loginResponse.request_token);
                        }, throwableConsumer));

    }

    private void getSessionID(String validatedToken) {
        mCompositeDisposable.add(model.getSessionID(validatedToken)
                .subscribe(loginResponse -> {
                            userManager.saveSessionID(loginResponse.sessionID);
                            mView.hideProgress();
                            mView.openAccountView();
                            Log.e("myLog", "sessionID " + loginResponse.sessionID);
                        }, throwableConsumer

                ));
    }

    @Override
    public void subscribe() {
        mView.showProgressMain();
        mCompositeDisposable.add(model.getToken()
                .subscribe(loginResponse -> {
                    mView.hideProgress();
                    userManager.saveAuthToken(loginResponse.request_token);
                    Log.e("myLog", "loginResponse.data.token.auth_token " + loginResponse.request_token);
                }, throwableConsumer));
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }

    private Consumer<Throwable> throwableConsumer = throwable -> {
        Log.d("myLogs", "Error! " + throwable.getMessage());
        throwable.printStackTrace();
        mView.hideProgress();
        if (throwable instanceof ConnectionException) {
            mView.showErrorMessage(Constants.MessageType.CONNECTION_PROBLEMS);
        } else {
            mView.showErrorMessage(Constants.MessageType.USER_NOT_REGISTERED);
        }
    };
}
