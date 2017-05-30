package com.shtainyky.tvproject.presentation.account.movie;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.shtainyky.tvproject.R;
import com.shtainyky.tvproject.domain.AccountRepository;
import com.shtainyky.tvproject.domain.MovieRepository;
import com.shtainyky.tvproject.presentation.account.created_lists.CreatedListsAdapter;
import com.shtainyky.tvproject.presentation.base.BaseFragment;
import com.shtainyky.tvproject.presentation.listeners.EndlessScrollListener;
import com.shtainyky.tvproject.presentation.listeners.OnCardClickListener;
import com.shtainyky.tvproject.presentation.listeners.OnMovieClickListener;
import com.shtainyky.tvproject.utils.SignedUserManager;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

/**
 * Created by Bell on 29.05.2017.
 */
@EFragment(R.layout.fragment_movies)
public class MovieFragment extends BaseFragment implements MovieContract.MovieView, OnMovieClickListener {

    @ViewById
    RecyclerView rvLists;

    @FragmentArg
    protected int listID;

    @Bean
    protected MoviesAdapter listAdapter;

    @Bean
    protected MovieRepository mMovieRepository;

    @Bean
    protected SignedUserManager userManager;

    private MovieContract.MoviePresenter mPresenter;

    @AfterViews
    protected void initUI() {
        mPresenter.subscribe();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvLists.setLayoutManager(layoutManager);
        listAdapter.setListener(this);
        rvLists.setAdapter(listAdapter);
    }

    @AfterInject
    @Override
    public void initPresenter() {
        new MoviePresenter(this, listID, mMovieRepository, userManager);
    }

    @Override
    public void setPresenter(MovieContract.MoviePresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setLists(ArrayList<MovieDH> movieDHs) {
        listAdapter.setListDH(movieDHs);
    }

    @Override
    public void onMovieClick(int movieID, int position) {
        mPresenter.onItemClick(movieID, position);
    }

    @Override
    public void notifyAdapter(int itemPosition) {
        listAdapter.notifyItemChanged(itemPosition);
    }

    @Override
    public void showDialogWithExplanation(int itemID) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Do you want to delete movie");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            dialog.cancel();
            mPresenter.deleteItem();
        });
        builder.setNegativeButton("Cancel", null);

        builder.show();
    }
}
