package com.shtainyky.tvproject.presentation.account.find_star.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.shtainyky.tvproject.R;
import com.shtainyky.tvproject.data.models.star.FamousForItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Bell on 02.06.2017.
 */

public class CustomSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {
    private ArrayList<FamousForItem> mFamousForItems;
    private Context mContext;
    private LayoutInflater inflater;

    public CustomSpinnerAdapter(Context context) {
        mFamousForItems = new ArrayList<>();
        mContext = context;
        inflater = (LayoutInflater.from(mContext));
    }

    public void setFamousForItems(ArrayList<FamousForItem> famousForItems) {
        mFamousForItems.clear();
        mFamousForItems.addAll(famousForItems);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mFamousForItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mFamousForItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.item_seached_movie, null);
        ImageView icon = (ImageView) convertView.findViewById(R.id.imageView);
        TextView names = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView label = (TextView) convertView.findViewById(R.id.label);
        TextView info = (TextView) convertView.findViewById(R.id.tv_info);
        FamousForItem item = mFamousForItems.get(position);
        if (position == 0) {
            label.setText("We have such list");
        } else {
            label.setVisibility(View.INVISIBLE);
            if (item.media_type.equals("tv")) {
                names.setText(mContext.getResources().getString(R.string.name, item.tv_name));
                info.setText(mContext.getResources().getString(R.string.releaseDate, item.first_air_date));
            } else {
                names.setText(mContext.getResources().getString(R.string.name, item.movie_title));
                info.setText(mContext.getResources().getString(R.string.releaseDate, item.release_date));
            }
            Picasso.with(mContext)
                    .load(item.avatarUrl)
                    .error(R.drawable.ic_user)
                    .into(icon);
        }

        return convertView;
    }
}
