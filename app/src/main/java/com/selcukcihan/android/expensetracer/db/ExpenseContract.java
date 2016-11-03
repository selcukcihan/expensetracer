package com.selcukcihan.android.expensetracer.db;

import android.provider.BaseColumns;

/**
 * Created by SELCUKCI on 20.10.2016.
 */

public final class ExpenseContract {
    public static final int DATABASE_VERSION   = 2;
    public static final String DATABASE_NAME      = "expense.db";
    public static final String CATEGORY_AUTHORITY = "com.selcukcihan.android.expensetracer.categoryprovider";
    public static final String TRANSACTION_AUTHORITY = "com.selcukcihan.android.expensetracer.transactionprovider";

    private ExpenseContract() {}

    public static class TransactionTable implements BaseColumns {
        public static final String TABLE_NAME = "TRANSACTION_TBL";
        public static final String TABLE_ALIAS = "T";
        public static final String COLUMN_NAME_TRANSACTION_ID = BaseColumns._ID;
        public static final String COLUMN_NAME_AMOUNT = "AMOUNT";
        public static final String COLUMN_NAME_DATE = "DATE";
        public static final String COLUMN_NAME_CATEGORY = "CATEGORY";
        public static final String COLUMN_NAME_NOTE = "NOTE";

        public static final String[] PROJECTION_CLIENT = {
                TABLE_NAME + "_" + COLUMN_NAME_TRANSACTION_ID,
                TABLE_NAME + "_" + COLUMN_NAME_AMOUNT,
                TABLE_NAME + "_" + COLUMN_NAME_DATE,
                TABLE_NAME + "_" + COLUMN_NAME_CATEGORY,
                TABLE_NAME + "_" + COLUMN_NAME_NOTE,
                ExpenseContract.CategoryTable.TABLE_NAME + "_" + ExpenseContract.CategoryTable.COLUMN_NAME_NAME,
                ExpenseContract.CategoryTable.TABLE_NAME + "_" + ExpenseContract.CategoryTable.COLUMN_NAME_RESOURCE_ID,
                ExpenseContract.CategoryTable.TABLE_NAME + "_" + ExpenseContract.CategoryTable.COLUMN_NAME_CATEGORY_TYPE,
                ExpenseContract.CategoryTable.TABLE_NAME + "_" + ExpenseContract.CategoryTable.COLUMN_NAME_CATEGORY_ID
        };

        public static final String[] PROJECTION_PROVIDER = {
                TABLE_ALIAS + "." + COLUMN_NAME_TRANSACTION_ID,
                TABLE_ALIAS + "." + COLUMN_NAME_AMOUNT,
                TABLE_ALIAS + "." + COLUMN_NAME_DATE,
                TABLE_ALIAS + "." + COLUMN_NAME_CATEGORY,
                TABLE_ALIAS + "." + COLUMN_NAME_NOTE,
                ExpenseContract.CategoryTable.TABLE_ALIAS + "." + ExpenseContract.CategoryTable.COLUMN_NAME_NAME,
                ExpenseContract.CategoryTable.TABLE_ALIAS + "." + ExpenseContract.CategoryTable.COLUMN_NAME_RESOURCE_ID,
                ExpenseContract.CategoryTable.TABLE_ALIAS + "." + ExpenseContract.CategoryTable.COLUMN_NAME_CATEGORY_TYPE,
                ExpenseContract.CategoryTable.TABLE_ALIAS + "." + ExpenseContract.CategoryTable.COLUMN_NAME_CATEGORY_ID
        };
    }

    public static class CategoryTable implements BaseColumns {
        public static final String TABLE_NAME = "CATEGORY";
        public static final String TABLE_ALIAS = "C";
        public static final String COLUMN_NAME_CATEGORY_ID = BaseColumns._ID;
        public static final String COLUMN_NAME_NAME = "NAME";
        public static final String COLUMN_NAME_CATEGORY_TYPE = "CATEGORY_TYPE";
        public static final String COLUMN_NAME_RESOURCE_ID = "RESOURCE_ID";

        public static final String[] PROJECTION_CLIENT = {
                TABLE_NAME + "_" + COLUMN_NAME_NAME,
                TABLE_NAME + "_" + COLUMN_NAME_RESOURCE_ID,
                TABLE_NAME + "_" + COLUMN_NAME_CATEGORY_TYPE,
                TABLE_NAME + "_" + COLUMN_NAME_CATEGORY_ID
        };

        public static final String[] PROJECTION_PROVIDER = {
                TABLE_ALIAS + "." + COLUMN_NAME_NAME,
                TABLE_ALIAS + "." + COLUMN_NAME_RESOURCE_ID,
                TABLE_ALIAS + "." + COLUMN_NAME_CATEGORY_TYPE,
                TABLE_ALIAS + "." + COLUMN_NAME_CATEGORY_ID
        };
    }
}
