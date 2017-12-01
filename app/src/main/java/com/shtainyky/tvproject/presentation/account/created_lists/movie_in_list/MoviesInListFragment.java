package com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;

import com.jakewharton.rxbinding2.view.RxMenuItem;
import com.jakewharton.rxbinding2.view.RxView;
import com.shtainyky.tvproject.R;
import com.shtainyky.tvproject.data.models.movie.MovieItem;
import com.shtainyky.tvproject.domain.MovieRepository;
import com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.adapter.MovieItemAdapter;
import com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.adapter.MovieItemDH;
import com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.movie_details.MovieDetailsFragment_;

import com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.search.latest_movies.SearchLatestMovieFragment_;
import com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.search.movies_by_genres.SearchMovieByGenreFragment_;
import com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.search.movies_by_title.SearchMovieByTitleFragment_;
import com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.search.popular_movies.SearchPopularMovieFragment_;
import com.shtainyky.tvproject.presentation.base.refreshable_content.RefreshableFragment;
import com.shtainyky.tvproject.presentation.base.refreshable_content.RefreshablePresenter;
import com.shtainyky.tvproject.presentation.listeners.OnGenreClickListener;
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
import org.androidannotations.annotations.res.AnimationRes;

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

    @AnimationRes(R.anim.menu_fab_open)
    protected Animation mAnimFabOpen;
    @AnimationRes(R.anim.menu_fab_close)
    protected Animation mAnimFabClose;

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
    public void init() {
        mActivity.getToolbarManager().setTitle(listTitle);
        setHasOptionsMenu(true);
        setupRecyclerView();
        setupFABs();
        presenter.subscribe();
    }

    private void setupFABs() {
        fabAdd_VC.setVisibility(View.VISIBLE);
        RxView.clicks(fabAdd_VC)
                .throttleFirst(Constants.CLICK_DELAY, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> presenter.onMainFABClick());
        RxView.clicks(fabFindUsingTitle)
                .throttleFirst(Constants.CLICK_DELAY, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> presenter.onFabFindUsingTitleClick());
        RxView.clicks(fabFindUsingGenre)
                .throttleFirst(Constants.CLICK_DELAY, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> presenter.onFabFindUsingGenreClick());
        RxView.clicks(fabFindPopular)
                .throttleFirst(Constants.CLICK_DELAY, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> presenter.onFabFindPopularClick());
        RxView.clicks(fabFindLatest)
                .throttleFirst(Constants.CLICK_DELAY, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> presenter.onFabFindLatestClick());
    }

    private void setupRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(mActivity, 2);
        rvLists.setLayoutManager(layoutManager);
        rvLists.setAdapter(adapter);
        adapter.setListener(this);
    }


    @Override
    public void closeFabMenu() {
        setClickableViews(false);
        fabAdd_VC.setImageResource(R.drawable.ic_add);
        seAnimation(mAnimFabClose);
        updateContainerAlpha(1f);
    }

    @Override
    public void openFabMenu() {
        fabAdd_VC.setImageResource(R.drawable.ic_close);
        setClickableViews(true);
        seAnimation(mAnimFabOpen);
        updateContainerAlpha(0.15f);
    }

    private void updateContainerAlpha(float value) {
        if (rlPlaceholder_VC.getVisibility() == View.VISIBLE)
            rlPlaceholder_VC.setAlpha(value);
        else
            flContent_VC.setAlpha(value);
    }

    private void setClickableViews(boolean isViewsClickable) {
        fabFindUsingTitle.setClickable(isViewsClickable);
        fabFindUsingGenre.setClickable(isViewsClickable);
        fabFindPopular.setClickable(isViewsClickable);
        fabFindLatest.setClickable(isViewsClickable);
    }

    private void seAnimation(Animation animation) {
        llFindUsingTitle.startAnimation(animation);
        llFindUsingGenre.startAnimation(animation);
        llFindPopular.startAnimation(animation);
        llFindLatest.startAnimation(animation);
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
    public void openSearchByTitleScreen(int listID, ArrayList<MovieItem> movieItems) {
        mActivity.replaceFragment(SearchMovieByTitleFragment_.builder()
                .listID(listID)
                .moviesInList(movieItems)
                .searchType(Constants.SEARCH_TYPE_MOVIES_BY_TITLE)
                .build());
    }

    @Override
    public void openSearchByGenreScreen(int listID, ArrayList<MovieItem> movieItems) {
        mActivity.replaceFragment(SearchMovieByGenreFragment_.builder()
                .listID(listID)
                .moviesInList(movieItems)
                .searchType(Constants.SEARCH_TYPE_MOVIES_BY_GENRE)
                .build());
    }

    @Override
    public void openLatestSearchScreen(int listID, ArrayList<MovieItem> movieItems) {
        mActivity.replaceFragment(SearchLatestMovieFragment_.builder()
                .listID(listID)
                .moviesInList(movieItems)
                .searchType(Constants.SEARCH_TYPE_LATEST_MOVIES)
                .build());
    }

    @Override
    public void openPopularSearchScreen(int listID, ArrayList<MovieItem> movieItems) {
        mActivity.replaceFragment(SearchPopularMovieFragment_.builder()
                .listID(listID)
                .moviesInList(movieItems)
                .searchType(Constants.SEARCH_TYPE_POPULAR_MOVIES)
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
    public void showAlert() {
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

    @Override
    public String getScreenName() {
        return "Movies In List Fragment";
    }
}
