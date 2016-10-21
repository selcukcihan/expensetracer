package com.selcukcihan.android.expensetracer.ui;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.selcukcihan.android.expensetracer.R;

/**
 * Created by SELCUKCI on 21.10.2016.
 */

public class CategoryIconAdapter extends BaseAdapter {
    private Context mContext;

    // Constructor
    public CategoryIconAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mIconResources.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        CategoryIconGridViewItem imageView;

        if (convertView == null) {
            imageView = new CategoryIconGridViewItem(mContext);
            imageView.setOnClickListener(new View.OnClickListener() {
                private ImageView mSelection;
                public void onClick(View v) {
                    ImageView view = (ImageView)v;
                    if (mSelection == null) {
                        mSelection = view;
                        view.setColorFilter(ContextCompat.getColor(mContext, R.color.colorAccent), PorterDuff.Mode.MULTIPLY);
                    } else {
                        if (mSelection != view) {
                            mSelection.setColorFilter(ContextCompat.getColor(mContext, R.color.colorPrimaryDark), PorterDuff.Mode.MULTIPLY);
                            view.setColorFilter(ContextCompat.getColor(mContext, R.color.colorAccent), PorterDuff.Mode.MULTIPLY);
                            mSelection = view;
                        }
                    }
                }
            });

            imageView.setImageResource(mIconResources[position]);
            imageView.setColorFilter(ContextCompat.getColor(mContext, R.color.colorPrimaryDark), PorterDuff.Mode.MULTIPLY);
            imageView.setScaleType(ImageView.ScaleType.CENTER);
        }
        else
        {
            imageView = (CategoryIconGridViewItem) convertView;
        }
        return imageView;
    }

    private Integer[] mIconResources = {
            R.drawable.ic_train_white_24dp,
            R.drawable.ic_school_white_24dp,
            R.drawable.ic_restaurant_white_24dp,
            R.drawable.ic_location_city_white_24dp,
            R.drawable.ic_local_atm_white_24dp,
            R.drawable.ic_child_friendly_white_24dp,
            R.drawable.ic_cake_white_24dp,
            R.drawable.ic_beach_access_white_24dp,
            R.drawable.ic_airport_shuttle_white_24dp,
            R.drawable.ic_redeem_white_24dp,
            R.drawable.ic_computer_white_24dp,
            R.drawable.ic_home_white_24dp
    };
}
