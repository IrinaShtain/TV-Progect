package com.shtainyky.tvproject.presentation.account.find_star;

import com.shtainyky.tvproject.data.models.movie.MovieItem;

/**
 * Created by Bell on 02.06.2017.
 */

public class FamousForMovieDH {  private final MovieItem model;

    public FamousForMovieDH(MovieItem model) {
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
