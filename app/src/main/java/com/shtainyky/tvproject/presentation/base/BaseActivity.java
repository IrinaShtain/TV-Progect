package com.shtainyky.tvproject.presentation.base;

import android.os.Build;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;


import com.shtainyky.tvproject.utils.ToolbarManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;

import java.util.Collections;
import java.util.List;

/**
 * Created by Bell on 23.05.2017.
 */

@EActivity
public abstract class BaseActivity extends AppCompatActivity {

    @IdRes
    protected abstract int getContainerId();
    @Bean
    protected ToolbarManager toolbarManager;

    protected abstract Toolbar getToolbar();

    @AfterViews
    public void initToolbar() {
        toolbarManager.init(this, getToolbar());

        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            if(getSupportFragmentManager().getBackStackEntryCount() > 1) {
                toolbarManager.showHomeButton(true);
            }
        });
    }
    public void replaceFragment(BaseFragment fragment) {
        replaceFragment(fragment, Collections.emptyList());
    }

    public void replaceFragment(BaseFragment fragment, List<View> sharedViews) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            for (View view : sharedViews) {
                transaction.addSharedElement(view, view.getTransitionName());
            }
        }

        transaction.replace(getContainerId(), fragment)
                .addToBackStack(null)
                .commit();
    }

    public void replaceFragmentClearBackStack(BaseFragment fragment) {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        replaceFragment(fragment);
    }

    public ToolbarManager getToolbarManager() {
        return toolbarManager;
    }
}
