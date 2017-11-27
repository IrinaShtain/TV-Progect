package com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.search_movie;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.shtainyky.tvproject.R;
import com.shtainyky.tvproject.data.models.movie.MovieItem;
import com.shtainyky.tvproject.domain.MovieRepository;
import com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.adapter.MovieItemAdapter;
import com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.adapter.MovieItemDH;
import com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.movie_details.MovieDetailsFragment_;
import com.shtainyky.tvproject.presentation.base.refreshable_content.RefreshableFragment;
import com.shtainyky.tvproject.presentation.base.refreshable_content.RefreshablePresenter;
import com.shtainyky.tvproject.presentation.listeners.EndlessScrollListener;
import com.shtainyky.tvproject.presentation.listeners.OnCardClickListener;
import com.shtainyky.tvproject.utils.Constants;

import org.androidannotations.annotations.AfterInject;
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
public class SearchMovieFragment extends RefreshableFragment implements SearchMovieContract.SearchMovieView, OnCardClickListener {
    @ViewById
    RecyclerView rvLists;
    @ViewById
    EditText tvSearch;
    @ViewById
    Button bt_search;
    @ViewById
    ImageView ivPlaceholderImage;
    @ViewById
    TextView tvPlaceholderMessage;
    @ViewById
    RelativeLayout rlPlaceholder;

    @FragmentArg
    protected int listID;
    @FragmentArg
    protected ArrayList<MovieItem> moviesInList;

    @Bean
    protected MovieRepository repository;

    private SearchMovieContract.SearchMoviePresenter presenter;
    protected EndlessScrollListener scrollListener;
    @Bean
    protected MovieItemAdapter listAdapter;

    @AfterInject
    @Override
    public void initPresenter() {
        new SearchMoviePresenter(this, repository);
    }

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
        mActivity.getToolbarManager().setTitle(R.string.title_find_movie);
        RxView.clicks(bt_search)
                .throttleFirst(Constants.CLICK_DELAY, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> {
                    hideKeyboard();
                    rlPlaceholder.setVisibility(View.GONE);
                    presenter.onSearchClick(tvSearch.getText().toString());
                });
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(mActivity, 2);
        rvLists.setLayoutManager(layoutManager);
        listAdapter.setListener(this);
        rvLists.setAdapter(listAdapter);
        scrollListener = new EndlessScrollListener(layoutManager, () -> {
            presenter.getNextPage();
            Log.e("myLog", "initUI getNextPage ");
            return true;
        });
        rvLists.addOnScrollListener(scrollListener);
    }


    @Override
    public void setList(ArrayList<MovieItemDH> movieDHs) {
        hideKeyboard();
        scrollListener.reset();
        rvLists.setVisibility(View.VISIBLE);
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
    public void showMessage(String message) {
        Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPlaceholder(Constants.PlaceholderType placeholderType) {
        rlPlaceholder.setVisibility(View.VISIBLE);
        rvLists.setVisibility(View.GONE);
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
