package com.selcukcihan.android.expensetracer.ui.chart;

import android.view.ViewGroup;

/**
 * Created by SELCUKCI on 15.11.2016.
 */
public interface IChartWrapper {
    void buildChart(ViewGroup layout, int position);

    int getPageCount();

    CharSequence getPageTitle(int position);
}
