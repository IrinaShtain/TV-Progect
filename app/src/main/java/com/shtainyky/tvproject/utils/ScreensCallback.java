package com.shtainyky.tvproject.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import java.util.List;

/**
 * Created by Irina Shtain on 28.11.2017.
 */

public class ScreensCallback implements Application.ActivityLifecycleCallbacks {

    private Context context;
    private Activity lastActivity;

    private int resumed;
    private int stopped;

    public ScreensCallback(Context context) {
        this.context = context;
    }

    public Activity getLastActivity() {
        return lastActivity;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        lastActivity = activity;
    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityResumed(Activity activity) {
        ++resumed;
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
        ++stopped;
        if(resumed == stopped)
            AnalyticManager.trackCustomEvent(Constants
                .AnalyticCustomEvent.APP_GOES_BACKGROUND);
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }


}
