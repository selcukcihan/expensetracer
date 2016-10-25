package com.selcukcihan.android.expensetracer.viewmodel;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.selcukcihan.android.expensetracer.db.CategoryProvider;
import com.selcukcihan.android.expensetracer.db.ExpenseContract;
import com.selcukcihan.android.expensetracer.model.Category;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by SELCUKCI on 25.10.2016.
 */

public class CategoryViewModel {
    private final Context mContext;
    public CategoryViewModel(Context context) {
        mContext = context;
    }
    public List<Category> GetCategories(Category.CategoryType categoryType) {
        Uri uri = categoryType == Category.CategoryType.EXPENSE ? CategoryProvider.CATEGORY_EXPENSE_CONTENT_URI : CategoryProvider.CATEGORY_INCOME_CONTENT_URI;

        String[] projection = {
                ExpenseContract.CategoryTable.COLUMN_NAME_NAME,
                ExpenseContract.CategoryTable.COLUMN_NAME_RESOURCE_ID,
                ExpenseContract.CategoryTable.COLUMN_NAME_CATEGORY_TYPE,
                ExpenseContract.CategoryTable.COLUMN_NAME_CATEGORY_ID
        };

        List<Category> categories = new LinkedList<>();
        Cursor c = mContext.getContentResolver().query(uri, projection, null, null, null);
        while (c.moveToNext()) {
            categories.add(new Category(c.getString(0), c.getInt(1), Category.CategoryType.fromInt(c.getInt(2))));
        }
        c.close();
        return categories;
    }
}
