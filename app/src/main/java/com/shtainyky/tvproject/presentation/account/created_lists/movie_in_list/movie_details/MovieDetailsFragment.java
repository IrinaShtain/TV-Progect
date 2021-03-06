package com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.movie_details;

import android.app.Activity;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.shtainyky.tvproject.R;
import com.shtainyky.tvproject.data.models.movie.MovieItem;
import com.shtainyky.tvproject.domain.MovieRepository;
import com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.movie_details.rating_dialog.RatingDialogFragment;
import com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.movie_details.rating_dialog.RatingDialogFragment_;
import com.shtainyky.tvproject.presentation.base.BasePresenter;
import com.shtainyky.tvproject.presentation.base.content.ContentFragment;
import com.shtainyky.tvproject.utils.Constants;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;
import org.androidannotations.annotations.res.StringRes;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by Bell on 21.06.2017.
 */
@EFragment
public class MovieDetailsFragment extends ContentFragment implements MovieDetailsContract.MovieDetailsView {

    @ViewById
    TextView tv_description;
    @ViewById
    ImageView imageView;
    @ViewById
    TextView tvTitle;
    @ViewById
    TextView tv_genre;
    @ViewById
    TextView tv_releaseDate;
    @ViewById
    TextView tv_popularity;
    @ViewById
    Button bt_add_online;
    @ViewById
    FloatingActionButton fabRating;
    @ViewById
    protected CollapsingToolbarLayout collapsingToolbar;
    @ViewById
    protected Toolbar toolbar;
    @ViewById
    protected AppBarLayout appBarLayout;

    @FragmentArg
    protected int movieID;
    @FragmentArg
    protected int listID;
    @FragmentArg
    protected ArrayList<MovieItem> moviesInList;

    private MovieDetailsContract.MovieDetailsPresenter mPresenter;
    private RatingDialogFragment dialogRating;
    @Bean
    protected MovieRepository mMovieRepository;

    @ColorRes(R.color.colorPrimary)
    protected int toolbarColor;
    @StringRes(R.string.btn_delete_from_list)
    protected String deleteFromList;
    @StringRes(R.string.btn_add_to_list)
    protected String addToList;

    @AfterInject
    @Override
    public void initPresenter() {
        new MovieDetailsPresenter(this, mMovieRepository, movieID, moviesInList);
    }

    @Override
    public void setPresenter(MovieDetailsContract.MovieDetailsPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_movie_details;
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @AfterViews
    protected void initUI() {
        toolbar.setNavigationOnClickListener(v -> mActivity.onBackPressed());
        RxView.clicks(bt_add_online)
                .throttleFirst(Constants.CLICK_DELAY, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> mPresenter.buttonMovieActionClicked(listID));
        RxView.clicks(fabRating)
                .throttleFirst(Constants.CLICK_DELAY, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> mPresenter.fabClicked());
        setupCollapsingToolbar();
        mPresenter.subscribe();
    }

    private void setupCollapsingToolbar() {
        mActivity.getToolbarManager().displayToolbar(false);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            int scrollRange = -1;
            int collapseOffset = -1;

            @Override
            public void onOffsetChanged(final AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                    collapseOffset = appBarLayout.getHeight() / 3;
                }
                if (scrollRange + verticalOffset <= collapseOffset) {
                    toolbar.setBackgroundColor(toolbarColor);
                } else {
                    toolbar.setBackgroundResource(R.drawable.bg_title_black_gradient);
                    toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
                }
            }
        });
    }

    @Override
    public void setupUI(MovieItem movieItem) {
        collapsingToolbar.setTitle(movieItem.title);
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);
        tv_description.setText(movieItem.overview);
        tvTitle.setText(movieItem.title);
        tv_genre.setText(getResources().getString(R.string.genre, movieItem.genresString));
        tv_releaseDate.setText(movieItem.release_date);
        tv_popularity.setText(String.valueOf(movieItem.vote_average));
        Picasso.with(getContext())
                .load(movieItem.avatarUrl)
                .error(R.drawable.placeholder_movie)
                .into(imageView);

    }

    @Override
    public void showRatingDialog() {
        dialogRating = RatingDialogFragment_.builder()
                .movieID(movieID)
                .build();
        dialogRating.setTargetFragment(this, Constants.REQUEST_CODE_RATE_MOVIE);
        dialogRating.show(mActivity.getSupportFragmentManager(), "rate_ movie");
    }

    @Override
    public void setupButton(boolean isMovieAdded) {
        if (isMovieAdded)
            bt_add_online.setText(deleteFromList);
        else
            bt_add_online.setText(addToList);
    }

    @Override
    public void showPlaceholder(Constants.PlaceholderType placeholderType) {
        super.showPlaceholder(placeholderType);
        mActivity.getToolbarManager().displayToolbar(true);
    }

    @Override
    public void showProgressMain() {
        super.showProgressMain();
        mActivity.getToolbarManager().displayToolbar(true);
    }

    @Override
    public void hideProgress() {
        super.hideProgress();
        mActivity.getToolbarManager().displayToolbar(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mActivity.getToolbarManager().displayToolbar(true);
        if (dialogRating != null && dialogRating.isVisible())
            dialogRating.dismiss();
    }

    @OnActivityResult(Constants.REQUEST_CODE_RATE_MOVIE)
    public void onDialogFragmentResult(int resultCode,
                                       @OnActivityResult.Extra(value = Constants.KEY_ERROR_CODE) int errorCode) {
        if (resultCode == Activity.RESULT_OK) {
            mPresenter.showResult(errorCode);
        }
    }

    @Override
    public String getScreenName() {
        return "Movie Details Fragment";
    }
}
