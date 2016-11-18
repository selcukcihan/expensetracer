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

        List<Category> categories = new LinkedList<>();
        Cursor c = mContext.getContentResolver().query(uri, ExpenseContract.CategoryTable.PROJECTION, null, null, null);
        while (c.moveToNext()) {
            categories.add(new Category(c));
        }
        c.close();
        return categories;
    }

    public Category getCategoryById(long id) {
        Uri uri = ContentUris.withAppendedId(CategoryProvider.CATEGORY_CONTENT_URI, id);

        Cursor c = mContext.getContentResolver().query(uri, ExpenseContract.CategoryTable.PROJECTION, null, null, null);
        Category category;
        if (c.moveToNext()) {
            assert id == c.getInt(3) : "category id mismatch";
            category = new Category(c);
        } else {
            throw new NoSuchElementException("No category with id " + id);
        }
        c.close();
        return category;
    }

    public void deleteCategory(Category category) {
        String selectionClause = ExpenseContract.CategoryTable._ID +  "= ?";
        String[] selectionArgs = {String.format("%d", category.getId())};

        Uri uri = ContentUris.withAppendedId(CategoryProvider.CATEGORY_CONTENT_URI, category.getId());
        int rowsDeleted = mContext.getContentResolver().delete(uri, selectionClause, selectionArgs);
        assert rowsDeleted == 1 : "category table delete primary key violation";
    }

    private boolean duplicate(Category category) {
        List<Category> categories = getCategories(category.getCategoryType());
        for (Category c : categories) {
            if (c.getName().compareToIgnoreCase(category.getName()) == 0) {
                return true;
            }
        }
        return false;
    }

    public Long putCategory(Category category) {
        if (duplicate(category)) {
            return null;
        }

        ContentValues values = new ContentValues();

        values.put(ExpenseContract.CategoryTable.COLUMN_NAME_CATEGORY_TYPE, category.getCategoryType().getValue());
        values.put(ExpenseContract.CategoryTable.COLUMN_NAME_NAME, category.getName());
        values.put(ExpenseContract.CategoryTable.COLUMN_NAME_RESOURCE_ID, category.getResourceId());

        Uri uri = mContext.getContentResolver().insert(CategoryProvider.CATEGORY_CONTENT_URI, values);
        return Long.parseLong(uri.getLastPathSegment());
    }

    public boolean updateCategory(Category category) {
        if (duplicate(category)) {
            return false;
        }
        ContentValues values = new ContentValues();

        String selectionClause = ExpenseContract.CategoryTable._ID +  "= ?";
        String[] selectionArgs = {String.format("%d", category.getId())};

        values.put(ExpenseContract.CategoryTable.COLUMN_NAME_CATEGORY_TYPE, category.getCategoryType().getValue());
        values.put(ExpenseContract.CategoryTable.COLUMN_NAME_NAME, category.getName());
        values.put(ExpenseContract.CategoryTable.COLUMN_NAME_RESOURCE_ID, category.getResourceId());

        Uri uri = ContentUris.withAppendedId(CategoryProvider.CATEGORY_CONTENT_URI, category.getId());
        int rowsUpdated = mContext.getContentResolver().update(uri, values, selectionClause, selectionArgs);
        assert rowsUpdated == 1 : "category table update primary key violation";

        return true;
    }
}
