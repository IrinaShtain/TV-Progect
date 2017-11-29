package com.shtainyky.tvproject.presentation.account.find_star.stars_details;

import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.shtainyky.tvproject.R;
import com.shtainyky.tvproject.data.models.star.FamousForItem;
import com.shtainyky.tvproject.data.models.star.StarItem;
import com.shtainyky.tvproject.presentation.account.find_star.stars_details.adapter.FamousForAdapter;
import com.shtainyky.tvproject.presentation.account.find_star.stars_details.adapter.FamousForDH;
import com.shtainyky.tvproject.presentation.base.BaseFragment;
import com.shtainyky.tvproject.presentation.base.BasePresenter;
import com.shtainyky.tvproject.presentation.base.content.ContentFragment;
import com.shtainyky.tvproject.utils.Constants;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bell on 21.06.2017.
 */
@EFragment
public class StarsDetailsFragment extends ContentFragment implements StarsDetailsContract.StarsDetailsView {

    @ViewById
    TextView tvName;
    @ViewById
    ImageView imageView;
    @ViewById
    TextView tv_popularity;
    @ViewById
    TextView tvFamousFor;
    @ViewById
    RecyclerView rvListFamousFor;

    @ViewById
    protected CollapsingToolbarLayout collapsingToolbar;
    @ViewById
    protected Toolbar toolbar;
    @ViewById
    protected AppBarLayout appBarLayout;

    @ColorRes(R.color.colorPrimary)
    protected int toolbarColor;

    @FragmentArg
    protected StarItem starItem;
    @Bean
    protected FamousForAdapter listAdapter;
    private StarsDetailsContract.StarsDetailsPresenter presenter;
    @AfterInject
    @Override
    public void initPresenter() {
        new StarsDetailsPresenter(this, starItem);
    }

    @Override
    public void setPresenter(StarsDetailsContract.StarsDetailsPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_star_details;
    }

    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }

    @AfterViews
    protected void initUI() {
        toolbar.setNavigationOnClickListener(v -> mActivity.onBackPressed());
        setupRecyclerView();
        setupCollapsingToolbar();
        presenter.subscribe();
    }

    private void setupCollapsingToolbar(){
        mActivity.getToolbarManager().displayToolbar(false);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            int scrollRange = -1;
            int collapseOffset = -1;

            @Override
            public void onOffsetChanged(final AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                    collapseOffset = appBarLayout.getHeight() / 3;
                }
                if (scrollRange + verticalOffset <= collapseOffset) {
                    toolbar.setBackgroundColor(toolbarColor);
                } else {
                    toolbar.setBackgroundResource(R.drawable.bg_title_black_gradient);
                    toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
                }
            }
        });
    }

    @Override
    public void setStarDetail(StarItem starItem) {
        collapsingToolbar.setTitle(starItem.name);
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);
        tv_popularity.setText(getResources().getString(R.string.popularity, String.valueOf(starItem.popularity)));
        Picasso.with(getContext())
                .load(starItem.avatarUrl)
                .error(R.drawable.placehoder_star)
                .into(imageView);
    }

    @Override
    public void setFamousForDH(List<FamousForDH> famousForDHs) {
        listAdapter.setListDH(famousForDHs);
    }

    @Override
    public void showNoFamousForMovies() {
        tvFamousFor.setText(R.string.msg_no_famous_for_movies);
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        rvListFamousFor.setLayoutManager(layoutManager);
        rvListFamousFor.setAdapter(listAdapter);
    }

    @Override
    public void showPlaceholder(Constants.PlaceholderType placeholderType) {
        super.showPlaceholder(placeholderType);
        mActivity.getToolbarManager().displayToolbar(true);
    }

    @Override
    public void showProgressMain() {
        super.showProgressMain();
        mActivity.getToolbarManager().displayToolbar(true);
    }

    @Override
    public void hideProgress() {
        super.hideProgress();
        mActivity.getToolbarManager().displayToolbar(false);
    }

    @Override
    public void onDestroyView() {
        mActivity.getToolbarManager().displayToolbar(true);
        super.onDestroyView();
    }

    @Override
    public String getScreenName() {
        return "Stars Details Fragment";
    }
}
