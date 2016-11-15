package com.selcukcihan.android.expensetracer.ui.view;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.selcukcihan.android.expensetracer.R;

import java.text.DecimalFormat;

/**
 * Created by SELCUKCI on 14.11.2016.
 */

public class RadarMarkerView extends MarkerView {
    private TextView mContent;
    private DecimalFormat mFormat = new DecimalFormat("##0");

    public RadarMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);

        mContent = (TextView) findViewById(R.id.text_view_radar_content);
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        mContent.setText(mFormat.format(e.getVal() + " %"));
    }

    @Override
    public int getXOffset(float var1) {
        return -(getWidth() / 2);
    }

    @Override
    public int getYOffset(float var1) {
        return -getHeight() - 10;
    }
}
