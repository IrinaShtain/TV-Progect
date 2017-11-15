package com.shtainyky.tvproject.presentation.account.movie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shtainyky.tvproject.R;
import com.shtainyky.tvproject.presentation.account.movie.adapter.MovieDH;
import com.squareup.picasso.Picasso;

/**
 * Created by Bell on 29.05.2017.
 */

public class MovieVH extends RecyclerView.ViewHolder {
    private final TextView tvTitle;
    private final TextView tv_description;
    private final TextView tv_genre;
    private final TextView tv_releaseDate;
    private final TextView tv_popularity;
    private final ImageView imageView;


    private Context mContext;

    public MovieVH(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        tv_description = (TextView) itemView.findViewById(R.id.tv_description);
        tv_genre = (TextView) itemView.findViewById(R.id.tv_genre);
        tv_releaseDate = (TextView) itemView.findViewById(R.id.tv_releaseDate);
        tv_popularity = (TextView) itemView.findViewById(R.id.tv_popularity);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);
    }

    public void bindData(MovieDH listsDH) {
        tvTitle.setText(mContext.getResources().getString(R.string.title, listsDH.getMovieTitle()));
        tv_description.setText(mContext.getResources().getString(R.string.description, listsDH.getMovieDescription()));
        tv_genre.setText(mContext.getResources().getString(R.string.genre, listsDH.getGenres()));
        tv_releaseDate.setText(mContext.getResources().getString(R.string.releaseDate, listsDH.getReleaseDate()));
        tv_popularity.setText(mContext.getResources().getString(R.string.popularity, String.valueOf(listsDH.getRate()).trim()));
        Picasso.with(mContext)
                .load(listsDH.getPosterPath())
                .error(R.drawable.ic_user)
                .into(imageView);


    }
}