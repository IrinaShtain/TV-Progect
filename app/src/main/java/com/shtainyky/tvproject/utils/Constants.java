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

    /*----------------- REQUEST CODE ------------------*/
    public static final int REQUEST_CODE_CREATE_NEW_LIST = 100;
    public static final int REQUEST_CODE_RATE_MOVIE = 101;

    /*----------------- KEY CODE ------------------*/
    public static final String KEY_TITLE = "list_title";
    public static final String KEY_DESCRIPTION = "list_desc";
    public static final String KEY_ERROR_CODE = "error_code";

    /*----------------- ERROR CODE ------------------*/
    public static final int ERROR_CODE_CONNECTION_LOST = -1;
    public static final int ERROR_CODE_UNKNOWN = -2;

    public enum MessageType {
        CONNECTION_PROBLEMS(R.string.err_msg_connection_problem, true),
        USER_NOT_REGISTERED(R.string.err_msg_user_not_registered, true),
        LIST_WAS_DELETED(R.string.err_msg_list_was_deleted, true),
        NEW_LIST_CREATED_SUCCESSFULLY(R.string.msg_new_list_created_successfully, false),
        NEW_MOVIE_ADDED_SUCCESSFULLY(R.string.msg_new_movie_added_successfully, false),
        MOVIE_RATED_SUCCESSFULLY(R.string.msg_movie_rated_successfully, false),
        NEW_MOVIE_REMOVED_SUCCESSFULLY(R.string.msg_new_movie_removed_successfully, true),
        INPUT_MOVIE_TITLE(R.string.error_msg_input_movie_title, true),
        INPUT_STAR_NAME(R.string.error_msg_star_name, true),
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

    public enum AnalyticClick {
        LOGIN("click_login", "AnalyticClick: LOGIN Button"),
        SEARCH_A_MOVIE("click_search_movie", "AnalyticClick: Search a movie"),
        SEARCH_A_STAR("click_search_read_about_star", "AnalyticClick: Search read about star");

        private String name;
        private String description;

        AnalyticClick(String name, String description) {
            this.name = name;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum AnalyticCustomEvent {
        ADDED_NEW_MOVIE("event_added_new_movie", "Event: Added new movie"),
        DELETED_MOVIE("event_deleted_movie", "Event: Deleted new movie"),
        ADDED_NEW_LIST("event_added_new_list", "Event: Added new list"),
        ADDED_MOVIE_RATING("event_added_movie_rating", "Event: Added movie rating"),
        DELETED_LIST("event_deleted_list", "Event: Deleted new list"),
        APP_GOES_BACKGROUND("app_goes_background", "Event: Application goes Background");

        private String name;
        private String description;

        AnalyticCustomEvent(String name, String description) {
            this.name = name;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }
    }


}
