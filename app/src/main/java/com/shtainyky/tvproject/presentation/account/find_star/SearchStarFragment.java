package com.shtainyky.tvproject.presentation.account.find_star;

import android.support.v7.widget.LinearLayoutManager;
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
import com.shtainyky.tvproject.data.models.star.StarItem;
import com.shtainyky.tvproject.domain.StarRepository;
import com.shtainyky.tvproject.presentation.account.find_star.adapters.SearchStarAdapter;
import com.shtainyky.tvproject.presentation.account.find_star.adapters.StarDH;
import com.shtainyky.tvproject.presentation.account.find_star.stars_details.StarsDetailsFragment_;
import com.shtainyky.tvproject.presentation.base.BaseFragment;
import com.shtainyky.tvproject.presentation.base.refreshable_content.RefreshableFragment;
import com.shtainyky.tvproject.presentation.base.refreshable_content.RefreshablePresenter;
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
@EFragment
public class SearchStarFragment extends RefreshableFragment implements SearchStarContract.SearchStarView, StarListener {
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
    protected RefreshablePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_search;
    }

    @Override
    public void setPresenter(SearchStarContract.SearchStarPresenter presenter) {
        mPresenter = presenter;
    }

    @AfterViews
    protected void initUI() {
        mActivity.getToolbarManager().setTitle(R.string.title_read_about_star);
        tvSearch.setHint(R.string.hint_input_stars_name);
        RxView.clicks(bt_search)
                .throttleFirst(Constants.CLICK_DELAY, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> {
                    mPresenter.onSearchClick(tvSearch.getText().toString());
                    hideKeyboard();
                });
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
    public void setList(ArrayList<StarDH> starDHs) {
        rvLists.setVisibility(View.VISIBLE);
        listAdapter.setListDH(starDHs);
    }

    @Override
    public void addList(ArrayList<StarDH> starDHs) {
        listAdapter.addListDH(starDHs);
    }

    @Override
    public void onStarClick(StarItem starItem) {
        mActivity.replaceFragment(StarsDetailsFragment_.builder().starItem(starItem).build());
    }

    @Override
    public void showPlaceholder(Constants.PlaceholderType placeholderType) {
        rlPlaceholder.setVisibility(View.VISIBLE);
        rvLists.setVisibility(View.GONE);
        switch (placeholderType) {
            case EMPTY:
                ivPlaceholderImage.setImageResource(R.drawable.placeholder_empty_lists);
                tvPlaceholderMessage.setText(R.string.error_msg_no_star_with_such_name);
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
