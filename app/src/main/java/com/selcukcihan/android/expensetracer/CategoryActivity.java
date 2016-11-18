package com.selcukcihan.android.expensetracer;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.selcukcihan.android.expensetracer.model.Category;
import com.selcukcihan.android.expensetracer.viewmodel.CategoryIconAdapter;
import com.selcukcihan.android.expensetracer.viewmodel.CategoryViewModel;
import com.selcukcihan.android.expensetracer.viewmodel.IInputObserver;

public class CategoryActivity extends DrawerActivity implements IInputObserver {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
    }

    @Override
    public void inputChanged() {
        if ((!((CategoryIconAdapter)((GridView)findViewById(R.id.gridIcon)).getAdapter()).selected())
            || (((EditText)findViewById(R.id.categoryName)).getText().toString().isEmpty())) {
            ((FloatingActionButton) findViewById(R.id.fabSave)).setEnabled(false);
            ((FloatingActionButton) findViewById(R.id.fabSave)).setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorDivider));
        } else {
            ((FloatingActionButton) findViewById(R.id.fabSave)).setEnabled(true);
            ((FloatingActionButton) findViewById(R.id.fabSave)).setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorAccent));
        }
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
    }

    private void init() {
        ((FloatingActionButton) findViewById(R.id.fabSave)).setEnabled(false);

        GridView gridView = (GridView) findViewById(R.id.gridIcon);
        gridView.setAdapter(new CategoryIconAdapter(this));

        ((EditText) findViewById(R.id.categoryName)).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                ((GridView) findViewById(R.id.gridIcon)).setVisibility(hasFocus ? View.GONE : View.VISIBLE);
            }
        });

        initFAB();

        ((EditText)findViewById(R.id.categoryName)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                CategoryActivity.this.inputChanged();
            }
        });
    }

    private void displaySnack(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.activity_category), message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    private void initFAB() {
        FloatingActionButton fabNew = (FloatingActionButton) findViewById(R.id.fabSave);
        fabNew.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = ((EditText)findViewById(R.id.categoryName)).getText().toString();
                int iconResourceId = ((CategoryIconAdapter)((GridView)findViewById(R.id.gridIcon)).getAdapter()).getSelectedIcon();
                Category.CategoryType categoryType = ((RadioButton)findViewById(R.id.btnExpenseType)).isChecked() ? Category.CategoryType.EXPENSE : Category.CategoryType.INCOME;
                Category category;
                if (getIntent().hasExtra(CategorySelectionActivity.EXTRA_CATEGORY_TYPE)) { // adding new category
                    category = new Category(name, getResources().getResourceEntryName(iconResourceId), categoryType);
                    Long id = new CategoryViewModel(CategoryActivity.this).putCategory(category);
                    if (id == null) {
                        displaySnack("Choose another name for the category.");
                        return;
                    }
                    category.setId(id);
                } else { // editing category
                    long id = getIntent().getLongExtra(CategorySelectionActivity.EXTRA_CATEGORY_ID, -1);
                    category = new Category(name, getResources().getResourceEntryName(iconResourceId), categoryType, id);
                    if (!(new CategoryViewModel(CategoryActivity.this).updateCategory(category))) {
                        displaySnack("Choose another name for the category.");
                        return;
                    }
                }

                Intent returnIntent = new Intent();
                returnIntent.putExtra(TransactionActivity.EXTRA_CATEGORY_ID, category.getId());
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }

    public void onRadioButtonClicked(View view) {
    }
}
