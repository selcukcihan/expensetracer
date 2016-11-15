package com.selcukcihan.android.expensetracer.viewmodel;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.selcukcihan.android.expensetracer.R;
import com.selcukcihan.android.expensetracer.model.ChartType;
import com.selcukcihan.android.expensetracer.model.TransactionReport;
import com.selcukcihan.android.expensetracer.ui.chart.BarChartWrapper;
import com.selcukcihan.android.expensetracer.ui.chart.IChartWrapper;
import com.selcukcihan.android.expensetracer.ui.chart.PieChartWrapper;
import com.selcukcihan.android.expensetracer.ui.chart.WebChartWrapper;

/**
 * Created by SELCUKCI on 8.11.2016.
 */

public class ReportPagerAdapter extends PagerAdapter {
    private final Context mContext;
    private final TransactionReport mReport;
    private final ChartType mChartType;
    private final IChartWrapper mChartWrapper;

    public ReportPagerAdapter(Context context, TransactionReport report, ChartType chartType) {
        mContext = context;
        mReport = report;
        mChartType = chartType;

        switch (mChartType.getLayoutResourceId()) {
            case R.layout.chart_pie:
                mChartWrapper = new PieChartWrapper(mContext, mReport);
                break;
            case R.layout.chart_web:
                mChartWrapper = new WebChartWrapper(mContext, mReport);
                break;
            case R.layout.chart_bar:
                mChartWrapper = new BarChartWrapper(mContext, mReport);
                break;
            default:
                mChartWrapper = null;
                break;
        }
    }

    public ChartType getChartType() {
        return mChartType;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(mChartType.getLayoutResourceId(), collection, false);
        mChartWrapper.buildChart(layout, position);
        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return mChartWrapper.getPageCount();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mChartWrapper.getPageTitle(position);
    }

}
