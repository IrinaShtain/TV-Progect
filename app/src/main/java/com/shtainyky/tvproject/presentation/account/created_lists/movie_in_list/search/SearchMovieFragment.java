package com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.search;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.shtainyky.tvproject.R;
import com.shtainyky.tvproject.data.models.movie.MovieItem;
import com.shtainyky.tvproject.domain.MovieRepository;
import com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.adapter.MovieItemAdapter;
import com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.adapter.MovieItemDH;
import com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.movie_details.MovieDetailsFragment_;
import com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.search.genres_adapter.GenreAdapter;
import com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.search.genres_adapter.GenreDH;
import com.shtainyky.tvproject.presentation.base.refreshable_content.RefreshableFragment;
import com.shtainyky.tvproject.presentation.base.refreshable_content.RefreshablePresenter;
import com.shtainyky.tvproject.presentation.listeners.EndlessScrollListener;
import com.shtainyky.tvproject.presentation.listeners.OnCardClickListener;
import com.shtainyky.tvproject.presentation.listeners.OnGenreClickListener;
import com.shtainyky.tvproject.utils.Constants;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by Bell on 30.05.2017.
 */
@EFragment()
public abstract class SearchMovieFragment extends RefreshableFragment implements SearchMovieContract.SearchMovieView, OnCardClickListener, OnGenreClickListener {
    @ViewById
    protected RecyclerView rvMovies;
    @ViewById
    protected EditText tvSearch;
    @ViewById
    protected Button bt_search;
    @ViewById
    protected ImageView ivPlaceholderImage;
    @ViewById
    protected TextView tvPlaceholderMessage;
    @ViewById
    protected RelativeLayout rlPlaceholder;
    @ViewById
    protected LinearLayout llFindByTitle;
    @ViewById
    protected RecyclerView rvGenres;


    @FragmentArg
    protected int listID;
    @FragmentArg
    protected int searchType;
    @FragmentArg
    protected ArrayList<MovieItem> moviesInList;

    @Bean
    protected MovieRepository repository;
    @Bean
    protected GenreAdapter genreAdapter;

    protected SearchMovieContract.SearchMoviePresenter presenter;
    protected EndlessScrollListener scrollListener;
    @Bean
    protected MovieItemAdapter listAdapter;

    @Override
    public void setPresenter(SearchMovieContract.SearchMoviePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_search;
    }

    @Override
    protected RefreshablePresenter getPresenter() {
        return presenter;
    }

    @AfterViews
    protected void initUI() {
        setupMoviesRecyclerView();
        presenter.subscribe();
    }

    private void setupMoviesRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(mActivity, 2);
        rvMovies.setLayoutManager(layoutManager);
        listAdapter.setListener(this);
        rvMovies.setAdapter(listAdapter);
        scrollListener = new EndlessScrollListener(layoutManager, () -> {
            presenter.getNextPage();
            return true;
        });
        rvMovies.addOnScrollListener(scrollListener);
    }

    @Override
    public void setupSearchByTitle() {
        RxView.clicks(bt_search)
                .throttleFirst(Constants.CLICK_DELAY, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> {
                    hideKeyboard();
                    rlPlaceholder.setVisibility(View.GONE);
                    presenter.onSearchClick(tvSearch.getText().toString());
                });
    }

    @Override
    public void setupGenresList() {
        rvGenres.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        rvGenres.setLayoutManager(layoutManager);
        genreAdapter.setListener(this);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(rvGenres);
        rvGenres.setAdapter(genreAdapter);
    }

    @Override
    public void setGenres(ArrayList<GenreDH> genreItems) {
        rlPlaceholder.setVisibility(View.GONE);
        genreAdapter.setListDH(genreItems);
    }

    @Override
    public void setList(ArrayList<MovieItemDH> movieDHs) {
        hideKeyboard();
        scrollListener.reset();
        rvMovies.setVisibility(View.VISIBLE);
        listAdapter.setListDH(movieDHs);
    }

    @Override
    public void addList(ArrayList<MovieItemDH> movieDHs) {
        listAdapter.addListDH(movieDHs);
    }

    @Override
    public void onCardClick(int itemID, int position) {
        mActivity.replaceFragment(MovieDetailsFragment_.builder()
                .movieID(itemID)
                .moviesInList(moviesInList)
                .listID(listID)
                .build());
    }

    @Override
    public void onGenreClick(int genreId, int position) {
        presenter.searchByGenre(genreId);
        rvGenres.smoothScrollToPosition(position);
    }

    @Override
    public void showPlaceholder(Constants.PlaceholderType placeholderType) {
        rlPlaceholder.setVisibility(View.VISIBLE);
        rvMovies.setVisibility(View.GONE);
        switch (placeholderType) {
            case EMPTY:
                ivPlaceholderImage.setImageResource(R.drawable.placeholder_empty);
                tvPlaceholderMessage.setText(R.string.error_msg_no_movies_with_such_title);
                break;
            case NETWORK:
                ivPlaceholderImage.setImageResource(R.drawable.ic_cloud_off);
                tvPlaceholderMessage.setText(R.string.err_msg_connection_problem);
                break;
            case UNKNOWN:
                ivPlaceholderImage.setImageResource(R.drawable.ic_sentiment_dissatisfied);
                tvPlaceholderMessage.setText(R.string.err_msg_something_goes_wrong);
                break;
            default:
                super.showPlaceholder(placeholderType);
        }
    }
}
