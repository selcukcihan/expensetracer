package com.selcukcihan.android.expensetracer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.selcukcihan.android.expensetracer.ui.CategoryView;
import com.selcukcihan.android.expensetracer.ui.CurrencyFormatInputFilter;

public class TransactionActivity extends AppCompatActivity {
    public final static String EXTRA_CATEGORY_TYPE = "com.selcukcihan.android.expensetracer.TRANSACTION_EXTRA_CATEGORY_TYPE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ((EditText)findViewById(R.id.amount)).setFilters(new InputFilter[] {new CurrencyFormatInputFilter()});
    }

    @Override
    protected void onStart() {
        super.onStart();
        ((RadioButton)findViewById(R.id.btnExpenseType)).setChecked(true);
        onRadioButtonClicked(findViewById(R.id.btnExpenseType));
    }

    public void onRadioButtonClicked(View view) {
        ((CategoryView)findViewById(R.id.category)).setExpense(view.getId() == R.id.btnExpenseType);
    }
}
