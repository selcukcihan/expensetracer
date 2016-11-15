package com.selcukcihan.android.expensetracer.ui.chart;

import android.content.Context;

import com.selcukcihan.android.expensetracer.model.TransactionReport;

/**
 * Created by SELCUKCI on 15.11.2016.
 */

public abstract class ChartWrapper implements IChartWrapper {
    protected final Context mContext;
    protected final TransactionReport mReport;

    protected ChartWrapper(Context context, TransactionReport report) {
        mContext = context;
        mReport = report;
    }
}
