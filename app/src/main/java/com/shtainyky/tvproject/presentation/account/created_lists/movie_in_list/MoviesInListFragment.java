package com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.jakewharton.rxbinding2.view.RxMenuItem;
import com.shtainyky.tvproject.R;
import com.shtainyky.tvproject.data.models.movie.MovieItem;
import com.shtainyky.tvproject.domain.MovieRepository;
import com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.adapter.MovieItemAdapter;
import com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.adapter.MovieItemDH;
import com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.movie_details.MovieDetailsFragment_;
import com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.search_movie.SearchMovieFragment_;
import com.shtainyky.tvproject.presentation.base.refreshable_content.RefreshableFragment;
import com.shtainyky.tvproject.presentation.base.refreshable_content.RefreshablePresenter;
import com.shtainyky.tvproject.presentation.listeners.OnCardClickListener;
import com.shtainyky.tvproject.utils.Constants;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by Irina Shtain on 17.11.2017.
 */
@EFragment
@OptionsMenu(R.menu.menu_delete)
public class MoviesInListFragment extends RefreshableFragment implements MoviesInListContract.MoviesInListView, OnCardClickListener {

    @ViewById
    RecyclerView rvLists;

    @FragmentArg
    protected int listID;
    @FragmentArg
    protected String listTitle;

    @Bean
    protected MovieItemAdapter adapter;
    @Bean
    protected MovieRepository repository;
    private MoviesInListContract.MoviesInListPresenter presenter;

    @OptionsMenuItem(R.id.delete)
    protected MenuItem menuDelete;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_recycler_view;
    }

    @AfterInject
    @Override
    public void initPresenter() {
        new MoviesInListPresenter(this, repository, listID);
    }

    @Override
    public void setPresenter(MoviesInListContract.MoviesInListPresenter presenter) {
        this.presenter = presenter;
    }

    @AfterViews
    public void init(){
        mActivity.getToolbarManager().setTitle(listTitle);
        setHasOptionsMenu(true);
        setupRecyclerView();
        setupFAB();
        presenter.subscribe();
    }

    private void setupFAB(){
        fabAdd_VC.setVisibility(View.VISIBLE);
        fabAdd_VC.setOnClickListener(v -> {
            Log.e("myLog", "onClick FAB ");
            presenter.onFABClick();
        });
    }

    private void setupRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(mActivity, 2);
        rvLists.setLayoutManager(layoutManager);
        rvLists.setAdapter(adapter);
        adapter.setListener(this);
    }

    @Override
    protected RefreshablePresenter getPresenter() {
        return presenter;
    }

    @Override
    public void onCardClick(int itemID, int position) {
        presenter.showDetails(itemID);
    }

    @Override
    public void setLists(ArrayList<MovieItemDH> itemDHS) {
        adapter.setListDH(itemDHS);
    }

    @Override
    public void openMovieDetails(int movieID, ArrayList<MovieItem> movieItems) {
        mActivity.replaceFragment(MovieDetailsFragment_.builder()
                .movieID(movieID)
                .listID(listID)
                .moviesInList(movieItems)
                .build());
    }

    @Override
    public void openSearchScreen(int listID, ArrayList<MovieItem> movieItems) {
        mActivity.replaceFragment(SearchMovieFragment_.builder()
                .listID(listID)
                .moviesInList(movieItems)
                .build());
    }

    @Override
    public void closeFragment() {
        mActivity.onBackPressed();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        RxMenuItem.clicks(menuDelete)
                .throttleFirst(Constants.CLICK_DELAY, TimeUnit.MILLISECONDS)
                .subscribe(o -> presenter.menuPressed());
    }

    @Override
    public void showAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.question_about_goal);
        builder.setPositiveButton(R.string.answer_yes,
                (dialog, which) -> presenter.deleteList(listID));
        builder.setNegativeButton(R.string.answer_no, null);

        builder.show();
    }

    @Override
    public void showPlaceholder(Constants.PlaceholderType placeholderType) {
        super.showPlaceholder(placeholderType);
        if (placeholderType == Constants.PlaceholderType.EMPTY) {
            ivPlaceholderImage_VC.setImageResource(R.drawable.placeholder_empty);
            tvPlaceholderMessage_VC.setText(R.string.error_msg_no_movies);
        }
    }
}
