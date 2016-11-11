package com.selcukcihan.android.expensetracer.ui;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.selcukcihan.android.expensetracer.R;
import com.selcukcihan.android.expensetracer.model.ChartType;
import com.selcukcihan.android.expensetracer.model.TransactionReport;
import com.selcukcihan.android.expensetracer.viewmodel.TransactionViewModel;

import java.util.ArrayList;

/**
 * Created by SELCUKCI on 8.11.2016.
 */

public class ReportPagerAdapter extends PagerAdapter {
    private final Context mContext;
    private final TransactionReport mReport;

    private ChartType mChartType = ChartType.CHART_TYPES[0];

    public ReportPagerAdapter(Context context) {
        mContext = context;
        mReport = (new TransactionViewModel(context)).getTransactionReport();
    }

    public void setChartType(ChartType chartType) {
        mChartType = chartType;
        this.notifyDataSetChanged();
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(mChartType.getLayoutResourceId(), collection, false);

        switch(mChartType.getLayoutResourceId()) {
            case R.layout.chart_pie:
                doPie(layout, position);
                break;
            case R.layout.chart_web:
                doWeb(layout, position);
                break;
            default:
                break;
        }
        collection.addView(layout);
        return layout;
    }

    private void doWeb(ViewGroup layout, int position) {
    }

    private void doPie(ViewGroup layout, int position) {
        PieChart chart = (PieChart)layout.findViewById(R.id.chart_pie);
        TextView textView = (TextView)layout.findViewById(R.id.text_report);

        TransactionReport.TransactionMonthly month = mReport.getMonth(position);

        ArrayList<String> labels = new ArrayList<String>();
        ArrayList<Entry> entries = new ArrayList<>();
        int i = 0;

        Float total = 0f;
        for (TransactionReport.TransactionMonthlyItem item : month.Items) {
            labels.add(item.Category.getName());
            entries.add(new Entry(item.getValue(), i++));
            total += item.getValue();
        }

        int[] categoryColors = mContext.getResources().getIntArray(R.array.category_colors);
        int colors[] = new int[labels.size()];
        for (i = 0; i < colors.length; i++) {
            colors[i] = categoryColors[i % categoryColors.length];
        };
        PieDataSet dataset = new PieDataSet(entries, "");
        dataset.setColors(colors);
        dataset.setSliceSpace(3f);

        PieData data = new PieData(labels, dataset);
        chart.setData(data);

        chart.setDescriptionTextSize(14);

        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(ContextCompat.getColor(chart.getContext(), R.color.colorWhite));
        //chart.setTransparentCircleColor(ContextCompat.getColor(chart.getContext(), R.color.colorPrimary));
        //chart.setTransparentCircleAlpha(110);
        //chart.setTransparentCircleRadius(61f);
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
        textView.setText(String.format("Total expenses: %.2f", total));

        //chart.invalidate();

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        if (mChartType.getLayoutResourceId() == R.layout.chart_pie) {

        }
        return mReport.getMonthCount();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mReport.getPageTitle(position);
    }

}
