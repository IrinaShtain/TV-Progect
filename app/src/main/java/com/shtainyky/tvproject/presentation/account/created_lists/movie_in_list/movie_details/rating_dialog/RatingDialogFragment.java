package com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.movie_details.rating_dialog;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.shtainyky.tvproject.R;
import com.shtainyky.tvproject.domain.MovieRepository;
import com.shtainyky.tvproject.utils.Constants;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.concurrent.TimeUnit;


/**
 * Created by Irina Shtain on 29.11.2017.
 */
@EFragment(R.layout.fragment_dialog_rating)
public class RatingDialogFragment extends DialogFragment implements RatingDialogContract.RatingDialogView {

    @ViewById
    protected Button btnRate;
    @ViewById
    protected Button btnCancel;
    @ViewById
    RatingBar rbMovieRating;
    @ViewById
    TextView tvTitle;
    @ViewById
    TextView tvError;
    @ViewById
    ProgressBar pbPagination;

    private RatingDialogContract.RatingDialogPresenter presenter;
    @Bean
    protected MovieRepository movieRepository;

    @FragmentArg
    protected int movieID;

    @AfterInject
    @Override
    public void initPresenter() {
        presenter = new RatingDialogPresenter(this, movieRepository, movieID);
    }

    @Override
    public void setPresenter(RatingDialogContract.RatingDialogPresenter presenter) {
        this.presenter = presenter;
    }

    @AfterViews
    public void init() {
        RxView.clicks(btnRate)
                .throttleFirst(Constants.CLICK_DELAY, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> presenter.sendRating(rbMovieRating.getRating()));

        RxView.clicks(btnCancel)
                .throttleFirst(Constants.CLICK_DELAY, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> dismiss());
        tvTitle.setText(getResources().getString(R.string.title_rating, ""));
        rbMovieRating.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            tvTitle.setText(getResources().getString(R.string.title_rating, String.valueOf(rating)));
            hideRatingError();
        });
    }


    @Override
    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        super.onResume();
    }

    @Override
    public void updateTargetFragment(int resultCode) {
        Intent intent = new Intent();
        intent.putExtra(Constants.KEY_ERROR_CODE, resultCode);
        getTargetFragment().onActivityResult(
                getTargetRequestCode(), Activity.RESULT_OK, intent);
        dismiss();
    }

    @Override
    public void showProgress() {
        pbPagination.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pbPagination.setVisibility(View.GONE);
    }

    @Override
    public void showRatingError() {
        tvError.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRatingError() {
        tvError.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.unsubscribe();
    }
}
