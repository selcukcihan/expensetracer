package com.selcukcihan.android.expensetracer.ui.chart;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.selcukcihan.android.expensetracer.R;
import com.selcukcihan.android.expensetracer.model.TransactionReport;

import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by SELCUKCI on 15.11.2016.
 */

public class BarChartWrapper extends ChartWrapper implements IChartWrapper {


    public BarChartWrapper(Context context, TransactionReport report) {
        super(context, report);
    }

    public void buildChart(ViewGroup layout, int position) {
        BarChart chart = (BarChart)layout.findViewById(R.id.chart_bar);
        //chart.setBackgroundColor(Color.WHITE);

        chart.setDescription("");
        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);
        chart.setPinchZoom(false);
        chart.setDrawGridBackground(false);

        chart.getAxisLeft().setEnabled(false);
        chart.getAxisRight().setDrawGridLines(false);
        //chart.getAxisRight().setDrawZeroLine(true);
        //chart.getAxisRight().setLabelCount(7, false);
        //chart.getAxisRight().setValueFormatter(new CustomFormatter());
        chart.getAxisRight().setTextSize(9f);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        xAxis.setLabelRotationAngle(90f);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setTextSize(8f);

        Legend l = chart.getLegend();
        l.setFormSize(8f);
        l.setFormToTextSpace(4f);
        l.setXEntrySpace(6f);

        ArrayList<BarEntry> yValues = new ArrayList<BarEntry>();
        TransactionReport.TransactionMonthly[] data = mReport.getYear(position);
        float minValue = 0, maxValue = 0;
        for (int i = 0; i < data.length; i++) {
            yValues.add(new BarEntry(new float[]{ -data[i].getExpense(), data[i].getIncome() }, i));
            if (-data[i].getExpense() < minValue) {
                minValue = -data[i].getExpense();
            }
            if (data[i].getIncome() > maxValue) {
                maxValue = data[i].getIncome();
            }
        }
        chart.getAxisRight().setAxisMinValue(minValue + minValue * 0.1f);
        chart.getAxisRight().setAxisMaxValue(maxValue + maxValue * 0.1f);

        BarDataSet set = new BarDataSet(yValues, "{Income - Expense Graph}");
        set.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float v, Entry entry, int i, ViewPortHandler viewPortHandler) {
                return String.format("%.0f", Math.abs(v));
            }
        });
        set.setValueTextSize(7f);
        set.setAxisDependency(YAxis.AxisDependency.RIGHT);
        set.setColors(new int[] {ContextCompat.getColor(mContext, R.color.colorExpense), ContextCompat.getColor(mContext, R.color.colorIncome)});
        set.setStackLabels(new String[]{
                "Expense", "Income"
        });

        DateFormatSymbols symbols = new DateFormatSymbols();
        String[] monthNames = symbols.getShortMonths();
        chart.setData(new BarData(monthNames, set));
        chart.invalidate();
    }

    @Override
    public int getPageCount() {
        return mReport.getYearCount();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mReport.getYearlyPageTitle(position);
    }
}
