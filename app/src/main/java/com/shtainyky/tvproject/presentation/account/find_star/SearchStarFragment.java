package com.shtainyky.tvproject.presentation.account.find_star;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.shtainyky.tvproject.R;
import com.shtainyky.tvproject.data.models.star.StarItem;
import com.shtainyky.tvproject.domain.StarRepository;
import com.shtainyky.tvproject.presentation.account.find_star.adapters.SearchStarAdapter;
import com.shtainyky.tvproject.presentation.account.find_star.adapters.StarDH;
import com.shtainyky.tvproject.presentation.account.find_star.stars_details.StarsDetailsFragment_;
import com.shtainyky.tvproject.presentation.base.BaseFragment;
import com.shtainyky.tvproject.presentation.listeners.EndlessScrollListener;
import com.shtainyky.tvproject.presentation.listeners.StarListener;
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
 * Created by Bell on 02.06.2017.
 */
@EFragment(R.layout.fragment_search)
public class SearchStarFragment extends BaseFragment implements SearchStarContract.SearchStarView, StarListener {
    @ViewById
    RecyclerView rvLists;

    @ViewById
    EditText tvSearch;

    @ViewById
    Button bt_search;

    @FragmentArg
    protected int listID;

    @Bean
    protected StarRepository mMovieRepository;

    @Bean
    protected SignedUserManager userManager;

    private SearchStarContract.SearchStarPresenter mPresenter;
    @Bean
    protected SearchStarAdapter listAdapter;

    @AfterInject
    @Override
    public void initPresenter() {
        new SearchStarPresenter(this, mMovieRepository, userManager);
    }


    @Override
    public void setPresenter(SearchStarContract.SearchStarPresenter presenter) {
        mPresenter = presenter;
    }

    @AfterViews
    protected void initUI() {
        mActivity.getToolbarManager().setTitle(R.string.title_read_about_star);
        tvSearch.setHint("Input star's name");
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
    public void getInputText() {
        String title = tvSearch.getText().toString();
        if (title.isEmpty())
            Toast.makeText(getContext(), "Empty name", Toast.LENGTH_LONG).show();
        else {
            mPresenter.makeSearch(title);
            hideKeyboard();
        }
    }

    @Override
    public void setList(ArrayList<StarDH> starDHs) {
        listAdapter.setListDH(starDHs);
    }

    @Override
    public void addList(ArrayList<StarDH> starDHs) {
        listAdapter.addListDH(starDHs);
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void onStarClick(StarItem starItem) {
        mActivity.replaceFragment(StarsDetailsFragment_.builder().starItem(starItem).build());
    }
}
