package com.shtainyky.tvproject.domain;

import com.shtainyky.tvproject.data.Rest;
import com.shtainyky.tvproject.data.models.account.User;
import com.shtainyky.tvproject.data.services.AccountService;
import com.shtainyky.tvproject.presentation.account.details_account.DetailsContract;
import com.shtainyky.tvproject.presentation.base.NetworkRepository;
import com.shtainyky.tvproject.utils.Constants;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import rx.Observable;

/**
 * Created by Bell on 25.05.2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class AccountRepository extends NetworkRepository implements DetailsContract.DetailsModel {

    @Bean
    protected Rest rest;

    private AccountService mAccountService;

    @AfterInject
    protected void initServices() {
        mAccountService = rest.getAccountService();
    }


    @Override
    public Observable<User> getUserDetails(String sessionID) {
        return getNetworkObservable(mAccountService.getDetails(Constants.KEY_API, sessionID));
    }
}
