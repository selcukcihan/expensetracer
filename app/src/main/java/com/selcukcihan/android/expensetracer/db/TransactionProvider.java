package com.selcukcihan.android.expensetracer.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

import java.util.HashMap;

/**
 * Created by SELCUKCI on 2.11.2016.
 */

public class TransactionProvider extends ContentProvider {
    private ExpenseTracerDbHelper mDbHelper;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int TRANSACTION_ALL = 10;
    private static final int TRANSACTION_WITH_ID = 20;

    public static final String TRANSACTION_BASE_PATH = "transaction";
    public static final String TRANSACTION_ALL_PATH = TRANSACTION_BASE_PATH + "/all";
    public static final String TRANSACTION_WITH_ID_PATH = TRANSACTION_BASE_PATH + "/#";

    public static final Uri TRANSACTION_CONTENT_URI = Uri.parse("content://" + ExpenseContract.TRANSACTION_AUTHORITY + "/" + TRANSACTION_BASE_PATH);
    public static final Uri TRANSACTION_ALL_CONTENT_URI = Uri.parse("content://" + ExpenseContract.TRANSACTION_AUTHORITY + "/" + TRANSACTION_ALL_PATH);

    static {
        sUriMatcher.addURI(ExpenseContract.TRANSACTION_AUTHORITY, TRANSACTION_ALL_PATH, TRANSACTION_ALL);
        sUriMatcher.addURI(ExpenseContract.TRANSACTION_AUTHORITY, TRANSACTION_WITH_ID_PATH, TRANSACTION_WITH_ID);
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new ExpenseTracerDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        queryBuilder.setTables(ExpenseContract.TransactionTable.TABLE_NAME
            + " JOIN " + ExpenseContract.CategoryTable.TABLE_NAME + " ON ("
            + ExpenseContract.TransactionTable.TABLE_NAME + "." + ExpenseContract.TransactionTable.COLUMN_NAME_CATEGORY + " = "
            + ExpenseContract.CategoryTable.TABLE_NAME + "." + ExpenseContract.CategoryTable._ID + ")");

        switch (sUriMatcher.match(uri)) {
            case TRANSACTION_ALL:
                break;
            case TRANSACTION_WITH_ID:
                queryBuilder.appendWhere(ExpenseContract.TransactionTable.TABLE_NAME + "." + ExpenseContract.TransactionTable._ID + "=" + uri.getLastPathSegment());
                break;
            default:
                break;
        }
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        long id = db.insert(ExpenseContract.TransactionTable.TABLE_NAME, "", values);
        if (id > 0) {
            Uri newUri = ContentUris.withAppendedId(TransactionProvider.TRANSACTION_CONTENT_URI, id);
            getContext().getContentResolver().notifyChange(newUri, null);
            return newUri;
        }
        throw new SQLException("Fail to add a new record into " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int rowCount = 0;
        switch (sUriMatcher.match(uri)) {
            case TRANSACTION_WITH_ID:
                rowCount = db.delete(ExpenseContract.TransactionTable.TABLE_NAME, selection, selectionArgs);
                getContext().getContentResolver().notifyChange(uri, null);
                break;
            default:
                break;
        }
        return rowCount;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int rowCount = 0;
        switch (sUriMatcher.match(uri)) {
            case TRANSACTION_WITH_ID:
                rowCount = db.update(ExpenseContract.TransactionTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                break;
        }
        return rowCount;
    }
}
