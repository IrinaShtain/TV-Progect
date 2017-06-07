package com.shtainyky.tvproject.domain;

import com.shtainyky.tvproject.data.Rest;
import com.shtainyky.tvproject.data.models.movie.SearchMovieResponse;
import com.shtainyky.tvproject.data.models.star.StarResponse;
import com.shtainyky.tvproject.data.services.MovieService;
import com.shtainyky.tvproject.presentation.account.find_star.SearchStarContract;
import com.shtainyky.tvproject.presentation.base.NetworkRepository;
import com.shtainyky.tvproject.utils.Constants;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import rx.Observable;

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
        return getNetworkObservable(mMovieService.searchStar(Constants.KEY_API, name, page));
    }
}
