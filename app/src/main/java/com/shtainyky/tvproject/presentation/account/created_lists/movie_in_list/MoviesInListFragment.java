package com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.shtainyky.tvproject.R;
import com.shtainyky.tvproject.domain.MovieRepository;
import com.shtainyky.tvproject.presentation.account.created_lists.adapter.CreatedListsDH;
import com.shtainyky.tvproject.presentation.account.created_lists.create_list.CreateNewListDialog_;
import com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.adapter.MovieItemAdapter;
import com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.adapter.MovieItemDH;
import com.shtainyky.tvproject.presentation.account.movie.adapter.MoviesAdapter;
import com.shtainyky.tvproject.presentation.account.movie.search_movie.SearchMovieFragment_;
import com.shtainyky.tvproject.presentation.base.refreshable_content.RefreshableFragment;
import com.shtainyky.tvproject.presentation.base.refreshable_content.RefreshablePresenter;
import com.shtainyky.tvproject.presentation.listeners.OnCardClickListener;
import com.shtainyky.tvproject.utils.Constants;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

/**
 * Created by Irina Shtain on 17.11.2017.
 */
@EFragment
public class MoviesInListFragment extends RefreshableFragment implements MoviesInListContract.MoviesInListView, OnCardClickListener {

    @ViewById
    RecyclerView rvLists;

    @FragmentArg
    protected int listID;

    @Bean
    protected MovieItemAdapter adapter;
    @Bean
    protected MovieRepository repository;
    private MoviesInListContract.MoviesInListPresenter presenter;
    private int mPos;

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
        setHasOptionsMenu(true);
        setupRecyclerView();
        setupFAB();
        presenter.subscribe();
    }

    private void setupFAB(){
        fabAdd_VC.setVisibility(View.VISIBLE);
        fabAdd_VC.setOnClickListener(v -> {
            Log.e("myLog", "onClick FAB ");
            mActivity.replaceFragment(SearchMovieFragment_.builder().listID(listID).build());
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
        Toast.makeText(this.getContext(), "itemID" + itemID, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setLists(ArrayList<MovieItemDH> itemDHS) {
        adapter.setListDH(itemDHS);
    }

    @Override
    public void openMovieDetails(int lisID) {

    }

    @Override
    public void closeFragment() {
        mActivity.onBackPressed();
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
    public void showPlaceholder(Constants.PlaceholderType placeholderType) {
        super.showPlaceholder(placeholderType);
        if (placeholderType == Constants.PlaceholderType.EMPTY) {
            ivPlaceholderImage_VC.setImageResource(R.drawable.placeholder_empty_lists);
            tvPlaceholderMessage_VC.setText(R.string.no_movies);
        }
    }
}