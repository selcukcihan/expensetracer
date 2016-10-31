package com.selcukcihan.android.expensetracer.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.selcukcihan.android.expensetracer.CategoryActivity;
import com.selcukcihan.android.expensetracer.CategorySelectionActivity;
import com.selcukcihan.android.expensetracer.R;
import com.selcukcihan.android.expensetracer.TransactionActivity;
import com.selcukcihan.android.expensetracer.model.Category;
import com.selcukcihan.android.expensetracer.viewmodel.CategoryViewModel;

import java.io.Console;
import java.util.List;


/**
 * Created by SELCUKCI on 18.10.2016.
 */

public class CategoryView extends GridLayout {
    private ImageButton mSelectedButton;
    private Category.CategoryType mCategoryType;
    private final CategoryViewModel mCategoryViewModel = new CategoryViewModel(this.getContext());
    private CategoryObserver mObserver;

    private final int mColCount = 4;
    private final int mRowCount = 2;

    public CategoryView(Context context) {
        super(context);
        init();
    }

    public CategoryView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }
    public CategoryView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void reset(Category.CategoryType categoryType, Category category) {
        this.removeAllViews();
        init();
        mCategoryType = categoryType;
        mSelectedButton = null;
        populate(category);
        this.invalidate();
    }

    public void setObserver(CategoryObserver observer) {
        mObserver = observer;
    }

    private void populate(Category category) {
        Spec rows[] = new Spec[mRowCount];
        for (int i = 0; i < mRowCount; i++) {
            rows[i] = GridLayout.spec(i, FILL, 1);
        }

        Spec cols[] = new Spec[mColCount];
        for (int i = 0; i < mColCount; i++) {
            cols[i] = GridLayout.spec(i, FILL, 1);
        }

        List<Category> categories = mCategoryViewModel.getCategories(mCategoryType);
        int row = 0, col = 0, maxCount = mRowCount * mColCount - 1, count = 0;

        int selectedItemIndex = 0;

        // find the selected item's index, if it exists
        if (category != null) {
            for (int i = 0; i < categories.size(); i++) {
                if (categories.get(i).getId() == category.getId()) {
                    selectedItemIndex = i;
                    break;
                }
            }
        }

        if (selectedItemIndex >= maxCount) { // the selected item is not contained in the visible items, so we put it in the first cell (0, 0)
            addButton(rows[row], cols[col], category, category.getResourceId(), true);
            selectedItemIndex = -1; // we mark it that the selected item is already displayed
            col++;
            count++;
        }

        for (int i = 0; i < categories.size() && count < maxCount; i++) {
            Category cat = categories.get(i);
            addButton(rows[row], cols[col], cat, cat.getResourceId(), selectedItemIndex == i);
            count++;
            col++;
            if (col == mColCount) {
                col = 0;
                row++;
            }
        }
        addButton(rows[row], cols[col], null, R.drawable.abc_ic_menu_moreoverflow_mtrl_alpha, false);
        while (count < maxCount) {
            addSpace(rows[row], cols[col]);
            count++;
            col++;
            if (col == mColCount) {
                col = 0;
                row++;
            }
        }
    }

    private View addSpace(Spec row, Spec column) {
        View view = new View(getContext());
        view.setBackgroundColor(Color.TRANSPARENT);
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(row, column);
        layoutParams.setGravity(Gravity.FILL);
        layoutParams.width = 0;
        layoutParams.height = 1;
        view.setLayoutParams(layoutParams);
        addView(view);
        return view;
    }

    private void init() {
        //setUseDefaultMargins(false);
        //setAlignmentMode(GridLayout.ALIGN_MARGINS);
        //setAlignmentMode(GridLayout.FILL);

        setColumnCount(mColCount);
        setRowCount(mRowCount);
    }

    private void selectionChanged(ImageButton b) {
        if (mSelectedButton != null) {
            mSelectedButton.getDrawable().setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark), PorterDuff.Mode.MULTIPLY);
        }
        if (mObserver != null) {
            mObserver.onCategoryChanged(((Category)b.getTag()));
        }
        b.getDrawable().setColorFilter(ContextCompat.getColor(getContext(), R.color.colorAccent), PorterDuff.Mode.MULTIPLY);

        mSelectedButton = b;
    }

    private ImageButton addButton(Spec row, Spec column, Category category, int resourceId, boolean selected) {
        ImageButton b = new ImageButton(getContext());
        b.setTag(category);

        assert category == null || category.getResourceId() == resourceId : "category id mismatch";
        b.setImageResource(resourceId);
        b.setBackgroundColor(Color.TRANSPARENT);

        if (selected) {
            selectionChanged(b);
        } else {
            b.getDrawable().setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark), PorterDuff.Mode.MULTIPLY);
        }
        b.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageButton button = (ImageButton)v;
                if (button.getTag() != null) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN: {
                            if (mSelectedButton != button) {
                                selectionChanged(button);
                            }
                            break;
                        }
                        case MotionEvent.ACTION_UP: {
                            break;
                        }
                    }
                } else {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        Intent i = new Intent(getContext(), CategorySelectionActivity.class);
                        i.putExtra(TransactionActivity.EXTRA_CATEGORY_TYPE, mCategoryType.getValue());
                        ((Activity) getContext()).startActivityForResult(i, TransactionActivity.CATEGORY_SELECTION_ACTIVITY_REQUEST_CODE);
                    }
                }
                return false;
            }
        });

        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(row, column);
        layoutParams.setGravity(Gravity.FILL);
        layoutParams.width = 0;
        layoutParams.height = 1;
        b.setLayoutParams(layoutParams);
        addView(b);
        return b;
    }
}
