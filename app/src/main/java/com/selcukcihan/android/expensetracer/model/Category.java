package com.selcukcihan.android.expensetracer.model;

import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.selcukcihan.android.expensetracer.R;
import com.selcukcihan.android.expensetracer.db.ExpenseContract;

/**
 * Created by SELCUKCI on 18.10.2016.
 */

public class Category implements Parcelable {
    private Long mId;
    private String mName;
    private String mResourceId;
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

    public Category(String name, String resourceId, CategoryType categoryType) {
        mName = name;
        mResourceId = resourceId;
        mCategoryType = categoryType;
    }

    public Category(Cursor cursor) {
        this(cursor, false);
    }
    public Category(Cursor cursor, boolean useAlternativeId) {
        if (useAlternativeId) {
            mId = cursor.getLong(cursor.getColumnIndex(ExpenseContract.TransactionTable.COLUMN_NAME_CATEGORY));
        } else {
            mId = cursor.getLong(cursor.getColumnIndex(ExpenseContract.CategoryTable._ID));
        }
        mName = cursor.getString(cursor.getColumnIndex(ExpenseContract.CategoryTable.COLUMN_NAME_NAME));
        mResourceId = cursor.getString(cursor.getColumnIndex(ExpenseContract.CategoryTable.COLUMN_NAME_RESOURCE_ID));
        mCategoryType = Category.CategoryType.fromInt(cursor.getInt(cursor.getColumnIndex(ExpenseContract.CategoryTable.COLUMN_NAME_CATEGORY_TYPE)));
    }

    public Category(String name, String resourceId, CategoryType categoryType, long categoryId) {
        this(name, resourceId, categoryType);
        mId = categoryId;
    }

    public String getName() { return mName; }
    public String getResourceId() { return mResourceId; }
    public int getResourceIdInteger(Context context) {
        return context.getResources().getIdentifier(mResourceId, "drawable", context.getPackageName());
    }
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
            new Category("Market", "ic_shopping_cart_white_24dp", CategoryType.EXPENSE),
            new Category("Health", "ic_local_hospital_white_24dp", CategoryType.EXPENSE),
            new Category("Transportation", "ic_train_white_24dp", CategoryType.EXPENSE),
            new Category("Clothing", "ic_shopping_basket_white_24dp", CategoryType.EXPENSE),
            new Category("Gas", "ic_local_gas_station_white_24dp", CategoryType.EXPENSE),
            new Category("Bills", "ic_description_white_24dp", CategoryType.EXPENSE),
            new Category("Eating out", "ic_restaurant_white_24dp", CategoryType.EXPENSE)
    };

    public static final Category[] DEFAULT_INCOME_CATEGORIES = new Category[] {
            new Category("Salary", "ic_local_atm_white_24dp", CategoryType.INCOME),
            new Category("Gift", "ic_redeem_white_24dp", CategoryType.INCOME)
    };

    // Parcelling part
    public Category(Parcel in){
        this.mId = in.readLong();
        this.mName = in.readString();
        this.mResourceId = in.readString();
        this.mCategoryType = CategoryType.fromInt(in.readInt());
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mId);
        dest.writeString(this.mName);
        dest.writeString(this.mResourceId);
        dest.writeInt(this.mCategoryType.getValue());
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
}
