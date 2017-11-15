package com.shtainyky.tvproject.presentation.account.movie.search_movie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shtainyky.tvproject.R;
import com.shtainyky.tvproject.presentation.account.movie.search_movie.adapter.SearchMovieDH;
import com.squareup.picasso.Picasso;

/**
 * Created by Bell on 30.05.2017.
 */

public class SearchMovieVH extends RecyclerView.ViewHolder {
    private final TextView tvTitle;
    private final TextView tv_info;
    private final ImageView imageView;


    private Context mContext;

    public SearchMovieVH(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        tv_info = (TextView) itemView.findViewById(R.id.tv_info);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);
    }

    public void bindData(SearchMovieDH movieDH) {
        tvTitle.setText(mContext.getResources().getString(R.string.title, movieDH.getMovieTitle()));
        tv_info.setText(movieDH.getInfo());

        Picasso.with(mContext)
                .load(movieDH.getPosterPath())
                .error(R.drawable.ic_user)
                .into(imageView);


    }
}