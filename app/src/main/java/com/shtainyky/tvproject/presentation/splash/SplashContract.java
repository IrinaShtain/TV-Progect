package com.shtainyky.tvproject.presentation.splash;

import com.shtainyky.tvproject.presentation.base.BasePresenter;
import com.shtainyky.tvproject.presentation.base.BaseView;

/**
 * Created by Bell on 23.05.2017.
 */

public interface SplashContract {

    interface SplashView extends BaseView<SplashPresenter> {
        void runSplashAnimation();

        void startHomeScreen();

        void startLoginScreen();
    }
    interface SplashPresenter extends BasePresenter {
        void startNextScreen();
    }
}
