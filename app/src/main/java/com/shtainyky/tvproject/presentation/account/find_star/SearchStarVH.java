package com.shtainyky.tvproject.presentation.account.find_star;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.shtainyky.tvproject.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Bell on 02.06.2017.
 */

public class SearchStarVH extends RecyclerView.ViewHolder {
    private final TextView tvTitle;
    private final TextView tv_info;
    private final ImageView imageView;
    public final Spinner spinner;



    private Context mContext;

    public SearchStarVH(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        tv_info = (TextView) itemView.findViewById(R.id.tv_info);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);

        spinner = (Spinner) itemView.findViewById(R.id.spinner);
    }

    public void bindData(StarDH starDH) {
        tvTitle.setText(mContext.getResources().getString(R.string.name, starDH.getName()));
        tv_info.setText(starDH.getInfo());
        Picasso.with(mContext)
                .load(starDH.getPosterPath())
                .error(R.drawable.ic_user)
                .into(imageView);




    }
}