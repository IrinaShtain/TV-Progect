package com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.search.movies_by_genres;

import com.shtainyky.tvproject.R;
import com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.search.SearchMovieFragment;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

/**
 * Created by Irina Shtain on 30.11.2017.
 */
@EFragment
public class SearchMovieByGenreFragment extends SearchMovieFragment {
    @AfterInject
    @Override
    public void initPresenter() {
        new SearchMovieByGenrePresenter(this, repository, searchType);
    }

    @AfterViews
    protected void initChildUI() {
        mActivity.getToolbarManager().setTitle(R.string.menu_fab_find_by_genres);
    }

    @Override
    public String getScreenName() {
        return "Search Movies by genres screen";
    }
}
