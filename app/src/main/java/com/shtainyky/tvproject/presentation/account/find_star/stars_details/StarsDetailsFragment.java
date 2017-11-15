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
@EFragment(R.layout.fragment_star_details)
public class StarsDetailsFragment extends BaseFragment implements StarsDetailsContract.StarsDetailsView {

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
    @AfterInject
    @Override
    public void initPresenter() {

    }

    @Override
    public void setPresenter(StarsDetailsContract.StarsDetailsPresenter presenter) {

    }

    @AfterViews
    protected void initUI() {
        tvName.setText(getResources().getString(R.string.name, starItem.name));
        tv_popularity.setText(getResources().getString(R.string.popularity, String.valueOf(starItem.popularity)));
        Picasso.with(getContext())
                .load(starItem.avatarUrl)
                .error(R.drawable.ic_user)
                .into(imageView);
        Log.e("myLog", "starItem.known_for.size() " + starItem.known_for.size());
        setupRecyclerView();

    }
    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvListFamousFor.setLayoutManager(layoutManager);
        rvListFamousFor.setAdapter(listAdapter);
        List<FamousForItem> list = starItem.known_for;
        List<FamousForDH> famousForDHs = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            famousForDHs.add(new FamousForDH(list.get(i)));
        }
        listAdapter.setListDH(famousForDHs);
    }
}
