package com.selcukcihan.android.expensetracer.model;

/**
 * Created by SELCUKCI on 18.10.2016.
 */

public class Category {
    private int mId;
    private String mName;
    private int mResourceId;
    private int mCategoryType;

    public Category(String name, int resourceId, int categoryType) {
        mName = name;
        mResourceId = resourceId;
        mCategoryType = categoryType;
    }

    public String getName() { return mName; }
    public int getResourceId() { return mResourceId; }
    public int getCategoryType() { return mCategoryType; }
    public int getId() { return mId; }
}
