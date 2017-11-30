package com.shtainyky.tvproject.presentation.base.refreshable_content;

import android.support.annotation.LayoutRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.LinearLayout;

import com.shtainyky.tvproject.R;
import com.shtainyky.tvproject.presentation.base.content.ContentFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Irina Shtain on 16.11.2017.
 */

@EFragment
public abstract class RefreshableFragment extends ContentFragment {


    @ViewById
    protected SwipeRefreshLayout swipeContainer_VC;
    @ViewById
    protected FloatingActionButton fabAdd_VC;
    @ViewById
    protected LinearLayout llFindUsingTitle;
    @ViewById
    protected LinearLayout llFindUsingGenre;
    @ViewById
    protected LinearLayout llFindPopular;
    @ViewById
    protected LinearLayout llFindLatest;
    @ViewById
    protected FloatingActionButton fabFindUsingTitle;
    @ViewById
    protected FloatingActionButton fabFindUsingGenre;
    @ViewById
    protected FloatingActionButton fabFindPopular;
    @ViewById
    protected FloatingActionButton fabFindLatest;


    protected abstract RefreshablePresenter getPresenter();

    @LayoutRes
    protected int getRootLayoutRes() {
        return R.layout.view_content_refreshable;
    }

    @AfterViews
    protected void initRefreshing() {
        swipeContainer_VC.setEnabled(false);
        swipeContainer_VC.setColorSchemeColors(ContextCompat.getColor(mActivity, R.color.colorPrimaryDark));
        swipeContainer_VC.setEnabled(true);
        swipeContainer_VC.setOnRefreshListener(() -> getPresenter().onRefresh());
    }

    public void enableRefreshing(boolean isEnabled) {
        swipeContainer_VC.setEnabled(isEnabled);
    }

    public boolean isRefreshing() {
        return swipeContainer_VC.isRefreshing();
    }

    @Override
    public void showProgressPagination() {
        super.showProgressPagination();
        enableRefreshing(false);
    }

    @Override
    public void showProgressMain() {
        super.showProgressMain();
        enableRefreshing(false);
    }

    @Override
    public void hideProgress() {
        super.hideProgress();
        enableRefreshing(true);
        if (swipeContainer_VC.isRefreshing()) swipeContainer_VC.setRefreshing(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fabAdd_VC.setVisibility(View.GONE);
    }
}
