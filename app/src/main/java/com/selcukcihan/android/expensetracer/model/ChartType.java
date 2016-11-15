package com.selcukcihan.android.expensetracer.model;

import com.selcukcihan.android.expensetracer.R;

/**
 * Created by SELCUKCI on 8.11.2016.
 */

public class ChartType {
    private final String mText;
    private final int mLayoutResourceId;
    private final int mIconResourceId;
    private ChartType(String text, int layoutResourceId, int iconResourceId) {
        mText = text;
        mLayoutResourceId = layoutResourceId;
        mIconResourceId = iconResourceId;
    }

    @Override
    public String toString() {
        return mText;
    }

    public int getLayoutResourceId() {
        return mLayoutResourceId;
    }

    public int getIconResourceId() {
        return mIconResourceId;
    }

    public static final ChartType[] CHART_TYPES = new ChartType[] {
            new ChartType("Pie chart", R.layout.chart_pie, R.drawable.ic_pie_chart_white_24dp),
            new ChartType("Web chart", R.layout.chart_web, R.drawable.ic_panorama_horizontal_white_24dp),
            new ChartType("Bar chart", R.layout.chart_bar, R.drawable.ic_insert_chart_white_24dp)
    };
}
