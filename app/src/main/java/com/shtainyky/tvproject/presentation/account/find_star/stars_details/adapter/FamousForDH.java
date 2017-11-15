package com.shtainyky.tvproject.presentation.account.find_star.stars_details.adapter;

import com.shtainyky.tvproject.data.models.star.FamousForItem;
import com.shtainyky.tvproject.data.models.star.StarItem;

import java.util.ArrayList;

/**
 * Created by Bell on 21.06.2017.
 */

public class FamousForDH {
    private final FamousForItem model;

    public FamousForDH(FamousForItem model) {
        this.model = model;
    }

    public FamousForItem getModel() {
        return model;
    }

    public String getTitle() {
        return model.media_type.equals("tv") ? model.tv_name.trim() : model.movie_title.trim();
    }


    public String getMediaType() {
        return model.media_type;
    }


    public String getReleaseDate() {
        return model.media_type.equals("tv") ? model.first_air_date : model.release_date;
    }

    public String getPosterPath() {
        return model.avatarUrl;
    }

    public String getDesc() {
        return model.overview;
    }
}
