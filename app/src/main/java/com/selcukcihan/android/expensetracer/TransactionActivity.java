package com.selcukcihan.android.expensetracer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;

public class TransactionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ((EditText)findViewById(R.id.amount)).setFilters(new InputFilter[] {new CurrencyFormatInputFilter()});
    }

    public void onRadioButtonClicked(View view) {
    }
}
