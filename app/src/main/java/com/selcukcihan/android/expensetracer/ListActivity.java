package com.selcukcihan.android.expensetracer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.InputFilter;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;

import com.selcukcihan.android.expensetracer.model.Category;
import com.selcukcihan.android.expensetracer.model.Transaction;
import com.selcukcihan.android.expensetracer.ui.CategoryAdapter;
import com.selcukcihan.android.expensetracer.ui.CategoryView;
import com.selcukcihan.android.expensetracer.ui.CurrencyFormatInputFilter;
import com.selcukcihan.android.expensetracer.ui.TransactionListAdapter;
import com.selcukcihan.android.expensetracer.viewmodel.CategoryViewModel;
import com.selcukcihan.android.expensetracer.viewmodel.TransactionViewModel;

import java.util.Calendar;

/**
 * Created by SELCUKCI on 2.11.2016.
 */

public class ListActivity extends DrawerActivity {
    public final static String EXTRA_TRANSACTION = "com.selcukcihan.android.expensetracer.LIST_EXTRA_TRANSACTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        init();
    }

    private void init() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabNew);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(ListActivity.this, TransactionActivity.class);
                startActivity(i);
            }
        });

        registerForContextMenu(findViewById(R.id.list_transactions));
    }

    @Override
    protected void onStart() {
        super.onStart();

        TransactionListAdapter adapter = new TransactionListAdapter(
                this, R.layout.transaction_list_item, new TransactionViewModel(this).getTransactions(), 0 );

        ((ListView)findViewById(R.id.list_transactions)).setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.list_transactions) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_grid, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        View view = info.targetView;
        switch(item.getItemId()) {
            case R.id.edit:
                Intent i = new Intent(ListActivity.this, TransactionActivity.class);
                i.putExtra(ListActivity.EXTRA_TRANSACTION, ((Transaction)view.getTag()));
                startActivity(i);
                return true;
            case R.id.delete:
                new TransactionViewModel(this).deleteTransaction((Transaction)view.getTag());
                ((TransactionListAdapter)((ListView)findViewById(R.id.list_transactions)).getAdapter()).swapCursor(new TransactionViewModel(this).getTransactions());
                ((TransactionListAdapter)((ListView)findViewById(R.id.list_transactions)).getAdapter()).notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
