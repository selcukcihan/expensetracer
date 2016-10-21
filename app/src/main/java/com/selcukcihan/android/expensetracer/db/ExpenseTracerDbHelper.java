package com.selcukcihan.android.expensetracer.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.selcukcihan.android.expensetracer.model.Category;

/**
 * Created by SELCUKCI on 21.10.2016.
 */

public class ExpenseTracerDbHelper extends SQLiteOpenHelper {
    public ExpenseTracerDbHelper(Context context) {
        super(context, ExpenseContract.DATABASE_NAME, null, ExpenseContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + ExpenseContract.CategoryTable.TABLE_NAME + "("
                + ExpenseContract.CategoryTable.COLUMN_NAME_CATEGORY_ID + " INTEGER PRIMARY KEY,"
                + ExpenseContract.CategoryTable.COLUMN_NAME_CATEGORY_TYPE + " INTEGER,"
                + ExpenseContract.CategoryTable.COLUMN_NAME_NAME + " TEXT,"
                + ExpenseContract.CategoryTable.COLUMN_NAME_RESOURCE_ID + " INTEGER)";
        addCategory(new Category(""));
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    public void addCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ExpenseContract.CategoryTable.COLUMN_NAME_NAME, category.getName());
        values.put(ExpenseContract.CategoryTable.COLUMN_NAME_RESOURCE_ID, category.getResourceId());
        values.put(ExpenseContract.CategoryTable.COLUMN_NAME_CATEGORY_TYPE, category.getCategoryType());

        // Inserting Row
        db.insert(ExpenseContract.CategoryTable.TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}
