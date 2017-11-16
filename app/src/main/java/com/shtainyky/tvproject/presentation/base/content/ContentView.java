package com.shtainyky.tvproject.presentation.base.content;

import com.shtainyky.tvproject.utils.Constants;

/**
 * Created by Irina Shtain on 15.11.2017.
 */

public interface ContentView {
    void showProgressMain();
    void showProgressPagination();
    void hideProgress();
    void showMessage(Constants.MessageType messageType);
    void showCustomMessage(String msg, boolean isDangerous);
    void showPlaceholder(Constants.PlaceholderType placeholderType);
}
