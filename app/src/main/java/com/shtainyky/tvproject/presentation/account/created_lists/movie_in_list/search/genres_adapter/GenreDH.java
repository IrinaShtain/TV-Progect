package com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.search.genres_adapter;

import com.shtainyky.tvproject.data.models.movie.GenreItem;

/**
 * Created by Irina Shtain on 01.12.2017.
 */

public class GenreDH {
    private final GenreItem model;

    public GenreDH(GenreItem model) {
        this.model = model;
    }

    public GenreItem getModel() {
        return model;
    }

    public String getGenreTitle() {
        return model.name == null ? "No title" : model.name.trim();
    }

    public int getGenreID() {
        return model.id;
    }
}
