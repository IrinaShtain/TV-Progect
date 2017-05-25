package com.shtainyky.tvproject.utils;

import com.bluelinelabs.logansquare.LoganSquare;
import com.shtainyky.tvproject.data.models.account.User;


import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.io.IOException;

/**
 * Created by Bell on 24.05.2017.
 */

@EBean(scope = EBean.Scope.Singleton)
public class SignedUserManager {

    @Pref
    protected SharedPrefManager_ userStorePrefs;

        private User mCurrentUser;

    private void storeUser() {
        userStorePrefs
                .edit()
                .getUserProfile()
                .put(mCurrentUser == null ? null : serializeUser())
                .apply();
    }

    public User getCurrentUser() {
        return mCurrentUser == null ? mCurrentUser = parseUser() :  mCurrentUser.clone();
    }

    private User parseUser() {
        User user = null;
        try {
            user = LoganSquare.parse(userStorePrefs.getUserProfile().get(), User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    private String serializeUser() {
        String user = null;
        try {
            user = LoganSquare.serialize(mCurrentUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void updateUser(final User user) {
        mCurrentUser = user;
        storeUser();
    }

    public void clearUser() {
        userStorePrefs
                .edit()
                .getUserProfile()
                .remove()
                .apply();

        userStorePrefs
                .edit()
                .getAccessToken()
                .remove()
                .apply();
    }

    public void saveAuthToken(String token) {
        userStorePrefs
                .edit()
                .getAccessToken()
                .put(token)
                .apply();
    }

    public String getAuthToken() {
        return userStorePrefs.getAccessToken().get();
    }

    public void saveSessionID(String sessionID) {
        userStorePrefs
                .edit()
                .getSessionID()
                .put(sessionID)
                .apply();
    }

    public String getSessionId() {
        return userStorePrefs.getSessionID().get();
    }


}
