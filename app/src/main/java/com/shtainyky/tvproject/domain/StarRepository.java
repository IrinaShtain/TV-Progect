package com.shtainyky.tvproject.domain;

import com.shtainyky.tvproject.data.Rest;
import com.shtainyky.tvproject.data.models.star.StarResponse;
import com.shtainyky.tvproject.data.services.MovieService;
import com.shtainyky.tvproject.presentation.account.find_star.SearchStarContract;
import com.shtainyky.tvproject.presentation.base.NetworkRepository;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import io.reactivex.Observable;


/**
 * Created by Bell on 02.06.2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class StarRepository extends NetworkRepository implements SearchStarContract.SearchStarModel {
    @Bean
    protected Rest rest;

    private MovieService mMovieService;
    @AfterInject
    protected void initServices() {
        mMovieService = rest.getMovieService();
    }

    @Override
    public Observable<StarResponse> getStars(String name, int page) {
        return getNetworkObservable(mMovieService.searchStar(name, page));
    }
}
