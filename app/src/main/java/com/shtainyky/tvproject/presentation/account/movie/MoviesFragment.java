package com.shtainyky.tvproject.presentation.account.movie;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.shtainyky.tvproject.R;
import com.shtainyky.tvproject.domain.MovieRepository;
import com.shtainyky.tvproject.presentation.account.movie.adapter.MovieDH;
import com.shtainyky.tvproject.presentation.account.movie.adapter.MoviesAdapter;
import com.shtainyky.tvproject.presentation.account.movie.search_movie.SearchMovieFragment_;
import com.shtainyky.tvproject.presentation.base.refreshable_content.RefreshableFragment;
import com.shtainyky.tvproject.presentation.base.refreshable_content.RefreshablePresenter;
import com.shtainyky.tvproject.presentation.listeners.OnCardClickListener;
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
@EFragment
public class MoviesFragment extends RefreshableFragment implements MoviesContract.MovieView, OnCardClickListener {

    @ViewById
    RecyclerView rvLists;


    @FragmentArg
    protected int listID;

    @Bean
    protected MoviesAdapter listAdapter;
    @Bean
    protected MovieRepository repository;
    @Bean
    protected SignedUserManager userManager;

    private MoviesContract.MoviePresenter presenter;

    @AfterViews
    protected void initUI() {
        presenter.subscribe();
        setHasOptionsMenu(true);
        setupRecyclerView();
        fabAdd_VC.setVisibility(View.VISIBLE);
        fabAdd_VC.setOnClickListener(v -> {
            Log.e("myLog", "onClick FAB ");
            mActivity.replaceFragment(SearchMovieFragment_.builder().listID(listID).build());
        });

    }

    @Override
    protected RefreshablePresenter getPresenter() {
        return presenter;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_recycler_view;
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvLists.setLayoutManager(layoutManager);
        listAdapter.setListener(this);
        rvLists.setAdapter(listAdapter);
    }

    @AfterInject
    @Override
    public void initPresenter() {
        new MoviesPresenter(this, listID, repository, userManager);
    }

    @Override
    public void setPresenter(MoviesContract.MoviePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setLists(ArrayList<MovieDH> movieDHs) {
        listAdapter.setListDH(movieDHs);
    }


    @Override
    public void onCardClick(int itemID, int position) {
        presenter.onItemClick(itemID, position);
    }

    @Override
    public void notifyAdapter(int itemPosition) {
        listAdapter.deleteItem(itemPosition);
    }

    @Override
    public void showDialogWithExplanation(int itemID) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.question_about_deleting);
        builder.setPositiveButton(R.string.answer_yes, (dialog, which) -> {
            dialog.cancel();
            presenter.deleteItem();
        });
        builder.setNegativeButton(R.string.answer_cancel, null);

        builder.show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_delete, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(R.string.question_about_goal);
                builder.setPositiveButton(R.string.answer_yes,
                        (dialog, which) -> presenter.deleteList(listID));
                builder.setNegativeButton(R.string.answer_no, null);

                builder.show();
                break;
        }
        return true;
    }

    @Override
    public void close() {
        mActivity.onBackPressed();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.unsubscribe();
    }
}
