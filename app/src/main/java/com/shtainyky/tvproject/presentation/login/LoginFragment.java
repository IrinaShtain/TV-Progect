package com.shtainyky.tvproject.presentation.login;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.jakewharton.rxbinding2.view.RxView;
import com.shtainyky.tvproject.R;
import com.shtainyky.tvproject.domain.LoginRepository;
import com.shtainyky.tvproject.presentation.account.AccountActivity_;
import com.shtainyky.tvproject.presentation.base.BasePresenter;
import com.shtainyky.tvproject.presentation.base.content.ContentFragment;
import com.shtainyky.tvproject.utils.Constants;
import com.shtainyky.tvproject.utils.SignedUserManager;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.concurrent.TimeUnit;

/**
 * Created by Bell on 23.05.2017.
 */
@EFragment
public class LoginFragment extends ContentFragment implements LoginContract.LoginView {

    @ViewById
    TextInputEditText text_userName;
    @ViewById
    TextInputEditText text_password;
    @ViewById
    Button bt_signIn;
    @ViewById
    TextView bt_signUp;
    @ViewById(R.id.til_password_container)
    TextInputLayout passwordWrapper;
    @ViewById(R.id.til_userName_container)
    TextInputLayout userNameWrapper;

    private LoginContract.LoginPresenter mPresenter;
    @Bean
    protected LoginRepository mLoginRepository;
    @Bean
    protected SignedUserManager userManager;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_login;
    }

    @AfterInject
    @Override
    public void initPresenter() {
        new LoginPresenter(this, mLoginRepository, userManager);
    }

    @Override
    public void setPresenter(LoginContract.LoginPresenter presenter) {
        mPresenter = presenter;
    }

    @AfterViews
    protected void initUI() {
        bt_signUp.setMovementMethod(LinkMovementMethod.getInstance());
        RxView.clicks(bt_signIn)
                .throttleFirst(Constants.CLICK_DELAY, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> {
                    hideKeyboard();
                    mPresenter.onSignInClick(userNameWrapper.getEditText().getText().toString(), passwordWrapper.getEditText().getText().toString());});

        Log.e("myLog", "initUI ");
        mPresenter.subscribe();
    }

    @Override
    public void setUserNameErrorEnabled(boolean isEnabled) {
        userNameWrapper.setErrorEnabled(false);
    }

    @Override
    public void setPasswordErrorEnabled(boolean isEnabled) {
        passwordWrapper.setErrorEnabled(false);
    }

    @Override
    public void showNotValidPasswordError() {
        passwordWrapper.setError(getString(R.string.error_not_valid_password));
    }

    @Override
    public void showEmptyNameError() {
        userNameWrapper.setError(getString(R.string.error_empty_user_name));
    }

    @Override
    public void showEmptyPasswordError() {
        passwordWrapper.setError(getString(R.string.error_empty_password));
    }

    @Override
    public void openAccountView() {
        AccountActivity_.intent(this)
                .flags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
                .start();
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public String getScreenName() {
        return "Login Fragment";
    }
}
