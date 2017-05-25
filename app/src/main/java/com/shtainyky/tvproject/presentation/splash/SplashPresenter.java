package com.shtainyky.tvproject.presentation.splash;

import com.shtainyky.tvproject.utils.SignedUserManager;

import org.androidannotations.annotations.Bean;

/**
 * Created by Bell on 23.05.2017.
 */

public class SplashPresenter implements SplashContract.SplashPresenter {

    private SplashContract.SplashView view;

    private SignedUserManager userManager;


    public SplashPresenter(SplashContract.SplashView view, SignedUserManager userManager) {
        this.view = view;
        this.userManager = userManager;

        view.setPresenter(this);
    }


    @Override
    public void subscribe() {
        view.runSplashAnimation();
    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void startNextScreen() {
        if (userManager.getSessionId().isEmpty())
            view.startLoginScreen();
        else
            view.startHomeScreen();
    }
}
