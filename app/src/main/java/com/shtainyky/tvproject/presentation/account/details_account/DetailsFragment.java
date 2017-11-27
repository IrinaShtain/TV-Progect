package com.shtainyky.tvproject.presentation.account.details_account;


import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxMenuItem;
import com.shtainyky.tvproject.R;
import com.shtainyky.tvproject.domain.AccountRepository;
import com.shtainyky.tvproject.presentation.base.BasePresenter;
import com.shtainyky.tvproject.presentation.base.content.ContentFragment;
import com.shtainyky.tvproject.presentation.login.SignUpActivity_;
import com.shtainyky.tvproject.utils.Constants;
import com.shtainyky.tvproject.utils.SignedUserManager;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.ViewById;

import java.util.concurrent.TimeUnit;


/**
 * Created by Bell on 25.05.2017.
 */
@EFragment
@OptionsMenu(R.menu.menu_logout)
public class DetailsFragment extends ContentFragment implements DetailsContract.DetailsView {


    @Bean
    protected SignedUserManager userManager;
    @Bean
    protected AccountRepository mAccountRepository;
    private DetailsContract.DetailsPresenter presenter;

    @ViewById
    TextView tvUserName;
    @ViewById
    ImageView ivUserAvatar;
    @ViewById
    TextView tvName;
    @ViewById
    TextView tvIncludeAdult;

    @OptionsMenuItem(R.id.logout)
    protected MenuItem menulogout;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_details;
    }

    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }

    @AfterInject
    @Override
    public void initPresenter() {
        new DetailsPresenter(this, userManager, mAccountRepository);
    }

    @Override
    public void setPresenter(DetailsContract.DetailsPresenter presenter) {
        this.presenter = presenter;
    }

    @AfterViews
    protected void initUI() {
        mActivity.getToolbarManager().setTitle(R.string.title_my_account);
        presenter.subscribe();
    }

    @Override
    public void setUserNick(String name) {
        tvUserName.setText(getString(R.string.username, name));
    }

    @Override
    public void setUserName(String name) {
        String validatedName = name.length() == 0 ? "No name" : name;
        tvName.setText(getString(R.string.name, validatedName));
    }

    @Override
    public void setAdultPermission(boolean hasPermission) {
        String perm = hasPermission ? "Yes" : "No";
        tvIncludeAdult.setText(getString(R.string.include_adult, perm));
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        RxMenuItem.clicks(menulogout)
                .throttleFirst(Constants.CLICK_DELAY, TimeUnit.MILLISECONDS)
                .subscribe(o -> presenter.menuPressed());
    }

    @Override
    public void showAlertAboutLogout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.question_about_logout);
        builder.setPositiveButton(R.string.answer_yes,
                (dialog, which) -> presenter.clearUser());
        builder.setNegativeButton(R.string.answer_no, null);

        builder.show();
    }

    @Override
    public void openLogin() {
        SignUpActivity_.intent(this)
                .flags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
                .start();
    }

}
