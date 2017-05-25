package com.shtainyky.tvproject.presentation.login;

import android.util.Log;

import com.shtainyky.tvproject.utils.SignedUserManager;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Bell on 23.05.2017.
 */

public class LoginPresenter implements LoginContract.LoginPresenter {

    private LoginContract.LoginView mView;
    private CompositeSubscription compositeSubscription;
    private LoginContract.LoginModel model;
    private SignedUserManager userManager;

    public LoginPresenter(LoginContract.LoginView view, LoginContract.LoginModel model, SignedUserManager userManager) {
        mView = view;
        this.model = model;
        this.userManager = userManager;
        mView.setPresenter(this);
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void onSignInClick() {
        mView.validate();
    }

    @Override
    public void onSignUpClick() {
        if (userManager.getAuthToken() != null)
            mView.redirect(userManager.getAuthToken());
    }

    @Override
    public void validateTokennAndGetSessoinID(String userName, String password) {
        mView.showResult("userName " + userName + " password " + password);

        compositeSubscription.add(model.getValidatedToken(userName, password, userManager.getAuthToken())
                .subscribe(loginResponse -> {
                    userManager.saveAuthToken(loginResponse.request_token);
                    getSessionID(userManager.getAuthToken());

                    Log.e("myLog", "getValidatedToken .auth_token " + loginResponse.request_token);


                }, throwable -> {
                    mView.showResult("Wrong credentials");
                    Log.e("myLog", "request error " + throwable.getMessage());


                }));

    }

    private void getSessionID(String validatedToken) {
        compositeSubscription.add(model.getSessionID(validatedToken)
                .subscribe(loginResponse -> {
                    userManager.saveSessionID(loginResponse.sessionID);
                    mView.showResult("sessionID:" + loginResponse.sessionID);
                    mView.openAccountView();
                    Log.e("myLog", "sessionID " + loginResponse.sessionID);

                }, throwable -> {
                    mView.showResult("Wrong credentials");
                    Log.e("myLog", "request error " + throwable.getMessage());


                }));
    }

    @Override
    public void subscribe() {
        Log.e("myLog", "subscribe ");
        Log.e("myLog", "subscribe model " + String.valueOf(model == null));

        compositeSubscription.add(model.getToken()
                .subscribe(loginResponse -> {
                    mView.showResult(loginResponse.request_token);
                    userManager.saveAuthToken(loginResponse.request_token);
                    Log.e("myLog", "loginResponse.data.token.auth_token " + loginResponse.request_token);
                }, throwable -> {

                    Log.e("myLog", "request error " + throwable.getMessage());
                    throwable.printStackTrace();

                }));
    }

    @Override
    public void unsubscribe() {
        if (compositeSubscription.hasSubscriptions()) compositeSubscription.clear();
    }


}
