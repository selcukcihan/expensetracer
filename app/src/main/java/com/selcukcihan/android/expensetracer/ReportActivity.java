package com.selcukcihan.android.expensetracer;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.selcukcihan.android.expensetracer.model.ChartType;
import com.selcukcihan.android.expensetracer.model.TransactionReport;
import com.selcukcihan.android.expensetracer.ui.ChartTypeSelection;
import com.selcukcihan.android.expensetracer.viewmodel.ReportPagerAdapter;
import com.selcukcihan.android.expensetracer.viewmodel.TransactionViewModel;

/**
 * Created by SELCUKCI on 2.11.2016.
 */

public class ReportActivity extends DrawerActivity implements ChartTypeSelection.ChartTypeObserver {
    private static final String KEY_SELECTED_CHART_TYPE = "SELECTED_CHART_TYPE";

    private TransactionReport mReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        ChartType selectedChartType = ChartType.CHART_TYPES[2];
        if (savedInstanceState != null) {
            CharSequence savedChart = savedInstanceState.getCharSequence(KEY_SELECTED_CHART_TYPE);
            for (ChartType c : ChartType.CHART_TYPES) {
                if (c.toString().compareTo(savedChart.toString()) == 0) {
                    selectedChartType = c;
                }
            }
        }

        init(selectedChartType);
    }

    private void init(ChartType selectedChartType) {
        mReport = (new TransactionViewModel(this)).getTransactionReport();
        handleChartTypeChange(selectedChartType, true);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_charts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_chart_type:
                ChartTypeSelection.showDialog(this, ((ReportPagerAdapter)(((ViewPager)findViewById(R.id.viewpager)).getAdapter())).getChartType());
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void handleChartTypeChange(ChartType chartType, boolean gotoLastPage) {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new ReportPagerAdapter(this, mReport, chartType));
        if (gotoLastPage) {
            viewPager.setCurrentItem(((ReportPagerAdapter)(((ViewPager)findViewById(R.id.viewpager)).getAdapter())).getCount() - 1);
        }
    }

    @Override
    public void onChartTypeChanged(ChartType chartType) {
        handleChartTypeChange(chartType, false);
    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence(KEY_SELECTED_CHART_TYPE, ((ReportPagerAdapter)(((ViewPager)findViewById(R.id.viewpager)).getAdapter())).getChartType().toString());
    }
}
