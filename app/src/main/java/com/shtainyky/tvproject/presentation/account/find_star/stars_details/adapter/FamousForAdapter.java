package com.shtainyky.tvproject.presentation.account.find_star.stars_details.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shtainyky.tvproject.R;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bell on 23.06.2017.
 */
@EBean
public class FamousForAdapter extends RecyclerView.Adapter<FamousForVH> {
    private List<FamousForDH> items;

    public FamousForAdapter() {
        items = new ArrayList<>();
    }

    public void setListDH(List<FamousForDH> listsDHs) {
        items = listsDHs;
        notifyDataSetChanged();
    }


    @Override
    public FamousForVH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_famous_for, parent, false);
        return new FamousForVH(view);

    }

    @Override
    public void onBindViewHolder(FamousForVH holder, int position) {
        holder.bindData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
