package com.shtainyky.tvproject.presentation.login;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;


import com.jakewharton.rxbinding.view.RxView;
import com.shtainyky.tvproject.R;
import com.shtainyky.tvproject.domain.LoginRepository;
import com.shtainyky.tvproject.presentation.account.AccountActivity_;
import com.shtainyky.tvproject.presentation.base.BaseFragment;
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
@EFragment(R.layout.fragment_login)
public class LoginFragment extends BaseFragment implements LoginContract.LoginView {

    @ViewById
    TextInputEditText text_userName;
    @ViewById
    TextInputEditText text_password;
    @ViewById
    Button bt_signIn;
    @ViewById
    Button bt_signUp;
    @ViewById(R.id.til_password_container)
    TextInputLayout passwordWrapper;
    @ViewById(R.id.til_userName_container)
    TextInputLayout userNameWrapper;

    private LoginContract.LoginPresenter mPresenter;
    @Bean
    protected LoginRepository mLoginRepository;
    @Bean
    protected SignedUserManager userManager;

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
        RxView.clicks(bt_signIn)
                .throttleFirst(Constants.CLICK_DELAY, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> mPresenter.onSignInClick());
        RxView.clicks(bt_signUp)
                .throttleFirst(Constants.CLICK_DELAY, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> mPresenter.onSignUpClick());
        Log.e("myLog","initUI ");
        mPresenter.subscribe();
    }
    @Override
    public void validate() {
        hideKeyboard();
        String username = userNameWrapper.getEditText().getText().toString();
        String password = passwordWrapper.getEditText().getText().toString();
        boolean hasPassword = false;
        boolean hasEmail = false;

        if (username.isEmpty())
            userNameWrapper.setError(getString(R.string.error_empty_user_name));
        else {
            hasEmail = true;
        }
        Log.d("myLog", "password = " + password);
        if (password.isEmpty()) {
            passwordWrapper.setError(getString(R.string.error_empty_password));
        } else {
            if (!validatePassword(password))
                passwordWrapper.setError(getString(R.string.error_not_valid_password));
            else
                hasPassword = true;
        }

        if (hasEmail && hasPassword) {
            userNameWrapper.setErrorEnabled(false);
            passwordWrapper.setErrorEnabled(false);
            mPresenter.validateTokennAndGetSessoinID(username, password);
            Log.d("myLog", "username = " + username);
            Log.d("myLog", "password = " + password);
        }

        if (hasEmail)
            userNameWrapper.setErrorEnabled(false);
        if (hasPassword)
            passwordWrapper.setErrorEnabled(false);

    }

    public boolean validatePassword(String password) {
        return password.length() >= 4;
    }

    private void hideKeyboard() {
        View view = mActivity.getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void showResult(String result) {
        Toast.makeText(mActivity, result, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openAccountView() {
        AccountActivity_.intent(this)
                .flags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
                .start();
    }

    @Override
    public void redirect(String token) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.themoviedb.org/authenticate/" + token));
        mActivity.startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

}
