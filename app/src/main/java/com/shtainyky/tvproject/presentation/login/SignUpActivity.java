package com.shtainyky.tvproject.presentation.login;

import android.support.v7.widget.Toolbar;

import com.shtainyky.tvproject.R;
import com.shtainyky.tvproject.presentation.base.BaseActivity;
import com.shtainyky.tvproject.presentation.base.BaseFragment;
import com.shtainyky.tvproject.utils.AnalyticManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;


/**
 * Created by Bell on 23.05.2017.
 */

@EActivity(R.layout.activity_container)
public class SignUpActivity extends BaseActivity {


    @AfterViews
    protected void initUI() {
     replaceFragment(LoginFragment_.builder().build());
    }

    @Override
    protected int getContainerId() {
        return R.id.container;
    }

    @Override
    protected Toolbar getToolbar() {
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        BaseFragment fragment = (BaseFragment) getSupportFragmentManager().findFragmentById(getContainerId());
        AnalyticManager.trackScreenOpen(this, fragment.getScreenName());
    }
}
