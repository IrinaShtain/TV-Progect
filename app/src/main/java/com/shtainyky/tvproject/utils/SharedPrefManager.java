package com.shtainyky.tvproject.utils;

import org.androidannotations.annotations.sharedpreferences.DefaultBoolean;
import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by Bell on 24.05.2017.
 */

@SharedPref(value = SharedPref.Scope.UNIQUE)
public interface SharedPrefManager {

    @DefaultString("")
    String getAccessToken();

    @DefaultString("")
    String getSessionID();

    String getUserProfile();




}