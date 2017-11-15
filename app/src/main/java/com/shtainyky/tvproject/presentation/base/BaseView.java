package com.shtainyky.tvproject.presentation.base;

/**
 * Created by Bell on 23.05.2017.
 */

public interface BaseView<T extends BasePresenter> {
    void initPresenter();
    void setPresenter(T presenter);
}
