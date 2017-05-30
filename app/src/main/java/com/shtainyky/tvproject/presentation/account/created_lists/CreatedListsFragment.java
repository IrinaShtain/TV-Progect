package com.shtainyky.tvproject.presentation.account.created_lists;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.shtainyky.tvproject.R;
import com.shtainyky.tvproject.domain.AccountRepository;
import com.shtainyky.tvproject.presentation.account.movie.MovieFragment_;
import com.shtainyky.tvproject.presentation.base.BaseFragment;
import com.shtainyky.tvproject.presentation.listeners.EndlessScrollListener;
import com.shtainyky.tvproject.presentation.listeners.OnCardClickListener;
import com.shtainyky.tvproject.utils.SignedUserManager;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

/**
 * Created by Bell on 25.05.2017.
 */
@EFragment(R.layout.fragment_created_lists)
public class CreatedListsFragment extends BaseFragment implements CreatedListsContract.CreatedListsView, OnCardClickListener {

    @ViewById
    RecyclerView rvLists;
    @Bean
    protected SignedUserManager userManager;
    @Bean
    protected AccountRepository mAccountRepository;
    private CreatedListsContract.CreatedListsPresenter mPresenter;

    @Bean
    protected CreatedListsAdapter listAdapter;

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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvLists.setLayoutManager(layoutManager);
        listAdapter.setListener(this);
        rvLists.setAdapter(listAdapter);
        rvLists.addOnScrollListener(new EndlessScrollListener(layoutManager, () -> {
            mPresenter.getNextPage();
            Log.e("myLog", "initUI getNextPage ");
            return true;
        }));
    }

    @Override
    public void setLists(ArrayList<CreatedListsDH> createdListsDHs) {
        listAdapter.setListDH(createdListsDHs);
    }

    @Override
    public void addLists(ArrayList<CreatedListsDH> createdListsDHs) {
        listAdapter.addListDH(createdListsDHs);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    public void onCardClick(int listID) {
        Toast.makeText(getContext(), "listID "+ listID, Toast.LENGTH_LONG).show();
        mActivity.replaceFragment(MovieFragment_.builder().listID(listID).build());
    }
}
