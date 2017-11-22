package com.shtainyky.tvproject.utils;

import android.content.res.Resources;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import com.shtainyky.tvproject.presentation.account.AccountActivity;
import com.shtainyky.tvproject.presentation.base.BaseActivity;

import org.androidannotations.annotations.EBean;

/**
 * Created by Irina Shtain on 20.11.2017.
 */

@EBean
public class ToolbarManager {

    private ActionBar actionBar;
    private Toolbar toolbar;
    private View.OnClickListener mNavigationClickListener;

    /**
     * Should be called after UI initialized
     */
    public void init(BaseActivity activity, Toolbar toolbar) {
        this.toolbar = toolbar;
        activity.setSupportActionBar(toolbar);
        actionBar = activity.getSupportActionBar();

        mNavigationClickListener = v -> {
            activity.onBackPressed();
            Toast.makeText(activity, "Click", Toast.LENGTH_SHORT).show();};

        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    public void showHomeButton(boolean show) {
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(show);
        }
    }

    public void showHomeAsUp(boolean isShow) {
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(isShow);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    public View.OnClickListener getNavigationClickListener(boolean need) {
        return need ? mNavigationClickListener : null;
    }

    public void setTitle(@StringRes int title) {
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    public void setTitle(CharSequence title) {
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    public void displayToolbar(boolean isShown) {
        toolbar.setVisibility(isShown ? View.VISIBLE : View.GONE);
    }

}
