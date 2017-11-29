package com.shtainyky.tvproject.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Created by Irina Shtain on 28.11.2017.
 */

public class AnalyticManager {
    private static FirebaseAnalytics sFirebaseAnalytics;

    public static void init(@NonNull Context context) {
        sFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
    }

    public static void trackScreenOpen(@NonNull Activity activity, @Nullable String screenName) {
        if(sFirebaseAnalytics != null && screenName != null) {
            sFirebaseAnalytics.setCurrentScreen(activity, screenName, activity.getClass().getSimpleName());
        }
    }

    public static void trackClick(Constants.AnalyticClick analyticClick) {
        if(sFirebaseAnalytics != null) {
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, analyticClick.getDescription());
            sFirebaseAnalytics.logEvent(analyticClick.getName(), bundle);
        }
    }

    public static void trackCustomEvent(Constants.AnalyticCustomEvent analyticCustomEvent) {
        if(sFirebaseAnalytics != null) {
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, analyticCustomEvent.getDescription());
            sFirebaseAnalytics.logEvent(analyticCustomEvent.getName(), bundle);
        }
    }

    public static void trackLogin() {
        if(sFirebaseAnalytics != null) {
            sFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, null);
        }
    }
}
