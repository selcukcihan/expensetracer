package com.selcukcihan.android.expensetracer.ui.chart;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.selcukcihan.android.expensetracer.R;
import com.selcukcihan.android.expensetracer.model.TransactionReport;
import com.selcukcihan.android.expensetracer.ui.view.RadarMarkerView;

/**
 * Created by SELCUKCI on 15.11.2016.
 */

public class WebChartWrapper extends MonthlyChartWrapper implements IChartWrapper {

    public WebChartWrapper(Context context, TransactionReport report) {
        super(context, report);
    }

    @Override
    protected void customize(ViewGroup parent, MonthlyChartData data) {
        RadarChart chart = (RadarChart) parent.findViewById(R.id.chart_web);
        //chart.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorDivider));

        chart.setDescription("description");

        chart.setWebLineWidth(1f);
        chart.setWebColor(R.color.colorIncome);
        chart.setWebLineWidthInner(1f);
        chart.setWebColorInner(R.color.colorPurple);
        chart.setWebAlpha(100);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        MarkerView mv = new RadarMarkerView(mContext, R.layout.radar_markerview);
        chart.setMarkerView(mv);

        setDataForWebChart(chart, data);

/*
        XAxis xAxis = chart.getXAxis();
        //xAxis.setTypeface(mTfLight);
        xAxis.setTextSize(9f);
        xAxis.setYOffset(0f);
        xAxis.setXOffset(0f);
        xAxis.setValueFormatter(new XAxisValueFormatter() {
            @Override
            public String getXValue(String s, int i, ViewPortHandler viewPortHandler) {
                return mActivities[(int) i % mActivities.length];
            }
        });
        xAxis.setTextColor(R.color.colorPrimaryLight);*/
/*
        YAxis yAxis = chart.getYAxis();
        yAxis.setLabelCount(5, false);
        yAxis.setTextSize(9f);
        yAxis.setAxisMinValue(0f);
        yAxis.setAxisMaxValue(80f);
        yAxis.setDrawLabels(false);*/
/*
        Legend l = chart.getLegend();
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
        l.setTextColor(Color.WHITE);*/
    }

    private void setDataForWebChart(Chart chart, MonthlyChartData myData) {
        RadarDataSet set = new RadarDataSet(myData.Entries, "");
        set.setColor(R.color.colorGreenish);
        set.setFillColor(R.color.colorAccent);
        set.setDrawFilled(true);
        //set.setFillAlpha(180);
        set.setLineWidth(2f);
        set.setDrawHighlightIndicators(false);

        RadarData data = new RadarData(myData.Labels, set);
        data.setValueTextSize(8f);
        data.setDrawValues(false);
        data.setValueTextColor(Color.WHITE);

        chart.setTouchEnabled(false);
        chart.setData(data);
        chart.invalidate();
    }
}
