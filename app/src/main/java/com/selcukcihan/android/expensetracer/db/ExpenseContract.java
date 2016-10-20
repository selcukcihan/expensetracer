package com.selcukcihan.android.expensetracer.db;

import android.provider.BaseColumns;

/**
 * Created by SELCUKCI on 20.10.2016.
 */

public final class ExpenseContract {
    public static final int DATABASE_VERSION   = 1;
    public static final String DATABASE_NAME      = "expense.db";
    private static final String TEXT_TYPE          = " TEXT";
    private static final String COMMA_SEP          = ",";

    private ExpenseContract() {}

    public static class TransactionTable implements BaseColumns {
        public static final String TABLE_NAME = "TRANSACTION";
        public static final String COLUMN_NAME_TRANSACTION_ID = "TRANSACTION_ID";
        public static final String COLUMN_NAME_AMOUNT = "AMOUNT";
        public static final String COLUMN_NAME_DATE = "DATE";
        public static final String COLUMN_NAME_CATEGORY = "CATEGORY";
        public static final String COLUMN_NAME_NOTE = "NOTE";
    }

    public static class CategoryTable implements BaseColumns {
        public static final String TABLE_NAME = "CATEGORY";
        public static final String COLUMN_NAME_CATEGORY_ID = "CATEGORY_ID";
        public static final String COLUMN_NAME_NAME = "NAME";
        public static final String COLUMN_NAME_CATEGORY_TYPE = "CATEGORY_TYPE";
        public static final String COLUMN_NAME_RESOURCE_ID = "RESOURCE_ID";
    }
}
