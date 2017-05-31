package com.shtainyky.tvproject.presentation.account.created_lists.create_list;


import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.shtainyky.tvproject.R;
import com.shtainyky.tvproject.domain.AccountRepository;
import com.shtainyky.tvproject.presentation.base.BaseFragment;
import com.shtainyky.tvproject.utils.Constants;
import com.shtainyky.tvproject.utils.SignedUserManager;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.concurrent.TimeUnit;

/**
 * Created by Bell on 30.05.2017.
 */
@EFragment(R.layout.fragment_create_new_list)
public class CreateNewListFragment extends BaseFragment implements CreateNewListContract.CreateNewListView {

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

    private CreateNewListContract.CreateNewListPresenter mPresenter;

    @Bean
    protected SignedUserManager userManager;
    @Bean
    protected AccountRepository mAccountRepository;

    @AfterInject
    @Override
    public void initPresenter() {
        new CreateNewListPresenter(this, userManager, mAccountRepository);
    }

    @Override
    public void setPresenter(CreateNewListContract.CreateNewListPresenter presenter) {
        this.mPresenter = presenter;
    }

    @AfterViews
    protected void initUI() {
        RxView.clicks(bt_addList)
                .throttleFirst(Constants.CLICK_DELAY, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> mPresenter.onButtonAddClick());
        mPresenter.subscribe();
    }

    @Override
    public void validate() {
        hideKeyboard();
        String title = titleWrapper.getEditText().getText().toString();
        String description = descriptionWrapper.getEditText().getText().toString();
        if (title.isEmpty())
            titleWrapper.setError(getString(R.string.error_empty_title));
        else {
            titleWrapper.setErrorEnabled(false);
            descriptionWrapper.setErrorEnabled(false);
            mPresenter.createNewList(title, description);

        }

    }

    private void hideKeyboard() {
        View view = mActivity.getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void clearInput() {
        text_title.setText("");
        text_description.setText("");
    }

    @Override
    public void showMessage() {
        Toast.makeText(getContext(), "New list is created", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }
}
