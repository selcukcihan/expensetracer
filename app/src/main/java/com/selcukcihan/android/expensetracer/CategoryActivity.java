package com.selcukcihan.android.expensetracer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.selcukcihan.android.expensetracer.ui.CategoryAdapter;
import com.selcukcihan.android.expensetracer.ui.CategoryIconAdapter;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        boolean expense = intent.getBooleanExtra(CategorySelectionActivity.EXTRA_CATEGORY_TYPE, true);
        ((RadioButton)findViewById(R.id.btnExpenseType)).setChecked(expense);
        ((RadioButton)findViewById(R.id.btnIncomeType)).setChecked(!expense);
    }

    private void init() {
        GridView gridView = (GridView)findViewById(R.id.gridIcon);
        gridView.setAdapter(new CategoryIconAdapter(this));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(CategoryActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onRadioButtonClicked(View view) {
    }

}
