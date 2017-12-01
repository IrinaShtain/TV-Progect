package com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.search.genres_adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.shtainyky.tvproject.R;

/**
 * Created by Irina Shtain on 01.12.2017.
 */

public class GenreVH extends RecyclerView.ViewHolder {
    private final TextView tvGenreName;


    public GenreVH(View itemView) {
        super(itemView);
        tvGenreName = (TextView) itemView.findViewById(R.id.tvGenreName);
    }

    public void bindData(GenreDH listsDH) {
        tvGenreName.setText(listsDH.getGenreTitle());
    }
}