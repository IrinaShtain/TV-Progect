package com.shtainyky.tvproject.presentation.account;

/**
 * Created by Bell on 25.05.2017.
 */


import android.graphics.Color;
import android.support.v7.widget.Toolbar;

import com.shtainyky.tvproject.R;
import com.shtainyky.tvproject.presentation.account.details_account.DetailsFragment_;
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

    @AfterViews
    protected void initUI() {
        setSupportActionBar(toolbar);
        toolbar.setTitle("My Account");
        toolbar.setTitleTextColor(Color.WHITE);
        replaceFragment(DetailsFragment_.builder().build());
    }


    @Override
    protected int getContainerId() {
        return R.id.container;
    }
}
