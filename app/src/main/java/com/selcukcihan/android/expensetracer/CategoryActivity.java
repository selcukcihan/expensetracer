package com.selcukcihan.android.expensetracer;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;

import com.selcukcihan.android.expensetracer.model.Category;
import com.selcukcihan.android.expensetracer.viewmodel.CategoryIconAdapter;
import com.selcukcihan.android.expensetracer.viewmodel.CategoryViewModel;

public class CategoryActivity extends DrawerActivity {
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
        if (intent.hasExtra(CategorySelectionActivity.EXTRA_CATEGORY_TYPE)) {
            boolean expense = intent.getBooleanExtra(CategorySelectionActivity.EXTRA_CATEGORY_TYPE, true);
            ((RadioButton)findViewById(R.id.btnExpenseType)).setChecked(expense);
            ((RadioButton)findViewById(R.id.btnIncomeType)).setChecked(!expense);

            setTitle("New Category");
        } else {
            setTitle("Edit Category");

            long id = intent.getLongExtra(CategorySelectionActivity.EXTRA_CATEGORY_ID, -1);
            CategoryViewModel viewModel = new CategoryViewModel(this);
            Category category = viewModel.getCategoryById(id);

            ((RadioButton)findViewById(R.id.btnExpenseType)).setChecked(category.getCategoryType() == Category.CategoryType.EXPENSE);
            ((RadioButton)findViewById(R.id.btnIncomeType)).setChecked(category.getCategoryType() == Category.CategoryType.INCOME);

            ((CategoryIconAdapter)((GridView)findViewById(R.id.gridIcon)).getAdapter()).setSelectedIcon(category.getResourceIdInteger(this));
            ((EditText)findViewById(R.id.categoryName)).setText(category.getName());
        }

        findViewById(R.id.categoryTypeStrip).setBackgroundColor(ContextCompat.getColor(this,
                ((RadioButton)findViewById(R.id.btnExpenseType)).isChecked() ? R.color.colorExpense : R.color.colorIncome));
    }

    private void init() {
        GridView gridView = (GridView)findViewById(R.id.gridIcon);
        gridView.setAdapter(new CategoryIconAdapter(this));

        ((EditText)findViewById(R.id.categoryName)).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                ((GridView)findViewById(R.id.gridIcon)).setVisibility(hasFocus ? View.GONE : View.VISIBLE);
            }
        });

        FloatingActionButton fabNew = (FloatingActionButton) findViewById(R.id.fabSave);
        fabNew.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = ((EditText)findViewById(R.id.categoryName)).getText().toString();
                int iconResourceId = ((CategoryIconAdapter)((GridView)findViewById(R.id.gridIcon)).getAdapter()).getSelectedIcon();
                Category.CategoryType categoryType = ((RadioButton)findViewById(R.id.btnExpenseType)).isChecked() ? Category.CategoryType.EXPENSE : Category.CategoryType.INCOME;
                Category category;
                if (getIntent().hasExtra(CategorySelectionActivity.EXTRA_CATEGORY_TYPE)) { // adding new category
                    category = new Category(name, getResources().getResourceEntryName(iconResourceId), categoryType);
                    long id = new CategoryViewModel(CategoryActivity.this).putCategory(category);
                    category.setId(id);
                } else { // editing category
                    long id = getIntent().getLongExtra(CategorySelectionActivity.EXTRA_CATEGORY_ID, -1);
                    category = new Category(name, getResources().getResourceEntryName(iconResourceId), categoryType, id);
                    new CategoryViewModel(CategoryActivity.this).updateCategory(category);
                }

                Intent returnIntent = new Intent();
                returnIntent.putExtra(TransactionActivity.EXTRA_CATEGORY_ID, category.getId());
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        findViewById(R.id.categoryTypeStrip).setBackgroundColor(ContextCompat.getColor(this,
                ((RadioButton)findViewById(R.id.btnExpenseType)).isChecked() ? R.color.colorExpense : R.color.colorIncome));
    }
}
