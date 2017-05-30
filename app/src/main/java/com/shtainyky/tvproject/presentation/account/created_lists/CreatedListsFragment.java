package com.shtainyky.tvproject.presentation.account.created_lists;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.shtainyky.tvproject.R;
import com.shtainyky.tvproject.domain.AccountRepository;
import com.shtainyky.tvproject.presentation.account.created_lists.create_list.CreateNewListFragment;
import com.shtainyky.tvproject.presentation.account.created_lists.create_list.CreateNewListFragment_;
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

    @ViewById
    SwipeRefreshLayout swiperefresh;

    @ViewById
    FloatingActionButton fab_add;


    @Bean
    protected SignedUserManager userManager;
    @Bean
    protected AccountRepository mAccountRepository;
    private CreatedListsContract.CreatedListsPresenter mPresenter;

    @Bean
    protected CreatedListsAdapter listAdapter;

    private int listId;
    private int listPosition;

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
        setupRecyclerView();
        setupSwipeToRefresh();

        fab_add.setOnClickListener(v -> {
            Log.e("myLog", "onClick FAB ");
            mActivity.replaceFragment(CreateNewListFragment_.builder().build());
        });


    }

    private void setupRecyclerView(){
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

    private void setupSwipeToRefresh() {
        swiperefresh.setOnRefreshListener(
                () ->{ mPresenter.loadPage(1);}

        );
    }

    @Override
    public void dismissRefreshing() {
        swiperefresh.setRefreshing(false);
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
    public void onCardClick(int listID, int position) {
        listId = listID;
        listPosition = position;
        mPresenter.showDialog();
    }

    public void showMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.question_about_goal);
        builder.setPositiveButton(R.string.answer_delete_list,
                (dialog, which) -> mPresenter.deleteItem(listId));
        builder.setNegativeButton(R.string.answer_open_lish,
                (dialog, which) -> mActivity.replaceFragment(MovieFragment_.builder().listID(listId).build()));

        builder.show();
    }

    @Override
    public void deleteCurrentPosition() {
        listAdapter.deleteItem(listPosition);
    }
}
