package com.selcukcihan.android.expensetracer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.InputFilter;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import com.selcukcihan.android.expensetracer.model.Transaction;
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
    }

    @Override
    protected void onStart() {
        super.onStart();

        TransactionListAdapter adapter = new TransactionListAdapter(
                this, R.layout.transaction_list_item, new TransactionViewModel(this).getTransactions(), 0 );

        ((ListView)findViewById(R.id.list_transactions)).setAdapter(adapter);
    }
}
