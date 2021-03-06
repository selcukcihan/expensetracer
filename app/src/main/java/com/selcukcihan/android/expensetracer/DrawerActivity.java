package com.selcukcihan.android.expensetracer;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.selcukcihan.android.expensetracer.viewmodel.IInputObserver;

/**
 * Created by SELCUKCI on 31.10.2016.
 */

public abstract class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, IInputObserver {
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView mNavigationView;
    private DrawerLayout mFullView;
    private Toolbar mToolbar;
    private int mSelectedDrawerItem;

    public void inputChanged() {

    }

    @Override
    public void setContentView(@LayoutRes int layoutResID)
    {
        mFullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_drawer, null);
        FrameLayout activityContainer = (FrameLayout) mFullView.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        super.setContentView(mFullView);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorWhite));
        setSupportActionBar(mToolbar);
        initializeDrawer();
    }

    private void initializeDrawer() {
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        mDrawerToggle = new ActionBarDrawerToggle(this, mFullView, mToolbar, R.string.drawer_open_accessibility, R.string.drawer_close_accessibility);
        mFullView.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        mFullView.closeDrawer(GravityCompat.START);
        mSelectedDrawerItem = menuItem.getItemId();
        return onOptionsItemSelected(menuItem);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.drawer_new_transaction:
                startActivity(new Intent(this, TransactionActivity.class));
                return true;
            case R.id.drawer_categories:
                startActivity(new Intent(this, CategorySelectionActivity.class));
                return true;
            case R.id.drawer_list:
                //startActivity(new Intent(this, ListActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                startActivity(new Intent(this, ListActivity.class));
                return true;
            case R.id.drawer_report:
                startActivity(new Intent(this, ReportActivity.class));
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
}
