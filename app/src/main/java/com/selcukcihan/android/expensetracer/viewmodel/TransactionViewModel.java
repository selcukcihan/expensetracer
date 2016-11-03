package com.selcukcihan.android.expensetracer.viewmodel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.selcukcihan.android.expensetracer.db.CategoryProvider;
import com.selcukcihan.android.expensetracer.db.ExpenseContract;
import com.selcukcihan.android.expensetracer.db.TransactionProvider;
import com.selcukcihan.android.expensetracer.model.Category;
import com.selcukcihan.android.expensetracer.model.Transaction;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by SELCUKCI on 2.11.2016.
 */

public class TransactionViewModel {
    private final Context mContext;
    public TransactionViewModel(Context context) {
        mContext = context;
    }
    public Cursor getTransactions() {
        Uri uri = TransactionProvider.TRANSACTION_ALL_CONTENT_URI;

        Cursor c = mContext.getContentResolver().query(uri, ExpenseContract.TransactionTable.PROJECTION_CLIENT, null, null,
                ExpenseContract.TransactionTable.TABLE_ALIAS + "." + ExpenseContract.TransactionTable.COLUMN_NAME_DATE + " DESC");
        return c;
    }

    public long putTransaction(Transaction transaction) {
        ContentValues values = new ContentValues();

        values.put(ExpenseContract.TransactionTable.COLUMN_NAME_AMOUNT, transaction.getAmountRaw());
        values.put(ExpenseContract.TransactionTable.COLUMN_NAME_DATE, transaction.getDateRaw());
        values.put(ExpenseContract.TransactionTable.COLUMN_NAME_CATEGORY, transaction.getCategoryId());
        values.put(ExpenseContract.TransactionTable.COLUMN_NAME_NOTE, transaction.getNote());

        Uri uri = mContext.getContentResolver().insert(TransactionProvider.TRANSACTION_CONTENT_URI, values);
        return Long.parseLong(uri.getLastPathSegment());
    }
}
