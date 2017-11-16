package com.shtainyky.tvproject.presentation.account.movie.movie_details;

import android.support.design.widget.Snackbar;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.shtainyky.tvproject.R;
import com.shtainyky.tvproject.data.models.movie.MovieItem;
import com.shtainyky.tvproject.domain.MovieRepository;
import com.shtainyky.tvproject.presentation.base.BaseFragment;
import com.shtainyky.tvproject.utils.Constants;
import com.shtainyky.tvproject.utils.SignedUserManager;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.concurrent.TimeUnit;

/**
 * Created by Bell on 21.06.2017.
 */
@EFragment(R.layout.fragment_movie_details)
public class MovieDetailsFragment extends BaseFragment implements MovieDetailsContract.MovieDetailsView {

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
    Button bt_close;
    @ViewById
    Button bt_add_offline;

    @FragmentArg
    protected MovieItem movieItem;
    @FragmentArg
    protected int listID;
    private MovieDetailsContract.MovieDetailsPresenter mPresenter;
    @Bean
    protected MovieRepository mMovieRepository;

    @Bean
    protected SignedUserManager userManager;

    @AfterInject
    @Override
    public void initPresenter() {
        new MovieDetailsPresenter(this, mMovieRepository, userManager);
    }

    @Override
    public void setPresenter(MovieDetailsContract.MovieDetailsPresenter presenter) {
        mPresenter = presenter;
    }

    @AfterViews
    protected void initUI() {
        mPresenter.subscribe();
        RxView.clicks(bt_add_online)
                .throttleFirst(Constants.CLICK_DELAY, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> mPresenter.addOnlineClicked(listID, movieItem.id));
        RxView.clicks(bt_close)
                .throttleFirst(Constants.CLICK_DELAY, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> mPresenter.closeClicked());
        RxView.clicks(bt_add_offline)
                .throttleFirst(Constants.CLICK_DELAY, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> mPresenter.addOfflineClicked());
        setupUI();

    }

    private void setupUI() {
        tv_description.setText(getResources().getString(R.string.description, movieItem.overview));
        tvTitle.setText(getResources().getString(R.string.title, movieItem.title));
        tv_genre.setText(getResources().getString(R.string.genre, movieItem.genres));
        tv_releaseDate.setText(getResources().getString(R.string.releaseDate, movieItem.release_date));
        tv_popularity.setText(getResources().getString(R.string.popularity, String.valueOf(movieItem.vote_count)));
        Picasso.with(getContext())
                .load(movieItem.avatarUrl)
                .error(R.drawable.ic_user)
                .into(imageView);
    }

    @Override
    public void close() {
        mActivity.onBackPressed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }

    //    @Override
//    public void onCardClick(int movieID, int position) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setMessage(R.string.question_about_adding);
//        builder.setPositiveButton(R.string.answer_yes, (dialog, which) -> {
//            dialog.cancel();
//            mPresenter.addMovie(movieID, listID);
//        });
//        builder.setNegativeButton(R.string.answer_cancel, null);
//
//        builder.show();
//    }
}
