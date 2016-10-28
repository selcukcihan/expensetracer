package com.selcukcihan.android.expensetracer.ui;

import com.selcukcihan.android.expensetracer.model.Category;

/**
 * Created by SELCUKCI on 28.10.2016.
 */

public interface CategoryObserver {
    void onCategoryChanged(Category category);
}
