package com.shtainyky.tvproject.presentation.account.created_lists;

import android.widget.TextView;

import com.shtainyky.tvproject.R;
import com.shtainyky.tvproject.domain.AccountRepository;
import com.shtainyky.tvproject.presentation.base.BaseFragment;
import com.shtainyky.tvproject.utils.SignedUserManager;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Bell on 25.05.2017.
 */
@EFragment(R.layout.fragment_created_lists)
public class CreatedListsFragment extends BaseFragment implements CreatedListsContract.CreatedListsView {

    @ViewById
    TextView tvId;
    @Bean
    protected SignedUserManager userManager;
    @Bean
    protected AccountRepository mAccountRepository;
    private CreatedListsContract.CreatedListsPresenter mPresenter;

    @AfterInject
    @Override
    public void initPresenter() {
        new CreateListsPresenter(this, userManager, mAccountRepository);
    }

    @Override
    public void setPresenter(CreatedListsContract.CreatedListsPresenter presenter) {
        mPresenter = presenter;
    }

    @AfterViews
    protected void initUI() {
        mPresenter.subscribe();
    }

    @Override
    public void setupID(int id) {
        tvId.setText(String.valueOf(id));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }
}
