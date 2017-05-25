package com.shtainyky.tvproject.presentation.login;

import com.shtainyky.tvproject.data.models.loginization.LoginSession;
import com.shtainyky.tvproject.data.models.loginization.LoginToken;
import com.shtainyky.tvproject.presentation.base.BasePresenter;
import com.shtainyky.tvproject.presentation.base.BaseView;

import rx.Observable;

/**
 * Created by Bell on 23.05.2017.
 */

public interface LoginContract {

    interface LoginView extends BaseView<LoginContract.LoginPresenter> {

        void validate();
        void showResult(String result);
        void openAccountView();
        void redirect(String token);

    }
    interface LoginPresenter extends BasePresenter {
        void onSignInClick();
        void onSignUpClick();
        void validateTokennAndGetSessoinID(String userName, String password);

    }
    interface LoginModel {
        Observable<LoginToken> getToken();
        Observable<LoginSession> getSessionID(String validatedToken);
        Observable<LoginToken> getValidatedToken(String username, String password, String request_token);
    }

}
