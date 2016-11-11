package com.selcukcihan.android.expensetracer.viewmodel;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.selcukcihan.android.expensetracer.db.CategoryProvider;
import com.selcukcihan.android.expensetracer.db.ExpenseContract;
import com.selcukcihan.android.expensetracer.db.TransactionProvider;
import com.selcukcihan.android.expensetracer.model.Category;
import com.selcukcihan.android.expensetracer.model.Transaction;
import com.selcukcihan.android.expensetracer.model.TransactionReport;

import java.util.Arrays;
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

        // Cursor c = mContext.getContentResolver().query(CategoryProvider.CATEGORY_CONTENT_URI, ExpenseContract.CategoryTable.PROJECTION_CLIENT, null, null,
        //         ExpenseContract.CategoryTable.TABLE_ALIAS + "." + ExpenseContract.CategoryTable.COLUMN_NAME_RESOURCE_ID + " DESC");
        // c.close();

        Cursor c = mContext.getContentResolver().query(uri, ExpenseContract.TransactionTable.PROJECTION, null, null,
                ExpenseContract.TransactionTable.TABLE_NAME + "." + ExpenseContract.TransactionTable.COLUMN_NAME_DATE + " DESC");
        return c;
    }

    public TransactionReport getTransactionReport() {
        Uri uri = TransactionProvider.TRANSACTION_ALL_CONTENT_URI;
        Cursor c = mContext.getContentResolver().query(uri, ExpenseContract.TransactionTable.PROJECTION, null, null,
                ExpenseContract.TransactionTable.TABLE_NAME + "." + ExpenseContract.TransactionTable.COLUMN_NAME_DATE + " DESC, "
                + ExpenseContract.TransactionTable.TABLE_NAME + "." + ExpenseContract.TransactionTable.COLUMN_NAME_CATEGORY + " ASC");

        List<Transaction> transactions = new LinkedList<>();
        while (c.moveToNext()) {
            transactions.add(new Transaction(c));
        }
        c.close();
        return new TransactionReport(transactions);
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

    public void deleteTransaction(Transaction transaction) {
        String selectionClause = ExpenseContract.TransactionTable._ID +  "= ?";
        String[] selectionArgs = {String.format("%d", transaction.getId())};

        Uri uri = ContentUris.withAppendedId(TransactionProvider.TRANSACTION_CONTENT_URI, transaction.getId());
        int rowsDeleted = mContext.getContentResolver().delete(uri, selectionClause, selectionArgs);
        assert rowsDeleted == 1 : "transaction table delete primary key violation";
    }

    public void updateTransaction(Transaction transaction) {
        ContentValues values = new ContentValues();

        String selectionClause = ExpenseContract.TransactionTable._ID +  "= ?";
        String[] selectionArgs = {String.format("%d", transaction.getId())};

        values.put(ExpenseContract.TransactionTable.COLUMN_NAME_AMOUNT, transaction.getAmountRaw());
        values.put(ExpenseContract.TransactionTable.COLUMN_NAME_CATEGORY, transaction.getCategoryId());
        values.put(ExpenseContract.TransactionTable.COLUMN_NAME_DATE, transaction.getDateRaw());
        values.put(ExpenseContract.TransactionTable.COLUMN_NAME_NOTE, transaction.getNote());

        Uri uri = ContentUris.withAppendedId(TransactionProvider.TRANSACTION_CONTENT_URI, transaction.getId());
        int rowsUpdated = mContext.getContentResolver().update(uri, values, selectionClause, selectionArgs);
        assert rowsUpdated == 1 : "transaction table update primary key violation";
    }
}
