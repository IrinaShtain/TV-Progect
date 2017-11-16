package com.shtainyky.tvproject.presentation.account.created_lists.create_list;


import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.shtainyky.tvproject.R;
import com.shtainyky.tvproject.domain.AccountRepository;
import com.shtainyky.tvproject.utils.Constants;
import com.shtainyky.tvproject.utils.SignedUserManager;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.ViewById;

import java.util.concurrent.TimeUnit;

/**
 * Created by Bell on 30.05.2017.
 */
@EFragment(R.layout.fragment_create_new_list)
public class CreateNewListDialog extends DialogFragment implements CreateNewListContract.CreateNewListView {

    @ViewById
    TextInputEditText text_title;
    @ViewById
    TextInputEditText text_description;
    @ViewById
    Button bt_addList;

    @ViewById(R.id.til_description_container)
    TextInputLayout descriptionWrapper;
    @ViewById(R.id.til_title_container)
    TextInputLayout titleWrapper;
    @ViewById
    ProgressBar pbPagination;

    private CreateNewListContract.CreateNewListPresenter mPresenter;
    @SystemService
    protected InputMethodManager inputMethodManager;

    @Bean
    protected SignedUserManager userManager;
    @Bean
    protected AccountRepository repository;

    @AfterInject
    @Override
    public void initPresenter() {
        new CreateNewListPresenter(this, userManager, repository);
    }

    @Override
    public void setPresenter(CreateNewListContract.CreateNewListPresenter presenter) {
        this.mPresenter = presenter;
    }

    @AfterViews
    protected void initUI() {
        RxView.clicks(bt_addList)
                .throttleFirst(Constants.CLICK_DELAY, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> mPresenter.createNewList(titleWrapper.getEditText().getText().toString(), descriptionWrapper.getEditText().getText().toString()));
        mPresenter.subscribe();
    }

    @Override
    public void clearInput() {
        text_title.setText("");
        text_description.setText("");
    }

    @Override
    public void updateTargetFragment(int resultCode, String title, String description) {
        Intent intent = new Intent();
        intent.putExtra(Constants.KEY_TITLE, title);
        intent.putExtra(Constants.KEY_DESCRIPTION, description);
        intent.putExtra(Constants.KEY_ERROR_CODE, resultCode);
        getTargetFragment().onActivityResult(
                getTargetRequestCode(), Activity.RESULT_OK, intent);
        dismiss();
    }

    @Override
    public void showTitleError() {
        titleWrapper.setError(getString(R.string.error_empty_title));
    }

    @Override
    public void hideError() {
        titleWrapper.setErrorEnabled(false);
        descriptionWrapper.setErrorEnabled(false);
    }

    @Override
    public void showProgress() {
        pbPagination.setVisibility(View.VISIBLE);
        hideKeyboard();
    }

    @Override
    public void hideProgress() {
        pbPagination.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    protected void hideKeyboard() {
        if(getView() != null) {
            inputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }
    }
}
