package com.shtainyky.tvproject.presentation.account.created_lists.movie_in_list.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shtainyky.tvproject.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Irina Shtain on 17.11.2017.
 */

public class MovieItemVH extends RecyclerView.ViewHolder {
    private final TextView tvTitle;
    private final ImageView ivImage;


    public MovieItemVH(View itemView) {
        super(itemView);
        tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
    }

    public void bindData(MovieItemDH listsDH) {
        Picasso.with(itemView.getContext())
                .load(listsDH.getPosterPath())
                .error(R.drawable.placeholder_movie)
                .placeholder(R.drawable.placeholder_movie)
                .into(ivImage);
        tvTitle.setText(listsDH.getMovieTitle());
      }
}