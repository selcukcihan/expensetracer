package com.selcukcihan.android.expensetracer.ui;

import android.content.Context;
import android.support.v7.widget.GridLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.selcukcihan.android.expensetracer.R;

/**
 * Created by SELCUKCI on 20.10.2016.
 */

public class CategoryAdapter extends BaseAdapter {
    private Context mContext;

    public CategoryAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return 4;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        CategoryGridViewItem view;
        if (convertView == null) {
            view = new CategoryGridViewItem(mContext);
        } else {
            view = (CategoryGridViewItem) convertView;
        }

        //layout.setImageResource(mThumbIds[position]);
        return view;
    }
}
