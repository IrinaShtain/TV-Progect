package com.shtainyky.tvproject.presentation.account.find_star;

import android.util.Log;

import com.shtainyky.tvproject.data.models.star.StarItem;
import com.shtainyky.tvproject.presentation.account.find_star.adapters.StarDH;
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
    private int total_pages;
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
    public void unsubscribe() {
       сompositeDisposable.clear();
    }


    @Override
    public void onSearchClick() {
        view.getInputText();
    }

    @Override
    public void makeSearch(String movieTitle) {
        Log.e("myLog", "makeSearch " + movieTitle);
        this.name = movieTitle;
        current_page = 1;
        loadPage(current_page);
    }

    private void loadPage(int pageNumber) {
        сompositeDisposable.add(model.getStars(name, pageNumber)
                .subscribe(response -> {
                    current_page = pageNumber;
                    total_pages = response.total_pages;
                    if (current_page == 1)
                        view.setList(prepareList(response.stars));
                    else
                        view.addList(prepareList(response.stars));

                }, throwable -> {
                    Log.e("myLog", "throwable makeSearch" + throwable.getMessage());
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
        if (current_page < total_pages)
            loadPage(current_page + 1);
    }


}
