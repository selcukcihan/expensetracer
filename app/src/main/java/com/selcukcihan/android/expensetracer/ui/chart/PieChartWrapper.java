package com.selcukcihan.android.expensetracer.ui.chart;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.selcukcihan.android.expensetracer.R;
import com.selcukcihan.android.expensetracer.model.TransactionReport;

/**
 * Created by SELCUKCI on 15.11.2016.
 */

public class PieChartWrapper extends MonthlyChartWrapper implements IChartWrapper {

    public PieChartWrapper(Context context, TransactionReport report) {
        super(context, report);
    }

    @Override
    protected void customize(ViewGroup parent, MonthlyChartData data) {
        PieChart chart = (PieChart)parent.findViewById(R.id.chart_pie);

        PieDataSet dataset = new PieDataSet(data.Entries, "");
        dataset.setColors(data.Colors);
        dataset.setSliceSpace(3f);

        chart.setData(new PieData(data.Labels, dataset));

        chart.setDescriptionTextSize(14);

        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(ContextCompat.getColor(chart.getContext(), R.color.colorWhite));
        chart.setTransparentCircleRadius(0f);
        chart.setHoleRadius(25f);
        chart.setDrawCenterText(true);
        chart.setRotationEnabled(false);
        chart.setHighlightPerTapEnabled(true);

        chart.setDescription("Expense by category");

        for (PieDataSet set : chart.getData().getDataSets()) {
            set.setDrawValues(true);
            set.setValueTextSize(12f);
        }
    }
}
