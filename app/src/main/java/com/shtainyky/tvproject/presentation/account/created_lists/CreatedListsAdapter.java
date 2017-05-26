package com.shtainyky.tvproject.presentation.account.created_lists;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shtainyky.tvproject.R;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bell on 26.05.2017.
 */

@EBean
public class CreatedListsAdapter extends RecyclerView.Adapter<CreatedListsVH> {
    private List<CreatedListsDH> items;

    public CreatedListsAdapter() {
        items = new ArrayList<>();
    }

    public void setListDH(List<CreatedListsDH> listsDHs) {
        items = listsDHs;
        notifyDataSetChanged();
    }

    public void addListDH(List<CreatedListsDH> listsDHs) {
        items.addAll(listsDHs);
        notifyDataSetChanged();
    }


    @Override
    public CreatedListsVH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list, parent, false);
        return new CreatedListsVH(view);

    }

    @Override
    public void onBindViewHolder(CreatedListsVH holder, int position) {
        holder.bindData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
