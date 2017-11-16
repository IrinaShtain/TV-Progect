package com.shtainyky.tvproject.utils;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.shtainyky.tvproject.R;

/**
 * Created by Bell on 23.05.2017.
 */

public class Constants {
    public static final int CLICK_DELAY = 600;
    public static final long TIMEOUT = 30; //seconds
    public static final long TIMEOUT_READ = 30; //seconds
    public static final long TIMEOUT_WRITE = 60; //seconds

    public static final String BASE_URL = "https://api.themoviedb.org";

    /*----------------- API ------------------*/
    public static final String IMAGE_BASE = "https://image.tmdb.org/t/p/w500";
    public static final String GET_USER_TOKEN = "/3/authentication/token/new";
    public static final String GET_VALIDATED_USER_TOKEN = "/3/authentication/token/validate_with_login";
    public static final String GET_USER_SESSION_ID = "3/authentication/session/new";
    public static final String GET_USER_ACCOUNT = "/3/account";

    public enum MessageType {
        CONNECTION_PROBLEMS(R.string.err_msg_connection_problem, true),
        USER_NOT_REGISTERED(R.string.err_msg_user_not_registered, true),
        UNKNOWN(R.string.err_msg_something_goes_wrong, true);

        @StringRes
        private int messageRes;
        private boolean isDangerous;

        MessageType(@StringRes int messageRes, boolean isDangerous) {
            this.messageRes = messageRes;
            this.isDangerous = isDangerous;
        }

        public int getMessageRes() {
            return messageRes;
        }

        public boolean isDangerous() {
            return isDangerous;
        }
    }

    public enum PlaceholderType {
        NETWORK(R.string.err_msg_connection_problem, R.drawable.ic_cloud_off),
        UNKNOWN(R.string.err_msg_something_goes_wrong, R.drawable.ic_sentiment_dissatisfied),
        EMPTY(R.string.err_msg_no_data, R.drawable.ic_no_data);

        @StringRes
        private int messageRes;
        @DrawableRes
        private int iconRes;

        PlaceholderType(@StringRes int messageRes, @DrawableRes int iconRes) {
            this.messageRes = messageRes;
            this.iconRes = iconRes;
        }

        public int getMessageRes() {
            return messageRes;
        }

        public int getIconRes() {
            return iconRes;
        }
    }


}
