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

import com.selcukcihan.android.expensetracer.model.Category;

import java.util.HashMap;

/**
 * Created by SELCUKCI on 25.10.2016.
 */

public class CategoryProvider extends ContentProvider {
    private ExpenseTracerDbHelper mDbHelper;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int CATEGORY_INCOME = 10;
    private static final int CATEGORY_EXPENSE = 20;
    private static final int CATEGORY_WITH_ID = 30;

    public static final String CATEGORY_BASE_PATH = "category";
    public static final String CATEGORY_INCOME_PATH = CATEGORY_BASE_PATH + "/income";
    public static final String CATEGORY_EXPENSE_PATH = CATEGORY_BASE_PATH + "/expense";
    public static final String CATEGORY_WITH_ID_PATH = CATEGORY_BASE_PATH + "/#";

    public static final Uri CATEGORY_INCOME_CONTENT_URI = Uri.parse("content://" + ExpenseContract.CATEGORY_AUTHORITY + "/" + CATEGORY_INCOME_PATH);
    public static final Uri CATEGORY_EXPENSE_CONTENT_URI = Uri.parse("content://" + ExpenseContract.CATEGORY_AUTHORITY + "/" + CATEGORY_EXPENSE_PATH);
    public static final Uri CATEGORY_CONTENT_URI = Uri.parse("content://" + ExpenseContract.CATEGORY_AUTHORITY + "/" + CATEGORY_BASE_PATH);

    static {
        sUriMatcher.addURI(ExpenseContract.CATEGORY_AUTHORITY, CATEGORY_INCOME_PATH, CATEGORY_INCOME);
        sUriMatcher.addURI(ExpenseContract.CATEGORY_AUTHORITY, CATEGORY_EXPENSE_PATH, CATEGORY_EXPENSE);
        sUriMatcher.addURI(ExpenseContract.CATEGORY_AUTHORITY, CATEGORY_WITH_ID_PATH, CATEGORY_WITH_ID);
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

        queryBuilder.setTables(ExpenseContract.CategoryTable.TABLE_NAME);

        switch (sUriMatcher.match(uri)) {
            case CATEGORY_INCOME:
                queryBuilder.appendWhere(ExpenseContract.CategoryTable.COLUMN_NAME_CATEGORY_TYPE + "=" + Category.CategoryType.INCOME.getValue());
                break;
            case CATEGORY_EXPENSE:
                queryBuilder.appendWhere(ExpenseContract.CategoryTable.COLUMN_NAME_CATEGORY_TYPE + "=" + Category.CategoryType.EXPENSE.getValue());
                break;
            case CATEGORY_WITH_ID:
                queryBuilder.appendWhere(ExpenseContract.CategoryTable._ID + "=" + uri.getLastPathSegment());
                break;
            default:
                break;
                // If the URI is not recognized, you should do some error handling here.
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
        long id = db.insert(ExpenseContract.CategoryTable.TABLE_NAME, "", values);
        if (id > 0) {
            Uri newUri = ContentUris.withAppendedId(CategoryProvider.CATEGORY_CONTENT_URI, id);
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
            case CATEGORY_WITH_ID:
                rowCount = db.delete(ExpenseContract.CategoryTable.TABLE_NAME, selection, selectionArgs);
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
            case CATEGORY_WITH_ID:
                rowCount = db.update(ExpenseContract.CategoryTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                break;
        }
        return rowCount;
    }
}
