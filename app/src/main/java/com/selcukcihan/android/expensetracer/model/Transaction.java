package com.selcukcihan.android.expensetracer.model;

import android.database.Cursor;

import com.selcukcihan.android.expensetracer.db.ExpenseContract;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by SELCUKCI on 20.10.2016.
 */

public class Transaction {
    private Long mId;
    private Long mAmount;
    private Long mDate;
    private Category mCategory;
    private Long mCategoryId;
    private String mNote;

    public Transaction(Cursor cursor) {
        mId = cursor.getLong(cursor.getColumnIndex(ExpenseContract.TransactionTable.TABLE_ALIAS + "."+ ExpenseContract.TransactionTable.COLUMN_NAME_TRANSACTION_ID));
        mAmount = cursor.getLong(cursor.getColumnIndex(ExpenseContract.TransactionTable.TABLE_ALIAS + "."+ ExpenseContract.TransactionTable.COLUMN_NAME_AMOUNT));
        mId = cursor.getLong(cursor.getColumnIndex(ExpenseContract.TransactionTable.TABLE_ALIAS + "."+ ExpenseContract.TransactionTable.COLUMN_NAME_TRANSACTION_ID));
        mNote = cursor.getString(cursor.getColumnIndex(ExpenseContract.TransactionTable.TABLE_ALIAS + "."+ ExpenseContract.TransactionTable.COLUMN_NAME_NOTE));
        mDate = cursor.getLong(cursor.getColumnIndex(ExpenseContract.TransactionTable.TABLE_ALIAS + "."+ ExpenseContract.TransactionTable.COLUMN_NAME_DATE));
        mCategory = new Category(cursor);
    }

    public Transaction(String amount, Date date, Long categoryId, String note) {
        mAmount = (long)(Float.parseFloat(amount) * 100);
        mDate = date.getTime();
        mCategoryId = categoryId;
        mNote = note;
    }

    public Category getCategory() {
        return mCategory;
    }

    public String getAmountText() {
        return String.format("%.2f", mAmount / 100.0f);
    }

    public Date getDate() {
        return new Date(mDate);
    }

    public String getNote() {
        return mNote;
    }

    public Long getAmountRaw() {
        return mAmount;
    }

    public Long getDateRaw() {
        return mDate;
    }

    public Long getCategoryId() {
        return (mCategory != null ? mCategory.getId() : mCategoryId);
    }
}
