package com.selcukcihan.android.expensetracer.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.selcukcihan.android.expensetracer.model.Category;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by SELCUKCI on 21.10.2016.
 */

public abstract class ExpenseTracerDbHelper extends SQLiteOpenHelper {
    public ExpenseTracerDbHelper(Context context) {
        super(context, ExpenseContract.DATABASE_NAME, null, ExpenseContract.DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}
