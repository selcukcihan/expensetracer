package com.selcukcihan.android.expensetracer.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayout;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.selcukcihan.android.expensetracer.CategoryActivity;
import com.selcukcihan.android.expensetracer.CategorySelectionActivity;
import com.selcukcihan.android.expensetracer.R;

/**
 * Created by SELCUKCI on 18.10.2016.
 */

public class CategoryView extends GridLayout {
    private ImageButton mSelectedButton;

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

    private void init() {
        setUseDefaultMargins(true);
        setAlignmentMode(GridLayout.ALIGN_BOUNDS);

        setColumnCount(3);
        setRowCount(3);

        Spec rows[] = new Spec[]{GridLayout.spec(0, FILL, 1), GridLayout.spec(1, FILL, 1), GridLayout.spec(2, FILL, 1)};
        Spec cols[] = new Spec[]{GridLayout.spec(0, FILL, 1), GridLayout.spec(1, FILL, 1), GridLayout.spec(2, FILL, 1)};

        addButton(rows[0], cols[0], R.drawable.ic_restaurant_white_24dp);
        addButton(rows[0], cols[1], R.drawable.ic_local_atm_white_24dp);
        addButton(rows[0], cols[2], R.drawable.ic_local_hospital_white_24dp);

        addButton(rows[1], cols[0], R.drawable.ic_child_friendly_white_24dp);
        addButton(rows[1], cols[1], R.drawable.ic_cake_white_24dp);
        addButton(rows[1], cols[2], R.drawable.ic_airport_shuttle_white_24dp);

        addButton(rows[2], cols[0], R.drawable.ic_train_white_24dp);
        addButton(rows[2], cols[1], R.drawable.ic_location_city_white_24dp);
        addButton(rows[2], cols[2], R.drawable.abc_ic_menu_moreoverflow_mtrl_alpha).setTag(true);
    }

    private ImageButton addButton(Spec row, Spec column, int id) {
        /*
        FrameLayout frameLayout = new FrameLayout(getContext());
        FrameLayout.LayoutParams frameLayoutParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
        progressbar.setLayoutParams(params);
        progressbar.setVisibility(View.INVISIBLE);*/

        ImageButton b = new ImageButton(getContext());

        b.setImageResource(id);
        b.setBackgroundColor(Color.TRANSPARENT);
        b.getDrawable().setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark), PorterDuff.Mode.MULTIPLY);
        b.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageButton button = (ImageButton)v;
                if (button.getTag() == null) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN: {
                            if (mSelectedButton == button) {
                                button.getDrawable().setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark), PorterDuff.Mode.MULTIPLY);
                                button.invalidate();
                                mSelectedButton = null;
                            } else {
                                if (mSelectedButton != null) {
                                    mSelectedButton.getDrawable().setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark), PorterDuff.Mode.MULTIPLY);
                                    mSelectedButton.invalidate();
                                }

                                mSelectedButton = button;
                                mSelectedButton.getDrawable().setColorFilter(ContextCompat.getColor(getContext(), R.color.colorAccent), PorterDuff.Mode.MULTIPLY);
                                mSelectedButton.invalidate();
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
                        ((Activity) getContext()).startActivityForResult(i, 1);
                    }
                }
                return false;
            }
        });

        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(row, column);
        layoutParams.setGravity(Gravity.FILL);
        layoutParams.width = 0;
        layoutParams.height = 100;
        b.setLayoutParams(layoutParams);
        addView(b);
        return b;
    }
}
