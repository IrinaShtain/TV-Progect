package com.shtainyky.tvproject.presentation.account.details_account;


import android.widget.ImageView;
import android.widget.TextView;

import com.shtainyky.tvproject.R;
import com.shtainyky.tvproject.domain.AccountRepository;
import com.shtainyky.tvproject.presentation.base.BasePresenter;
import com.shtainyky.tvproject.presentation.base.content.ContentFragment;
import com.shtainyky.tvproject.utils.SignedUserManager;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;


/**
 * Created by Bell on 25.05.2017.
 */
@EFragment
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
    TextView tvIncludeAult;

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
        tvIncludeAult.setText(getString(R.string.include_adult, perm));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.unsubscribe();
    }

}
