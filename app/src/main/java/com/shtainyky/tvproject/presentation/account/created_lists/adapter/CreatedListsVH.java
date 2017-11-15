package com.shtainyky.tvproject.presentation.account.created_lists.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.shtainyky.tvproject.R;
import com.shtainyky.tvproject.presentation.account.created_lists.adapter.CreatedListsDH;

/**
 * Created by Bell on 26.05.2017.
 */

public class CreatedListsVH extends RecyclerView.ViewHolder {
    private final TextView tv_name;
    private final TextView tv_desc;
    private final TextView tv_type;

    private Context mContext;

    public CreatedListsVH(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        tv_name = (TextView) itemView.findViewById(R.id.tvName);
        tv_desc = (TextView) itemView.findViewById(R.id.tv_description);
        tv_type = (TextView) itemView.findViewById(R.id.tv_type);
    }

    public void bindData(CreatedListsDH listsDH) {
        tv_name.setText(mContext.getResources().getString(R.string.name, listsDH.getListsName()));
        tv_desc.setText(mContext.getResources().getString(R.string.description, listsDH.getListsDescription()));
        tv_type.setText(mContext.getResources().getString(R.string.type, listsDH.getListsType()));

    }

}
