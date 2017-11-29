package com.shtainyky.tvproject.domain;

import com.shtainyky.tvproject.data.Rest;
import com.shtainyky.tvproject.data.models.account.CreatedListsData;
import com.shtainyky.tvproject.data.models.account.User;
import com.shtainyky.tvproject.data.models.request_body.NewListRequest;
import com.shtainyky.tvproject.data.models.response.ResponseMessage;
import com.shtainyky.tvproject.data.services.AccountService;
import com.shtainyky.tvproject.data.services.MovieService;
import com.shtainyky.tvproject.presentation.account.created_lists.CreatedListsContract;
import com.shtainyky.tvproject.presentation.account.created_lists.create_list.CreateNewListContract;
import com.shtainyky.tvproject.presentation.account.details_account.DetailsContract;
import com.shtainyky.tvproject.presentation.base.NetworkRepository;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import io.reactivex.Observable;


/**
 * Created by Bell on 25.05.2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class AccountRepository extends NetworkRepository implements DetailsContract.DetailsModel,
        CreatedListsContract.CreatedListsModel, CreateNewListContract.CreateNewListModel {

    @Bean
    protected Rest rest;

    private AccountService mAccountService;
    private MovieService mMovieService;

    @AfterInject
    protected void initServices() {
        mAccountService = rest.getAccountService();
        mMovieService = rest.getMovieService();
    }


    @Override
    public Observable<User> getUserDetails(String sessionID) {
        return getNetworkObservable(mAccountService.getDetails(sessionID));
    }


    @Override
    public Observable<CreatedListsData> getLists(int userID, int page) {
        return getNetworkObservable(mAccountService.getLists(userID, page));
    }


    @Override
    public Observable<ResponseMessage> createList(String listTitle, String listDesc) {
        return getNetworkObservable(mAccountService.createList(new NewListRequest(listTitle, listDesc)));
    }

    @Override
    public Observable<ResponseMessage> deleteList(int listID) {
        return getNetworkObservable(mMovieService.deleteList(listID));
    }
}
