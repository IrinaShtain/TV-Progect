package com.shtainyky.tvproject.presentation.account.movie.search_movie;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.shtainyky.tvproject.R;
import com.shtainyky.tvproject.domain.MovieRepository;
import com.shtainyky.tvproject.presentation.account.movie.MovieDH;
import com.shtainyky.tvproject.presentation.base.BaseFragment;
import com.shtainyky.tvproject.presentation.listeners.EndlessScrollListener;
import com.shtainyky.tvproject.presentation.listeners.OnCardClickListener;
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
@EFragment(R.layout.fragment_search_movie)
public class SearchMovieFragment extends BaseFragment implements SearchMovieContract.SearchMovieView, OnCardClickListener {
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

    private void hideKeyboard() {
        View view = mActivity.getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
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
    public void onCardClick(int movieID, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.question_about_adding);
        builder.setPositiveButton(R.string.answer_yes, (dialog, which) -> {
            dialog.cancel();
            mPresenter.addMovie(movieID, listID);
        });
        builder.setNegativeButton(R.string.answer_cancel, null);

        builder.show();
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
