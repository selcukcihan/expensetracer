package com.selcukcihan.android.expensetracer.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.selcukcihan.android.expensetracer.model.Category;
import com.selcukcihan.android.expensetracer.model.Transaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by SELCUKCI on 21.10.2016.
 */

public class ExpenseTracerDbHelper extends SQLiteOpenHelper {
    public ExpenseTracerDbHelper(Context context) {
        super(context, ExpenseContract.DATABASE_NAME, null, ExpenseContract.DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    private void createCategoryTable(SQLiteDatabase db) {
        String CREATE_CATEGORIES_TABLE = "CREATE TABLE " + ExpenseContract.CategoryTable.TABLE_NAME + "("
                + ExpenseContract.CategoryTable._ID + " INTEGER PRIMARY KEY,"
                + ExpenseContract.CategoryTable.COLUMN_NAME_CATEGORY_TYPE + " INTEGER,"
                + ExpenseContract.CategoryTable.COLUMN_NAME_NAME + " TEXT,"
                + ExpenseContract.CategoryTable.COLUMN_NAME_RESOURCE_ID + " STRING)";

        db.execSQL(CREATE_CATEGORIES_TABLE);

        for (Category c : Category.DEFAULT_EXPENSE_CATEGORIES) {
            addCategory(c, db);
        }
        for (Category c : Category.DEFAULT_INCOME_CATEGORIES) {
            addCategory(c, db);
        }
    }

    private void createTransactionTable(SQLiteDatabase db) {
        String CREATE_TRANSACTIONS_TABLE = "CREATE TABLE " + ExpenseContract.TransactionTable.TABLE_NAME + "("
                + ExpenseContract.TransactionTable._ID + " INTEGER PRIMARY KEY,"
                + ExpenseContract.TransactionTable.COLUMN_NAME_AMOUNT + " INTEGER,"
                + ExpenseContract.TransactionTable.COLUMN_NAME_DATE + " INTEGER,"
                + ExpenseContract.TransactionTable.COLUMN_NAME_CATEGORY + " INTEGER,"
                + ExpenseContract.TransactionTable.COLUMN_NAME_NOTE + " TEXT)";

        db.execSQL(CREATE_TRANSACTIONS_TABLE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createCategoryTable(db);
        createTransactionTable(db);

        try {
            for (String year : new String[]{"2014", "2015", "2016"}) {
                addDummyTransactions(db, year);
                addDummyIncomes(db, year);
            }
        } catch(Exception ex) {
            Log.e("PSEUDO", ex.toString());
        }
    }

    private void addDummyIncomes(SQLiteDatabase db, String year) throws ParseException {
        Random r = new Random();
        String[] randomNotes = new String[] {
                "Random bus",
                "Cinema ticket",
                "Dummy dummy",
                "Market spending",
                "Shirt and trousers",
                "Holiday",
                "Swimming pool",
                "Gas for the car",
                "Mobile bill"
        };
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        for (int m = 0; m < 12; m++) {
            Date date = format.parse(year + "-" + String.format("%2d", m + 1) + "-" + String.format("%2d", 1));
            Transaction t = new Transaction("4000", date, (long)8, "Salary");

            ContentValues values = new ContentValues();
            values.put(ExpenseContract.TransactionTable.COLUMN_NAME_AMOUNT, t.getAmountRaw());
            values.put(ExpenseContract.TransactionTable.COLUMN_NAME_DATE, t.getDateRaw());
            values.put(ExpenseContract.TransactionTable.COLUMN_NAME_CATEGORY, t.getCategoryId());
            values.put(ExpenseContract.TransactionTable.COLUMN_NAME_NOTE, t.getNote());
            db.insert(ExpenseContract.TransactionTable.TABLE_NAME, null, values);
        }
    }

    private void addDummyTransactions(SQLiteDatabase db, String year) throws ParseException {
        Random r = new Random();
        String[] randomNotes = new String[] {
                "Random bus",
                "Cinema ticket",
                "Dummy dummy",
                "Market spending",
                "Shirt and trousers",
                "Holiday",
                "Swimming pool",
                "Gas for the car",
                "Mobile bill"
        };
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        for (int m = 0; m < 12; m++) {
            int monthlyCount = 20 + r.nextInt(100);
            for (int i = 0; i < monthlyCount; i++) {
                Float amount = 2 + r.nextInt(10000) / 100.0f;
                Date date = format.parse(year + "-" + String.format("%2d", m + 1) + "-" + String.format("%2d", 1 + r.nextInt(28)));
                Transaction t = new Transaction(String.format("%.2f", amount), date,
                        (long)r.nextInt(Category.DEFAULT_EXPENSE_CATEGORIES.length),
                        randomNotes[r.nextInt(randomNotes.length)]);

                ContentValues values = new ContentValues();
                values.put(ExpenseContract.TransactionTable.COLUMN_NAME_AMOUNT, t.getAmountRaw());
                values.put(ExpenseContract.TransactionTable.COLUMN_NAME_DATE, t.getDateRaw());
                values.put(ExpenseContract.TransactionTable.COLUMN_NAME_CATEGORY, t.getCategoryId());
                values.put(ExpenseContract.TransactionTable.COLUMN_NAME_NOTE, t.getNote());
                db.insert(ExpenseContract.TransactionTable.TABLE_NAME, null, values);
            }
        }
    }

    public void addCategory(Category category, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(ExpenseContract.CategoryTable.COLUMN_NAME_NAME, category.getName());
        values.put(ExpenseContract.CategoryTable.COLUMN_NAME_RESOURCE_ID, category.getResourceId());
        values.put(ExpenseContract.CategoryTable.COLUMN_NAME_CATEGORY_TYPE, category.getCategoryType().getValue());

        // Inserting Row
        db.insert(ExpenseContract.CategoryTable.TABLE_NAME, null, values);
    }
}
