package com.selcukcihan.android.expensetracer.ui.chart;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.data.Entry;
import com.selcukcihan.android.expensetracer.R;
import com.selcukcihan.android.expensetracer.model.TransactionReport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SELCUKCI on 15.11.2016.
 */

public abstract class MonthlyChartWrapper extends ChartWrapper implements IChartWrapper {
    protected class MonthlyChartData {
        public final List<Entry> Entries;
        public final List<String> Labels;
        public final int [] Colors;
        public final Float Total;


        public MonthlyChartData(List<Entry> entries, List<String> labels, int [] colors, Float total) {
            Entries = entries;
            Labels = labels;
            Colors = colors;
            Total = total;
        }
    }

    public MonthlyChartWrapper(Context context, TransactionReport report) {
        super(context, report);
    }

    protected abstract void customize(ViewGroup parent, MonthlyChartData data);

    public void buildChart(ViewGroup layout, int position) {
        MonthlyChartData myData = prepareChartData(mReport.getMonth(position));
        ((TextView) layout.findViewById(R.id.text_report)).setText(String.format("Total expenses: %.2f", myData.Total));

        customize(layout, myData);
    }

    public int getPageCount() {
        return mReport.getMonthCount();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mReport.getMonthlyPageTitle(position);
    }

    private MonthlyChartData prepareChartData(TransactionReport.TransactionMonthly data) {
        ArrayList<String> labels = new ArrayList<String>();
        ArrayList<Entry> entries = new ArrayList<>();
        int i = 0;

        Float total = 0f;
        for (TransactionReport.TransactionMonthlyItem item : data.Items) {
            labels.add(item.Category.getName());
            entries.add(new Entry(item.getValue(), i++));
            total += item.getValue();
        }

        int[] categoryColors = mContext.getResources().getIntArray(R.array.category_colors);
        int colors[] = new int[labels.size()];
        for (i = 0; i < colors.length; i++) {
            colors[i] = categoryColors[i % categoryColors.length];
        };

        return new MonthlyChartData(entries, labels, colors, total);
    }
}
