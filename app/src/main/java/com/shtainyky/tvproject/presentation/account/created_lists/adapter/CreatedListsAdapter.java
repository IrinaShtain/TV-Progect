package com.shtainyky.tvproject.presentation.account.created_lists.adapter;

import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.shtainyky.tvproject.R;
import com.shtainyky.tvproject.presentation.account.created_lists.adapter.CreatedListsDH;
import com.shtainyky.tvproject.presentation.account.created_lists.adapter.CreatedListsVH;
import com.shtainyky.tvproject.presentation.listeners.OnCardClickListener;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bell on 26.05.2017.
 */

@EBean
public class CreatedListsAdapter extends RecyclerView.Adapter<CreatedListsVH> {
    private List<CreatedListsDH> items;
    private OnCardClickListener mListener;

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

    public void deleteItem(int position) {
        items.remove(position);
        notifyDataSetChanged();
    }

    public CreatedListsDH getItem(int position){
        return items.get(position);
    }


    public void setListener(OnCardClickListener listener) {
        mListener = listener;
    }

    @Override
    public CreatedListsVH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list, parent, false);
        return new CreatedListsVH(view);

    }
    @Override
    public void onBindViewHolder(CreatedListsVH holder, int position) {
        if (mListener != null) {
            holder.itemView.setOnClickListener(v -> {
                mListener.onCardClick(items.get(position).getListsID(), position);
            });
        }
        holder.bindData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
