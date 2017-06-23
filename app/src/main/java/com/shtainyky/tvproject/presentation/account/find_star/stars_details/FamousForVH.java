package com.shtainyky.tvproject.presentation.account.find_star.stars_details;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shtainyky.tvproject.R;
import com.shtainyky.tvproject.presentation.account.find_star.StarDH;
import com.squareup.picasso.Picasso;

/**
 * Created by Bell on 21.06.2017.
 */

public class FamousForVH extends RecyclerView.ViewHolder {
    private final ImageView icon;
    private final TextView names;
    private final TextView info;
    private final TextView release;
    private Context mContext;

    public FamousForVH(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        icon = (ImageView) itemView.findViewById(R.id.imageView);
        names = (TextView) itemView.findViewById(R.id.tvTitle);
        info = (TextView) itemView.findViewById(R.id.tv_info);
        release = (TextView) itemView.findViewById(R.id.tv_release);
    }

    public void bindData(FamousForDH famousForDH) {
        names.setText(mContext.getResources().getString(R.string.title, famousForDH.getTitle()));
        info.setText(famousForDH.getDesc());
        release.setText(famousForDH.getReleaseDate());
        Picasso.with(mContext)
                .load(famousForDH.getPosterPath())
                .error(R.drawable.ic_user)
                .into(icon);




    }
}
