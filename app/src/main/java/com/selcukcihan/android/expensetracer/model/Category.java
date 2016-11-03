package com.selcukcihan.android.expensetracer.model;

import android.database.Cursor;

import com.selcukcihan.android.expensetracer.R;
import com.selcukcihan.android.expensetracer.db.ExpenseContract;

/**
 * Created by SELCUKCI on 18.10.2016.
 */

public class Category {
    private Long mId;
    private String mName;
    private int mResourceId;
    private CategoryType mCategoryType;

    public enum CategoryType {
        INCOME(0), EXPENSE(1);

        private final int mValue;
        private CategoryType(int value) {
            this.mValue = value;
        }

        public int getValue() {
            return mValue;
        }

        public static CategoryType fromInt(int i) {
            for (CategoryType c : CategoryType.values()) {
                if (c.getValue() == i) { return c; }
            }
            return null;
        }
    }

    public Category(String name, int resourceId, CategoryType categoryType) {
        mName = name;
        mResourceId = resourceId;
        mCategoryType = categoryType;
    }

    public Category(Cursor cursor) {
        mId = cursor.getLong(cursor.getColumnIndex(ExpenseContract.CategoryTable.TABLE_ALIAS + "."+ ExpenseContract.CategoryTable.COLUMN_NAME_CATEGORY_ID));
        mName = cursor.getString(cursor.getColumnIndex(ExpenseContract.CategoryTable.TABLE_ALIAS + "."+ ExpenseContract.CategoryTable.COLUMN_NAME_NAME));
        mResourceId = cursor.getInt(cursor.getColumnIndex(ExpenseContract.CategoryTable.TABLE_ALIAS + "."+ ExpenseContract.CategoryTable.COLUMN_NAME_RESOURCE_ID));
        mCategoryType = Category.CategoryType.fromInt(
                cursor.getInt(cursor.getColumnIndex(ExpenseContract.CategoryTable.TABLE_ALIAS + "."+ ExpenseContract.CategoryTable.COLUMN_NAME_CATEGORY_TYPE)));
    }

    public Category(String name, int resourceId, CategoryType categoryType, long categoryId) {
        this(name, resourceId, categoryType);
        mId = categoryId;
    }

    public String getName() { return mName; }
    public int getResourceId() { return mResourceId; }
    public CategoryType getCategoryType() { return mCategoryType; }
    public long getId() {
        if (mId == null) {
            throw new IllegalStateException("Category id not set.");
        }
        return mId;
    }
    public void setId(long id) {
        if (mId != null) {
            throw new IllegalStateException("Category already set. Original value: " + mId + ", New value: " + id);
        }
        mId = id;
    }

    public static final Category[] DEFAULT_EXPENSE_CATEGORIES = new Category[] {
            new Category("Market", R.drawable.ic_shopping_cart_white_24dp, CategoryType.EXPENSE),
            new Category("Health", R.drawable.ic_local_hospital_white_24dp, CategoryType.EXPENSE),
            new Category("Transportation", R.drawable.ic_train_white_24dp, CategoryType.EXPENSE),
            new Category("Clothing", R.drawable.ic_shopping_basket_white_24dp, CategoryType.EXPENSE),
            new Category("Gas", R.drawable.ic_local_gas_station_white_24dp, CategoryType.EXPENSE),
            new Category("Bills", R.drawable.ic_description_white_24dp, CategoryType.EXPENSE),
            new Category("Eating out", R.drawable.ic_restaurant_white_24dp, CategoryType.EXPENSE)
    };

    public static final Category[] DEFAULT_INCOME_CATEGORIES = new Category[] {
            new Category("Salary", R.drawable.ic_local_atm_white_24dp, CategoryType.INCOME),
            new Category("Gift", R.drawable.ic_redeem_white_24dp, CategoryType.INCOME)
    };
}
