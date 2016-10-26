package com.selcukcihan.android.expensetracer.viewmodel;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.selcukcihan.android.expensetracer.db.CategoryProvider;
import com.selcukcihan.android.expensetracer.db.ExpenseContract;
import com.selcukcihan.android.expensetracer.model.Category;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * Created by SELCUKCI on 25.10.2016.
 */

public class CategoryViewModel {
    private final Context mContext;
    public CategoryViewModel(Context context) {
        mContext = context;
    }
    public List<Category> getCategories(Category.CategoryType categoryType) {
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
            categories.add(new Category(c.getString(0), c.getInt(1), Category.CategoryType.fromInt(c.getInt(2)), c.getInt(3)));
        }
        c.close();
        return categories;
    }

    public Category getCategoryById(int id) {
        Uri uri = ContentUris.withAppendedId(CategoryProvider.CATEGORY_CONTENT_URI, id);

        String[] projection = {
                ExpenseContract.CategoryTable.COLUMN_NAME_NAME,
                ExpenseContract.CategoryTable.COLUMN_NAME_RESOURCE_ID,
                ExpenseContract.CategoryTable.COLUMN_NAME_CATEGORY_TYPE,
                ExpenseContract.CategoryTable.COLUMN_NAME_CATEGORY_ID
        };

        Cursor c = mContext.getContentResolver().query(uri, projection, null, null, null);
        Category category;
        if (c.moveToNext()) {
            assert id == c.getInt(3) : "category id mismatch";
            category = new Category(c.getString(0), c.getInt(1), Category.CategoryType.fromInt(c.getInt(2)), id);
        } else {
            throw new NoSuchElementException("No category with id " + id);
        }
        c.close();
        return category;
    }

    public void deleteCategory(Category category) {
        String selectionClause = ExpenseContract.CategoryTable.COLUMN_NAME_CATEGORY_ID +  "= ?";
        String[] selectionArgs = {String.format("%ld", category.getId())};

        Uri uri = ContentUris.withAppendedId(CategoryProvider.CATEGORY_CONTENT_URI, category.getId());
        int rowsDeleted = mContext.getContentResolver().delete(uri, selectionClause, selectionArgs);
    }

    public long putCategory(Category category) {
        ContentValues values = new ContentValues();

        values.put(ExpenseContract.CategoryTable.COLUMN_NAME_CATEGORY_TYPE, category.getCategoryType().getValue());
        values.put(ExpenseContract.CategoryTable.COLUMN_NAME_NAME, category.getName());
        values.put(ExpenseContract.CategoryTable.COLUMN_NAME_RESOURCE_ID, category.getResourceId());

        Uri uri = mContext.getContentResolver().insert(CategoryProvider.CATEGORY_CONTENT_URI, values);
        return Long.parseLong(uri.getLastPathSegment());
    }

    public void updateCategory(Category category) {
        ContentValues values = new ContentValues();

        String selectionClause = ExpenseContract.CategoryTable.COLUMN_NAME_CATEGORY_ID +  "= ?";
        String[] selectionArgs = {String.format("%ld", category.getId())};

        int rowsUpdated = 0;

        values.put(ExpenseContract.CategoryTable.COLUMN_NAME_CATEGORY_TYPE, category.getCategoryType().getValue());
        values.put(ExpenseContract.CategoryTable.COLUMN_NAME_NAME, category.getName());
        values.put(ExpenseContract.CategoryTable.COLUMN_NAME_RESOURCE_ID, category.getResourceId());

        Uri uri = ContentUris.withAppendedId(CategoryProvider.CATEGORY_CONTENT_URI, category.getId());
        rowsUpdated = mContext.getContentResolver().update(uri, values, selectionClause, selectionArgs);
        assert rowsUpdated == 1 : "category table update primary key violation";
    }
}
