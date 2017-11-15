package com.shtainyky.tvproject.utils;

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

}
