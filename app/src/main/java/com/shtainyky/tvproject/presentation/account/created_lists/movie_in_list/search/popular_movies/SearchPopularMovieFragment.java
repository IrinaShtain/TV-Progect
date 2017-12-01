package com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.search.popular_movies;

import com.shtainyky.tvproject.R;
import com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.search.SearchMovieFragment;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

/**
 * Created by Irina Shtain on 30.11.2017.
 */
@EFragment
public class SearchPopularMovieFragment extends SearchMovieFragment {
    @AfterInject
    @Override
    public void initPresenter() {
        new SearchPopularMoviePresenter(this, repository, searchType);
    }

    @AfterViews
    protected void initChildUI() {
        mActivity.getToolbarManager().setTitle(R.string.title_popular_movies);
    }

    @Override
    public String getScreenName() {
        return "Search the Popular Movies screen";
    }
}
