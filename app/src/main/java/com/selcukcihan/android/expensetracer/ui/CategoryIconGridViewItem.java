package com.selcukcihan.android.expensetracer.ui;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.selcukcihan.android.expensetracer.R;

/**
 * Created by SELCUKCI on 20.10.2016.
 */

public class CategoryIconGridViewItem extends ImageView {
    public CategoryIconGridViewItem(Context context) {
        super(context);
        init();
    }

    public CategoryIconGridViewItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CategoryIconGridViewItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        //setAdjustViewBounds(true);
        //android.view.ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //setLayoutParams(layoutParams);

    }
/*
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec); // This is the key that will make the height equivalent to its width
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec); // This is the key that will make the height equivalent to its width
    }*/
}
