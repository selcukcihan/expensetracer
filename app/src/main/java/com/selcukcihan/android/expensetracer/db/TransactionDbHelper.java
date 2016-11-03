package com.selcukcihan.android.expensetracer.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.selcukcihan.android.expensetracer.model.Category;

/**
 * Created by SELCUKCI on 2.11.2016.
 */

public class TransactionDbHelper extends ExpenseTracerDbHelper {
    public TransactionDbHelper(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TRANSACTIONS_TABLE = "CREATE TABLE " + ExpenseContract.TransactionTable.TABLE_NAME + "("
                + ExpenseContract.TransactionTable.COLUMN_NAME_TRANSACTION_ID + " INTEGER PRIMARY KEY,"
                + ExpenseContract.TransactionTable.COLUMN_NAME_AMOUNT + " INTEGER,"
                + ExpenseContract.TransactionTable.COLUMN_NAME_DATE + " INTEGER,"
                + ExpenseContract.TransactionTable.COLUMN_NAME_CATEGORY + " INTEGER,"
                + ExpenseContract.TransactionTable.COLUMN_NAME_NOTE + " TEXT)";

        db.execSQL(CREATE_TRANSACTIONS_TABLE);
    }
}
