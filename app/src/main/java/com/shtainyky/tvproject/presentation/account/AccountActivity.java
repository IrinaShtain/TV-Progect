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
import com.shtainyky.tvproject.presentation.account.details_account.DetailsFragment;
import com.shtainyky.tvproject.presentation.account.details_account.DetailsFragment_;
import com.shtainyky.tvproject.presentation.account.find_star.SearchStarFragment_;
import com.shtainyky.tvproject.presentation.base.BaseActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Bell on 25.05.2017.
 */
@EActivity(R.layout.activity_container_with_toolbar)
public class AccountActivity extends BaseActivity {

    @ViewById
    Toolbar toolbar;
    private Drawer result;

    @AfterViews
    protected void initUI() {
        setSupportActionBar(toolbar);
        toolbar.setTitle("My Account");
        toolbar.setTitleTextColor(Color.WHITE);
        setupNavigationDrawerMenu();
        replaceFragment(DetailsFragment_.builder().build());
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
                        new PrimaryDrawerItem().withName("My account")
                                .withIcon(R.drawable.ic_account)
                                .withIdentifier(0)
                                .withSelectedColor(Color.WHITE)
                                .withSelectedTextColor(ContextCompat.getColor(this, R.color.colorAccent))
                                .withTextColor(ContextCompat.getColor(this, R.color.primary_dark)),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(R.string.my_lists)
                                .withIcon(R.drawable.ic_lists)
                                .withIdentifier(1)
                                .withSelectedColor(Color.WHITE)
                                .withSelectedTextColor(ContextCompat.getColor(this, R.color.colorAccent))
                                .withTextColor(ContextCompat.getColor(this, R.color.primary_dark)),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(R.string.read_about_star)
                                .withIcon(R.drawable.ic_find_star)
                                .withIdentifier(2)
                                .withSelectedColor(Color.WHITE)
                                .withSelectedTextColor(ContextCompat.getColor(this, R.color.colorAccent))
                                .withTextColor(ContextCompat.getColor(this, R.color.primary_dark)),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName("Offline lists")
                                .withIcon(R.drawable.ic_off_lists)
                                .withIdentifier(3)
                                .withSelectedColor(Color.WHITE)
                                .withSelectedTextColor(ContextCompat.getColor(this, R.color.colorAccent))
                                .withTextColor(ContextCompat.getColor(this, R.color.primary_dark)))
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    // do something with the clicked item :D
                    switch ((int) drawerItem.getIdentifier()) {
                        case 0:
                            replaceFragment(DetailsFragment_.builder().build());
                            break;
                        case 1:
                            replaceFragment(CreatedListsFragment_.builder().build());
                            break;
                        case 2:
                            replaceFragment(SearchStarFragment_.builder().build());
                            break;
                        case 3:
                            replaceFragment(SearchStarFragment_.builder().build());
                            break;
                    }
                    return false;
                })
                .build();
    }

    @Override
    public void onBackPressed() {
        // Закрываем Navigation Drawer по нажатию системной кнопки "Назад" если он открыт
        if (result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    protected int getContainerId() {
        return R.id.container;
    }
}
