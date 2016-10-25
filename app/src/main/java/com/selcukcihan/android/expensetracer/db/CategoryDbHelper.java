package com.selcukcihan.android.expensetracer.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.selcukcihan.android.expensetracer.model.Category;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by SELCUKCI on 25.10.2016.
 */

public class CategoryDbHelper extends ExpenseTracerDbHelper {
    public CategoryDbHelper(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CATEGORIES_TABLE = "CREATE TABLE " + ExpenseContract.CategoryTable.TABLE_NAME + "("
                + ExpenseContract.CategoryTable.COLUMN_NAME_CATEGORY_ID + " INTEGER PRIMARY KEY,"
                + ExpenseContract.CategoryTable.COLUMN_NAME_CATEGORY_TYPE + " INTEGER,"
                + ExpenseContract.CategoryTable.COLUMN_NAME_NAME + " TEXT,"
                + ExpenseContract.CategoryTable.COLUMN_NAME_RESOURCE_ID + " INTEGER)";

        db.execSQL(CREATE_CATEGORIES_TABLE);

        for (Category c : Category.DEFAULT_EXPENSE_CATEGORIES) {
            addCategory(c, db);
        }
        for (Category c : Category.DEFAULT_INCOME_CATEGORIES) {
            addCategory(c, db);
        }
    }

    public void addCategory(Category category, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(ExpenseContract.CategoryTable.COLUMN_NAME_NAME, category.getName());
        values.put(ExpenseContract.CategoryTable.COLUMN_NAME_RESOURCE_ID, category.getResourceId());
        values.put(ExpenseContract.CategoryTable.COLUMN_NAME_CATEGORY_TYPE, category.getCategoryType().getValue());

        // Inserting Row
        db.insert(ExpenseContract.CategoryTable.TABLE_NAME, null, values);
    }

}
