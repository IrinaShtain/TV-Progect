package com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.search.genres_adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shtainyky.tvproject.R;
import com.shtainyky.tvproject.presentation.listeners.OnGenreClickListener;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Irina Shtain on 01.12.2017.
 */
@EBean
public class GenreAdapter extends RecyclerView.Adapter<GenreVH> {
    private List<GenreDH> items;
    private OnGenreClickListener mListener;

    public GenreAdapter() {
        items = new ArrayList<>();
    }

    public void setListDH(List<GenreDH> listsDHs) {
        items = listsDHs;
        notifyDataSetChanged();
    }

    public void setListener(OnGenreClickListener listener) {
        mListener = listener;
    }

    @Override
    public GenreVH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_genre, parent, false);
        return new GenreVH(view);

    }

    @Override
    public void onBindViewHolder(GenreVH holder, int position) {
        if (mListener != null) {
            holder.itemView.setOnClickListener(v -> mListener.onGenreClick(items.get(position).getGenreID(), position));
        }
        holder.bindData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }
}
