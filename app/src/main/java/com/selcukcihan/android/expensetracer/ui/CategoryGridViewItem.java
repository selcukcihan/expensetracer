package com.selcukcihan.android.expensetracer.ui;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.selcukcihan.android.expensetracer.R;
import com.selcukcihan.android.expensetracer.model.Category;

/**
 * Created by SELCUKCI on 20.10.2016.
 */

public class CategoryGridViewItem extends LinearLayout {
    public CategoryGridViewItem(Context context) {
        super(context);
    }

    public CategoryGridViewItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CategoryGridViewItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void init(Category category) {
        setOrientation(LinearLayout.VERTICAL);
        setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(category.getResourceId());
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f));
        imageView.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark), PorterDuff.Mode.MULTIPLY);
        imageView.setPadding(8, 8, 8, 8);

        addView(imageView, 0);

        TextView textView = new TextView(getContext());
        textView.setText(category.getName());
        textView.setGravity(Gravity.CENTER);
        addView(textView, 1);

        setTag(category);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec); // This is the key that will make the height equivalent to its width
    }
}
