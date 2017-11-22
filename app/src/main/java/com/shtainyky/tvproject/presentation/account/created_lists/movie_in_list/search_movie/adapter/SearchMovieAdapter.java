package com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.search_movie.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shtainyky.tvproject.R;
import com.shtainyky.tvproject.presentation.listeners.MovieListener;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bell on 30.05.2017.
 */
@EBean
public class SearchMovieAdapter extends RecyclerView.Adapter<SearchMovieVH> {
    private List<SearchMovieDH> items;
    private MovieListener mListener;

    public SearchMovieAdapter() {
        items = new ArrayList<>();
    }

    public void setListDH(List<SearchMovieDH> listsDHs) {
        items = listsDHs;
        notifyDataSetChanged();
    }
    public void addListDH(List<SearchMovieDH> listsDHs) {
        items.addAll(listsDHs);
        notifyDataSetChanged();
    }

    public void setListener(MovieListener listener) {
        mListener = listener;
    }

    @Override
    public SearchMovieVH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_seached_movie, parent, false);
        return new SearchMovieVH(view);

    }

    @Override
    public void onBindViewHolder(SearchMovieVH holder, int position) {
        if (mListener != null) {
            holder.itemView.setOnClickListener(v -> mListener.onMovieClick(items.get(position).getModel()));
        }
        holder.bindData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
