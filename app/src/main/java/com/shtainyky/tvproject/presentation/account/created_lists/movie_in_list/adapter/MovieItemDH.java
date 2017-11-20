package com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.adapter;

import com.shtainyky.tvproject.data.models.movie.MovieItem;

/**
 * Created by Irina Shtain on 17.11.2017.
 */

public class MovieItemDH {
    private final MovieItem model;

    public MovieItemDH(MovieItem model) {
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

    public String getPosterPath() {
        return model.avatarUrl;
    }
}
