package com.selcukcihan.android.expensetracer.viewmodel;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.selcukcihan.android.expensetracer.model.Category;
import com.selcukcihan.android.expensetracer.ui.view.CategoryGridViewItem;
import com.selcukcihan.android.expensetracer.viewmodel.CategoryViewModel;

import java.util.List;

/**
 * Created by SELCUKCI on 20.10.2016.
 */

public class CategoryAdapter extends BaseAdapter {
    private Context mContext;
    private List<Category> mCategories;
    public CategoryAdapter(Context c, Category.CategoryType categoryType) {
        mContext = c;
        CategoryViewModel viewModel = new CategoryViewModel(c);
        mCategories = viewModel.getCategories(categoryType);
    }

    public int getCount() {
        return mCategories.size();
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
            Category c = mCategories.get(position);
            view = new CategoryGridViewItem(mContext);
            view.init(c);
        } else {
            view = (CategoryGridViewItem) convertView;
        }
        return view;
    }
}
