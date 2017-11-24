package com.shtainyky.tvproject.presentation.account.find_star;

import android.util.Log;

import com.shtainyky.tvproject.data.exceptions.ConnectionException;
import com.shtainyky.tvproject.data.models.star.StarItem;
import com.shtainyky.tvproject.presentation.account.find_star.adapters.StarDH;
import com.shtainyky.tvproject.utils.Constants;
import com.shtainyky.tvproject.utils.SignedUserManager;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;


/**
 * Created by Bell on 02.06.2017.
 */

public class SearchStarPresenter implements SearchStarContract.SearchStarPresenter {
    private SearchStarContract.SearchStarView view;
    private CompositeDisposable сompositeDisposable;
    private SearchStarContract.SearchStarModel model;
    private int current_page;
    private int total_pages = Integer.MAX_VALUE;
    private String name;
    protected SignedUserManager userManager;

    public SearchStarPresenter(SearchStarContract.SearchStarView view,
                               SearchStarContract.SearchStarModel model,
                               SignedUserManager userManager) {
        this.view = view;
        this.model = model;
        this.userManager = userManager;
        view.setPresenter(this);
        сompositeDisposable = new CompositeDisposable();
    }


    @Override
    public void subscribe() {

    }

    @Override
    public void onRefresh() {
        current_page = 1;
        loadPage(current_page);
    }

    @Override
    public void unsubscribe() {
        сompositeDisposable.clear();
    }


    @Override
    public void onSearchClick(String starName) {
        if (!starName.isEmpty()) {
            this.name = starName;
            current_page = 1;
            view.showProgressMain();
            loadPage(current_page);
        } else view.showMessage(Constants.MessageType.INPUT_STAR_NAME);
    }

    private void loadPage(int pageNumber) {
        сompositeDisposable.add(model.getStars(name, pageNumber)
                .subscribe(response -> {
                    current_page = pageNumber;
                    total_pages = response.total_pages;
                    view.hideProgress();
                    if (current_page == 1)
                        if (!response.stars.isEmpty())
                            view.setList(prepareList(response.stars));
                        else
                            view.showPlaceholder(Constants.PlaceholderType.EMPTY);
                    else
                        view.addList(prepareList(response.stars));

                }, throwable -> {
                    view.hideProgress();
                    if (total_pages == Integer.MAX_VALUE)
                        if (throwable instanceof ConnectionException) {
                            view.showMessage(Constants.MessageType.CONNECTION_PROBLEMS);
                        } else {
                            view.showMessage(Constants.MessageType.UNKNOWN);
                        }
                    else if (throwable instanceof ConnectionException) {
                        view.showPlaceholder(Constants.PlaceholderType.NETWORK);
                    } else {
                        view.showPlaceholder(Constants.PlaceholderType.UNKNOWN);
                    }
                }));
    }


    private ArrayList<StarDH> prepareList(ArrayList<StarItem> items) {
        ArrayList<StarDH> list = new ArrayList<>();
        for (StarItem item : items) {
            list.add(new StarDH(item));
        }
        return list;
    }


    @Override
    public void getNextPage() {
        Log.e("myLog", "current_page" + current_page);
        if (current_page < total_pages) {
            view.showProgressPagination();
            loadPage(current_page + 1);
        }
    }


}
