package com.shtainyky.tvproject.domain;

import android.webkit.ClientCertRequest;

import com.shtainyky.tvproject.data.Rest;
import com.shtainyky.tvproject.data.models.account.CreatedListsData;
import com.shtainyky.tvproject.data.models.account.User;
import com.shtainyky.tvproject.data.models.request_body.NewListRequest;
import com.shtainyky.tvproject.data.models.response.ResponseMessage;
import com.shtainyky.tvproject.data.services.AccountService;
import com.shtainyky.tvproject.presentation.account.created_lists.CreatedListsContract;
import com.shtainyky.tvproject.presentation.account.created_lists.create_list.CreateNewListContract;
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
public class AccountRepository extends NetworkRepository implements DetailsContract.DetailsModel,
        CreatedListsContract.CreatedListsModel, CreateNewListContract.CreateNewListModel {

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


    @Override
    public Observable<CreatedListsData> getLists(int userID, String sessionID, int page) {
        return getNetworkObservable(mAccountService.getLists(userID, Constants.KEY_API, sessionID, page));
    }

    @Override
    public Observable<ResponseMessage> deleteList(int listID, String sessionID) {
        return getNetworkObservable(mAccountService.deleteList(listID, Constants.KEY_API, sessionID));
    }

    @Override
    public Observable<ResponseMessage> createList(String sessionID, String listTitle, String listDesc) {
        return getNetworkObservable(mAccountService.createList(Constants.KEY_API, sessionID, new NewListRequest(listTitle, listDesc)));
    }
}
