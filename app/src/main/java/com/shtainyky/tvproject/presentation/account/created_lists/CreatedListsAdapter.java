package com.shtainyky.tvproject.presentation.account.created_lists;

import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.shtainyky.tvproject.R;
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


    public void setListener(OnCardClickListener listener) {
        mListener = listener;
    }

    @Override
    public CreatedListsVH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list, parent, false);
        return new CreatedListsVH(view);

    }

    private void smth(View view) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.inflate(R.menu.popupmenu);
        popupMenu.setGravity(Gravity.FILL_HORIZONTAL);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu1:
                        Toast.makeText(view.getContext(),
                                "Вы выбрали PopupMenu 1",
                                Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.menu2:
                        Toast.makeText(view.getContext(),
                                "Вы выбрали PopupMenu 2",
                                Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();
    }


    @Override
    public void onBindViewHolder(CreatedListsVH holder, int position) {
        if (mListener != null) {
            holder.itemView.setOnClickListener(v -> {
             //   smth(holder.itemView);
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
