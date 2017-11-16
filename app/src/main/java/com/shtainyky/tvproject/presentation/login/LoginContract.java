package com.shtainyky.tvproject.presentation.login;

import com.shtainyky.tvproject.data.models.loginization.LoginSession;
import com.shtainyky.tvproject.data.models.loginization.LoginToken;
import com.shtainyky.tvproject.presentation.base.BasePresenter;
import com.shtainyky.tvproject.presentation.base.BaseView;
import com.shtainyky.tvproject.presentation.base.content.ContentView;

import io.reactivex.Observable;

/**
 * Created by Bell on 23.05.2017.
 */

public interface LoginContract {

    interface LoginView extends BaseView<LoginContract.LoginPresenter>, ContentView {
        void openAccountView();

        void showEmptyNameError();
        void showEmptyPasswordError();
        void showNotValidPasswordError();
        void setUserNameErrorEnabled(boolean isEnabled);
        void setPasswordErrorEnabled(boolean isEnabled);
    }

    interface LoginPresenter extends BasePresenter {
        void onSignInClick(String userName, String password);
    }

    interface LoginModel {
        Observable<LoginToken> getToken();
        Observable<LoginSession> getSessionID(String validatedToken);
        Observable<LoginToken> getValidatedToken(String username, String password, String request_token);
    }

}
