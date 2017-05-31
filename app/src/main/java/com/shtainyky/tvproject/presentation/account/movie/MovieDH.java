package com.shtainyky.tvproject.presentation.account.movie;

import com.shtainyky.tvproject.data.models.movie.MovieItem;

/**
 * Created by Bell on 29.05.2017.
 */

public class MovieDH {
    private final MovieItem model;

    public MovieDH(MovieItem model) {
        this.model = model;
    }

    public MovieItem getModel() {
        return model;
    }

    public String getMovieTitle() {
        return model.title == null ? "No title" : model.title.trim();
    }

    public int getMovieID() {
        return model.id;
    }

    public String getMovieDescription() {
        return model.overview.equals("") ? "No description" : model.overview.trim();
    }

    public String getReleaseDate() {
        return model.release_date == null ? "Unknown date" : model.release_date.trim();
    }

    public String getPosterPath() {
        return model.avatarUrl;
    }

    public float getRate() {
        return model.vote_average;
    }

    public String getGenres() {
        return model.genres;
    }
}
