package com.shtainyky.tvproject.presentation.account.find_star.adapters;

import com.shtainyky.tvproject.data.models.star.FamousForItem;
import com.shtainyky.tvproject.data.models.star.StarItem;

import java.util.ArrayList;

/**
 * Created by Bell on 02.06.2017.
 */

public class StarDH {
    private final StarItem model;

    public StarDH(StarItem model) {
        this.model = model;
    }

    public StarItem getModel() {
        return model;
    }

    public String getName() {
        return model.name == null ? "No name" : model.name.trim();
    }

    public int getModelID() {
        return model.id;
    }


    public String getInfo() {
        return "Popularity: " + model.popularity;
    }

    public String getPosterPath() {
        return model.avatarUrl;
    }

    public ArrayList<FamousForItem> getFamousFor() {
        return model.known_for;
    }


}
