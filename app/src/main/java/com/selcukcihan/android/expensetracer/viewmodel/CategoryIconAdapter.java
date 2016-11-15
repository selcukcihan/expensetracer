package com.selcukcihan.android.expensetracer.viewmodel;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.selcukcihan.android.expensetracer.R;
import com.selcukcihan.android.expensetracer.ui.view.CategoryIconGridViewItem;

/**
 * Created by SELCUKCI on 21.10.2016.
 */

public class CategoryIconAdapter extends BaseAdapter {
    private Context mContext;
    private ImageView mSelection;
    private Integer mSelectedResourceId = null;
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

    public void setSelectedIcon(int resourceId)
    {
        mSelectedResourceId = resourceId;
        notifyDataSetChanged();
    }

    public int getSelectedIcon() {
        return (int)mSelection.getTag();
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        CategoryIconGridViewItem imageView;

        if (convertView == null) {
            imageView = new CategoryIconGridViewItem(mContext);
            imageView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ImageView view = (ImageView) v;
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
            imageView.setTag(mIconResources[position]);
            if (mSelectedResourceId != null && mSelectedResourceId.equals(mIconResources[position])) {
                imageView.setColorFilter(ContextCompat.getColor(mContext, R.color.colorAccent), PorterDuff.Mode.MULTIPLY);
                mSelection = imageView;
            } else {
                imageView.setColorFilter(ContextCompat.getColor(mContext, R.color.colorPrimaryDark), PorterDuff.Mode.MULTIPLY);
            }
            imageView.setScaleType(ImageView.ScaleType.CENTER);
        }
        else
        {
            imageView = (CategoryIconGridViewItem) convertView;
        }
        return imageView;
    }

    private Integer[] mIconResources = {
            R.drawable.ic_beach_access_white_24dp,
            R.drawable.ic_child_friendly_white_24dp,
            R.drawable.ic_computer_white_24dp,
            R.drawable.ic_home_white_24dp,
            R.drawable.ic_local_atm_white_24dp,
            R.drawable.ic_local_gas_station_white_24dp,
            R.drawable.ic_local_hospital_white_24dp,
            R.drawable.ic_redeem_white_24dp,
            R.drawable.ic_restaurant_white_24dp,
            R.drawable.ic_school_white_24dp,
            R.drawable.ic_shopping_cart_white_24dp,
            R.drawable.ic_train_white_24dp
    };
}
