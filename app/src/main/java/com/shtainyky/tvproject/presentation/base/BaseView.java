package com.shtainyky.tvproject.presentation.base;

import com.shtainyky.tvproject.presentation.account.created_lists.CreatedListsDH;

import java.util.ArrayList;

/**
 * Created by Bell on 23.05.2017.
 */

public interface BaseView<T extends BasePresenter> {
    void initPresenter();
    void setPresenter(T presenter);
}
