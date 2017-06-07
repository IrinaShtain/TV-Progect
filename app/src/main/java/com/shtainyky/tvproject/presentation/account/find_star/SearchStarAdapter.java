package com.shtainyky.tvproject.presentation.account.find_star;

import android.content.Context;
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
 * Created by Bell on 02.06.2017.
 */
@EBean
public class SearchStarAdapter extends RecyclerView.Adapter<SearchStarVH> {
    private List<StarDH> items;
    private OnCardClickListener mListener;
    private final CustomSpinnerAdapter mAdapter;

    public SearchStarAdapter(Context context) {
        items = new ArrayList<>();
        mAdapter = new CustomSpinnerAdapter(context);
    }

    public void setListDH(List<StarDH> listsDHs) {
        items = listsDHs;
        notifyDataSetChanged();
    }
    public void addListDH(List<StarDH> listsDHs) {
        items.addAll(listsDHs);
        notifyDataSetChanged();
    }

    public void setListener(OnCardClickListener listener) {
        mListener = listener;
    }

    @Override
    public SearchStarVH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_star, parent, false);
        return new SearchStarVH(view);

    }

    @Override
    public void onBindViewHolder(SearchStarVH holder, int position) {
        if (mListener != null) {
            holder.itemView.setOnClickListener(v -> mListener.onCardClick(items.get(position).getModelID(), position));
        }
        holder.bindData(items.get(position));
        mAdapter.setFamousForItems(items.get(position).getFamousFor());
        holder.spinner.setAdapter(mAdapter);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
