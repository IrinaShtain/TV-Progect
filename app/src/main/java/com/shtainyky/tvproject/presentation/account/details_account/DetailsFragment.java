package com.shtainyky.tvproject.presentation.account.details_account;


import android.widget.Button;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.shtainyky.tvproject.R;
import com.shtainyky.tvproject.domain.AccountRepository;
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
 * Created by Bell on 25.05.2017.
 */
@EFragment(R.layout.fragment_details)
public class DetailsFragment extends BaseFragment implements DetailsContract.DetailsView {


    @Bean
    protected SignedUserManager userManager;
    @Bean
    protected AccountRepository mAccountRepository;
    private DetailsContract.DetailsPresenter mPresenter;

    @ViewById
    TextView tv_userName;

    @ViewById
    TextView tvName;

    @ViewById
    TextView tvIncludeAult;

    @ViewById
    Button bt_myLists;


    @AfterInject
    @Override
    public void initPresenter() {
        new DetailsPresenter(this, userManager, mAccountRepository);
    }

    @Override
    public void setPresenter(DetailsContract.DetailsPresenter presenter) {
        mPresenter = presenter;
    }

    @AfterViews
    protected void initUI() {
        RxView.clicks(bt_myLists)
                .throttleFirst(Constants.CLICK_DELAY, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> mPresenter.onButtonListsClick());
        mPresenter.subscribe();
    }

    @Override
    public void setUserNick(String name) {
        tv_userName.setText(getString(R.string.username, name));
    }

    @Override
    public void setUserName(String name) {
        String validatedName = name.length() == 0 ? "No name" : name;
        tvName.setText(getString(R.string.name, validatedName));
    }

    @Override
    public void setAdultPermission(boolean hasPermission) {
        String perm = hasPermission ? "Yes" : "No";
        tvIncludeAult.setText(getString(R.string.include_adult, perm));
    }

    @Override
    public void openMyLists() {
      //  mActivity.replaceFragment();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

}
