package com.shtainyky.tvproject.presentation.account.find_star.stars_details;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.shtainyky.tvproject.R;
import com.shtainyky.tvproject.data.models.star.FamousForItem;
import com.shtainyky.tvproject.data.models.star.StarItem;
import com.shtainyky.tvproject.presentation.account.find_star.stars_details.adapter.FamousForAdapter;
import com.shtainyky.tvproject.presentation.account.find_star.stars_details.adapter.FamousForDH;
import com.shtainyky.tvproject.presentation.base.BaseFragment;
import com.shtainyky.tvproject.presentation.base.BasePresenter;
import com.shtainyky.tvproject.presentation.base.content.ContentFragment;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bell on 21.06.2017.
 */
@EFragment
public class StarsDetailsFragment extends ContentFragment implements StarsDetailsContract.StarsDetailsView {

    @ViewById
    TextView tvName;
    @ViewById
    ImageView imageView;
    @ViewById
    TextView tv_popularity;
    @ViewById
    RecyclerView rvListFamousFor;

    @FragmentArg
    protected StarItem starItem;
    @Bean
    protected FamousForAdapter listAdapter;
    private StarsDetailsContract.StarsDetailsPresenter presenter;
    @AfterInject
    @Override
    public void initPresenter() {
        new StarsDetailsPresenter(this, starItem);
    }

    @Override
    public void setPresenter(StarsDetailsContract.StarsDetailsPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_star_details;
    }

    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }

    @AfterViews
    protected void initUI() {
        setupRecyclerView();
        presenter.subscribe();
    }

    @Override
    public void setStarDetail(StarItem starItem) {
        tvName.setText(getResources().getString(R.string.name, starItem.name));
        tv_popularity.setText(getResources().getString(R.string.popularity, String.valueOf(starItem.popularity)));
        Picasso.with(getContext())
                .load(starItem.avatarUrl)
                .error(R.drawable.placehoder_star)
                .into(imageView);
    }

    @Override
    public void setFamousForDH(List<FamousForDH> famousForDHs) {
        listAdapter.setListDH(famousForDHs);
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvListFamousFor.setLayoutManager(layoutManager);
        rvListFamousFor.setAdapter(listAdapter);
    }
}
