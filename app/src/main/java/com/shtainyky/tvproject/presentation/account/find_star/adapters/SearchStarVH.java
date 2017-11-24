package com.shtainyky.tvproject.presentation.account.find_star.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shtainyky.tvproject.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Bell on 02.06.2017.
 */

public class SearchStarVH extends RecyclerView.ViewHolder {
    private final TextView tvTitle;
    private final ImageView ivImage;



    private Context mContext;

    public SearchStarVH(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        ivImage = (ImageView) itemView.findViewById(R.id.ivImage);

    }

    public void bindData(StarDH starDH) {
        tvTitle.setText(starDH.getName());
        Picasso.with(mContext)
                .load(starDH.getPosterPath())
                .error(R.drawable.placehoder_star)
                .placeholder(R.drawable.placehoder_star)
                .into(ivImage);




    }
}