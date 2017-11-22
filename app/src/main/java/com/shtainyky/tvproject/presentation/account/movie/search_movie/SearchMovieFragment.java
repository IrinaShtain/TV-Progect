package com.shtainyky.tvproject.presentation.account.movie.search_movie;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.shtainyky.tvproject.R;
import com.shtainyky.tvproject.data.models.movie.MovieItem;
import com.shtainyky.tvproject.domain.MovieRepository;
import com.shtainyky.tvproject.presentation.account.movie.movie_details.MovieDetailsFragment_;
import com.shtainyky.tvproject.presentation.account.movie.search_movie.adapter.SearchMovieAdapter;
import com.shtainyky.tvproject.presentation.account.movie.search_movie.adapter.SearchMovieDH;
import com.shtainyky.tvproject.presentation.base.BaseFragment;
import com.shtainyky.tvproject.presentation.listeners.EndlessScrollListener;
import com.shtainyky.tvproject.presentation.listeners.MovieListener;
import com.shtainyky.tvproject.utils.Constants;
import com.shtainyky.tvproject.utils.SignedUserManager;

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
@EFragment(R.layout.fragment_search)
public class SearchMovieFragment extends BaseFragment implements SearchMovieContract.SearchMovieView, MovieListener {
    @ViewById
    RecyclerView rvLists;

    @ViewById
    EditText tvSearch;

    @ViewById
    Button bt_search;

    @FragmentArg
    protected int listID;

    @Bean
    protected MovieRepository mMovieRepository;

    @Bean
    protected SignedUserManager userManager;

    private SearchMovieContract.SearchMoviePresenter mPresenter;
    @Bean
    protected SearchMovieAdapter listAdapter;

    @AfterInject
    @Override
    public void initPresenter() {
        new SearchMoviePresenter(this, mMovieRepository, userManager);
    }

    @Override
    public void setPresenter(SearchMovieContract.SearchMoviePresenter presenter) {
        mPresenter = presenter;
    }

    @AfterViews
    protected void initUI() {
        mActivity.getToolbarManager().setTitle(R.string.title_find_movie);
        RxView.clicks(bt_search)
                .throttleFirst(Constants.CLICK_DELAY, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> mPresenter.onSearchClick());
        setupRecyclerView();
    }

    private void setupRecyclerView() {
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
    public void setList(ArrayList<SearchMovieDH> movieDHs) {
        hideKeyboard();
        listAdapter.setListDH(movieDHs);
    }

    @Override
    public void addList(ArrayList<SearchMovieDH> movieDHs) {
        listAdapter.addListDH(movieDHs);
    }

    @Override
    public void onMovieClick(MovieItem movieItem) {
        mActivity.replaceFragment(MovieDetailsFragment_.builder().movieItem(movieItem).listID(listID).build());
        Log.e("myLog", "movieItem.genres = " + movieItem.genres);
        Log.e("myLog", "movieItem.title = " + movieItem.title);
        Log.e("myLog", "movieItem.overview = " + movieItem.overview);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    public void getInputText() {
        String title = tvSearch.getText().toString();
        if (title.isEmpty())
            Toast.makeText(getContext(), "Empty title", Toast.LENGTH_LONG).show();
        else
            mPresenter.makeSearch(title);

    }


}
