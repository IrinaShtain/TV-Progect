package com.shtainyky.tvproject.presentation.account.movie;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.shtainyky.tvproject.R;
import com.shtainyky.tvproject.domain.MovieRepository;
import com.shtainyky.tvproject.presentation.account.movie.search_movie.SearchMovieFragment_;
import com.shtainyky.tvproject.presentation.base.BaseFragment;
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
@EFragment(R.layout.fragment_movies)
public class MoviesFragment extends BaseFragment implements MoviesContract.MovieView, OnCardClickListener {

    @ViewById
    RecyclerView rvLists;

    @ViewById
    TextView tv_empty_message;

    @ViewById
    SwipeRefreshLayout swiperefresh;

    @ViewById
    FloatingActionButton fab_add;

    @FragmentArg
    protected int listID;

    @Bean
    protected MoviesAdapter listAdapter;

    @Bean
    protected MovieRepository mMovieRepository;

    @Bean
    protected SignedUserManager userManager;

    private MoviesContract.MoviePresenter mPresenter;

    @AfterViews
    protected void initUI() {
        mPresenter.subscribe();
        setHasOptionsMenu(true);
        setupRecyclerView();
        setupSwipeToRefresh();
        fab_add.setOnClickListener(v -> {
            Log.e("myLog", "onClick FAB ");
            mActivity.replaceFragment(SearchMovieFragment_.builder().listID(listID).build());
        });

    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvLists.setLayoutManager(layoutManager);
        listAdapter.setListener(this);
        rvLists.setAdapter(listAdapter);
    }

    private void setupSwipeToRefresh() {
        swiperefresh.setOnRefreshListener(
                () -> {
                    mPresenter.loadMovies();
                }

        );
    }

    @AfterInject
    @Override
    public void initPresenter() {
        new MoviesPresenter(this, listID, mMovieRepository, userManager);
    }

    @Override
    public void setPresenter(MoviesContract.MoviePresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setLists(ArrayList<MovieDH> movieDHs) {
        tv_empty_message.setVisibility(View.INVISIBLE);
        listAdapter.setListDH(movieDHs);
    }


    @Override
    public void onCardClick(int listID, int position) {
        mPresenter.onItemClick(listID, position);
    }

    @Override
    public void notifyAdapter(int itemPosition) {
        listAdapter.deleteItem(itemPosition);
    }

    @Override
    public void dismissRefreshing() {
        swiperefresh.setRefreshing(false);
    }

    @Override
    public void showDialogWithExplanation(int itemID) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.question_about_deleting);
        builder.setPositiveButton(R.string.answer_yes, (dialog, which) -> {
            dialog.cancel();
            mPresenter.deleteItem();
        });
        builder.setNegativeButton(R.string.answer_cancel, null);

        builder.show();
    }

    @Override
    public void setEmptyMessage() {
        tv_empty_message.setVisibility(View.VISIBLE);
        tv_empty_message.setText(R.string.no_movies);
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
                        (dialog, which) -> mPresenter.deleteList(listID));
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
        mPresenter.unsubscribe();
    }
}
