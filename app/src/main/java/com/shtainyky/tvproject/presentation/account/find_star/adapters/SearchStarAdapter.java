package com.shtainyky.tvproject.presentation.account.find_star.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shtainyky.tvproject.R;
import com.shtainyky.tvproject.presentation.listeners.StarListener;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bell on 02.06.2017.
 */
@EBean
public class SearchStarAdapter extends RecyclerView.Adapter<SearchStarVH> {
    private List<StarDH> items;
    private StarListener mListener;

    public SearchStarAdapter() {
        items = new ArrayList<>();
    }

    public void setListDH(List<StarDH> listsDHs) {
        items = listsDHs;
        notifyDataSetChanged();
    }
    public void addListDH(List<StarDH> listsDHs) {
        items.addAll(listsDHs);
        notifyDataSetChanged();
    }

    public void setListener(StarListener listener) {
        mListener = listener;
    }

    @Override
    public SearchStarVH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new SearchStarVH(view);

    }

    @Override
    public void onBindViewHolder(SearchStarVH holder, int position) {
        if (mListener != null) {
            holder.itemView.setOnClickListener(v -> mListener.onStarClick(items.get(position).getModel()));
        }
        holder.bindData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
