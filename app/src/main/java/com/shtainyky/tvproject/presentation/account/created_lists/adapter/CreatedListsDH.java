package com.shtainyky.tvproject.presentation.account.created_lists.adapter;

import com.shtainyky.tvproject.data.models.account.ListItem;

/**
 * Created by Bell on 26.05.2017.
 */

public class CreatedListsDH {
    private final ListItem model;

    public CreatedListsDH(ListItem model) {
        this.model = model;
    }

    public ListItem getModel() {
        return model;
    }

    public String getListsName() {
        return model.name == null ? "No name" : model.name;
    }

    public int getListsID() {
        return model.id;
    }

    public String getListsDescription() {
        return model.description.equals("") ? "No description" : model.description;
    }

    public String getListsType() {
        return model.list_type == null ? "No type" : model.list_type;
    }
}
