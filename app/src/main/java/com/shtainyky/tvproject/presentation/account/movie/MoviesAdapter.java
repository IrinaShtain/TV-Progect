package com.shtainyky.tvproject.presentation.account.movie;

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
 * Created by Bell on 29.05.2017.
 */
@EBean
public class MoviesAdapter extends RecyclerView.Adapter<MovieVH> {
    private List<MovieDH> items;
    private OnCardClickListener mListener;

    public MoviesAdapter() {
        items = new ArrayList<>();
    }

    public void setListDH(List<MovieDH> listsDHs) {
        items = listsDHs;
        notifyDataSetChanged();
    }

    public void deleteItem(int position){
        items.remove(position);
        notifyItemChanged(position);
    }

    public void setListener(OnCardClickListener listener) {
        mListener = listener;
    }

    @Override
    public MovieVH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_movie, parent, false);
        return new MovieVH(view);

    }

    @Override
    public void onBindViewHolder(MovieVH holder, int position) {
        if (mListener != null) {
            holder.itemView.setOnClickListener(v -> mListener.onCardClick(items.get(position).getMovieID(), position));
        }
        holder.bindData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
