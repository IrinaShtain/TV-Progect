package com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shtainyky.tvproject.R;
import com.shtainyky.tvproject.presentation.listeners.OnCardClickListener;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Irina Shtain on 17.11.2017.
 */
@EBean
public class MovieItemAdapter extends RecyclerView.Adapter<MovieItemVH> {
    private List<MovieItemDH> items;
    private OnCardClickListener mListener;

    public MovieItemAdapter() {
        items = new ArrayList<>();
    }

    public void setListDH(List<MovieItemDH> listsDHs) {
        items = listsDHs;
        notifyDataSetChanged();
    }

    public void addListDH(List<MovieItemDH> listsDHs) {
        items.addAll(listsDHs);
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        items.remove(position);
        notifyItemChanged(position);
    }

    public void setListener(OnCardClickListener listener) {
        mListener = listener;
    }

    @Override
    public MovieItemVH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new MovieItemVH(view);

    }

    @Override
    public void onBindViewHolder(MovieItemVH holder, int position) {
        if (mListener != null) {
            holder.itemView.setOnClickListener(v -> mListener.onCardClick(items.get(position).getMovieID(), position));
        }
        holder.bindData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }
}
