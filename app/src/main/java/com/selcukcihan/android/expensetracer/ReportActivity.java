package com.selcukcihan.android.expensetracer;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.selcukcihan.android.expensetracer.model.ChartType;
import com.selcukcihan.android.expensetracer.model.Transaction;
import com.selcukcihan.android.expensetracer.ui.ChartTypeFragment;
import com.selcukcihan.android.expensetracer.ui.ReportPagerAdapter;
import com.selcukcihan.android.expensetracer.ui.TransactionListAdapter;
import com.selcukcihan.android.expensetracer.viewmodel.TransactionViewModel;

/**
 * Created by SELCUKCI on 2.11.2016.
 */

public class ReportActivity extends DrawerActivity implements ChartTypeFragment.ChartTypeObserver {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);


        init();
    }

    private void init() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new ReportPagerAdapter(this));
        ((ReportPagerAdapter)viewPager.getAdapter()).setChartType(ChartType.CHART_TYPES[0]);
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
                ChartTypeFragment.newDialog(this).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onChartTypeChanged(ChartType chartType) {
        Toast.makeText(this, "NABER\t" + chartType.getLayoutResourceId() + "\t" + chartType.toString(), Toast.LENGTH_SHORT).show();

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        ((ReportPagerAdapter)viewPager.getAdapter()).setChartType(chartType);
        //viewPager.setAdapter(new ReportPagerAdapter(this));
    }
}
