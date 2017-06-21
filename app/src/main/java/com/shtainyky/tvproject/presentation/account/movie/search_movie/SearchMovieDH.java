package com.shtainyky.tvproject.presentation.account.movie.search_movie;

import com.shtainyky.tvproject.data.models.movie.MovieItem;

/**
 * Created by Bell on 30.05.2017.
 */

public class SearchMovieDH {
    private final MovieItem model;

    public SearchMovieDH(MovieItem model) {
        this.model = model;
    }

    public MovieItem getModel() {
        return model;
    }

    public String getMovieTitle() {
        return model.title == null ? "No title" : model.title.trim();
    }

    public int getModelID() {
        return model.id;
    }
    public String getGenre() {
        return model.genres;
    }


    public String getInfo() {
        String info = "";
        if (model.original_title != null)
             info = model.original_title.trim() + ", " + model.release_date.trim();
        else
            info = model.release_date.trim();
        return info;
    }

    public String getPosterPath() {
        return model.avatarUrl;
    }


}
