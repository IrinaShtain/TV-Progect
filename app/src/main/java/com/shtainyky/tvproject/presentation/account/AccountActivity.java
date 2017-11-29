package com.shtainyky.tvproject.presentation.account;

/**
 * Created by Bell on 25.05.2017.
 */


import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.DimenHolder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.shtainyky.tvproject.R;
import com.shtainyky.tvproject.presentation.account.created_lists.CreatedListsFragment_;
import com.shtainyky.tvproject.presentation.account.details_account.DetailsFragment_;
import com.shtainyky.tvproject.presentation.account.find_star.SearchStarFragment_;
import com.shtainyky.tvproject.presentation.base.BaseActivity;
import com.shtainyky.tvproject.presentation.base.BaseFragment;
import com.shtainyky.tvproject.utils.AnalyticManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Bell on 25.05.2017.
 */
@EActivity(R.layout.activity_container_with_toolbar)
public class AccountActivity extends BaseActivity {

    @ViewById
    public Toolbar toolbar;
    private Drawer result;

    @AfterViews
    protected void initUI() {
        setupToolbar();
        setupNavigationDrawerMenu();
        replaceFragment(DetailsFragment_.builder().build());

        getSupportFragmentManager().addOnBackStackChangedListener(() ->
                enableViews(getSupportFragmentManager().getBackStackEntryCount() > 1));
    }

    @Override
    protected void onResume() {
        super.onResume();
        BaseFragment fragment = (BaseFragment) getSupportFragmentManager().findFragmentById(getContainerId());
        AnalyticManager.trackScreenOpen(this, fragment.getScreenName());
    }

    @Override
    protected Toolbar getToolbar() {
        return toolbar;
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.title_my_account);
        toolbar.setTitleTextColor(Color.WHITE);
    }

    private void setupNavigationDrawerMenu() {
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withHeader(R.layout.activity_navigation_view_header)
                .withHeaderHeight(DimenHolder.fromDp(175))
                .withSelectedItem(-1)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.title_my_account)
                                .withIcon(R.drawable.ic_user_color_primary)
                                .withIdentifier(0)
                                .withSelectedColor(Color.WHITE)
                                .withSelectedTextColor(ContextCompat.getColor(this, R.color.colorAccent))
                                .withSelectedIcon(R.drawable.ic_user_color_accent)
                                .withTextColor(ContextCompat.getColor(this, R.color.primary_dark)),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(R.string.title_my_lists)
                                .withIcon(R.drawable.ic_list_movies)
                                .withIdentifier(1)
                                .withSelectedColor(Color.WHITE)
                                .withSelectedTextColor(ContextCompat.getColor(this, R.color.colorAccent))
                                .withSelectedIcon(R.drawable.ic_list_movies_accent)
                                .withTextColor(ContextCompat.getColor(this, R.color.primary_dark)),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(R.string.read_about_star)
                                .withIcon(R.drawable.ic_find_new_star)
                                .withIdentifier(2)
                                .withSelectedColor(Color.WHITE)
                                .withSelectedTextColor(ContextCompat.getColor(this, R.color.colorAccent))
                                .withSelectedIcon(R.drawable.ic_find_new_star_accent)
                                .withTextColor(ContextCompat.getColor(this, R.color.primary_dark)))
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    switch ((int) drawerItem.getIdentifier()) {
                        case 0:
                            replaceFragmentClearBackStack(DetailsFragment_.builder().build());
                            break;
                        case 1:
                            replaceFragmentClearBackStack(CreatedListsFragment_.builder().build());
                            break;
                        case 2:
                            replaceFragmentClearBackStack(SearchStarFragment_.builder().build());
                            break;
                        case 3:
                            replaceFragmentClearBackStack(SearchStarFragment_.builder().build());
                            break;
                    }
                    return false;
                })
                .withOnDrawerNavigationListener(view -> {
                            if (!result.getActionBarDrawerToggle().isDrawerIndicatorEnabled()) {
                                onBackPressed();
                                return true;
                            } else
                                return false;
                        }
                )
                .build();
    }

    private void enableViews(boolean enable) {
        // To keep states of ActionBar and ActionBarDrawerToggle synchronized,
        // when you enable on one, you disable on the other.
        // And as you may notice, the order for this operation is disable first, then enable - VERY VERY IMPORTANT.
        if (enable) {
            // Remove hamburger
            result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(false);
            // Show updateStatus button
            toolbarManager.showHomeAsUp(true);
        } else {
            // Remove updateStatus button
            toolbarManager.showHomeAsUp(false);
            // Show hamburger
            result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        if (result.isDrawerOpen()) {
            result.closeDrawer();
        } else
            super.onBackPressed();

    }


    @Override
    protected int getContainerId() {
        return R.id.container;
    }
}
