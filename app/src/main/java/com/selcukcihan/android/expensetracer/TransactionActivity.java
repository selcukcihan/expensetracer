package com.selcukcihan.android.expensetracer;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.selcukcihan.android.expensetracer.model.Category;
import com.selcukcihan.android.expensetracer.ui.CategoryObserver;
import com.selcukcihan.android.expensetracer.ui.CategoryView;
import com.selcukcihan.android.expensetracer.ui.CurrencyFormatInputFilter;
import com.selcukcihan.android.expensetracer.viewmodel.CategoryViewModel;

public class TransactionActivity extends AppCompatActivity implements CategoryObserver {
    public final static String EXTRA_CATEGORY_TYPE = "com.selcukcihan.android.expensetracer.TRANSACTION_EXTRA_CATEGORY_TYPE";
    public final static String EXTRA_CATEGORY_ID = "com.selcukcihan.android.expensetracer.TRANSACTION_EXTRA_CATEGORY_ID";

    public final static int CATEGORY_SELECTION_ACTIVITY_REQUEST_CODE = 1;

    private final CategoryViewModel mCategoryViewModel = new CategoryViewModel(this);

    private Long mSelectedCategoryId; // selection from CategorySelectionActivity. Sync with CategoryView's selection at the beginning only.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ((EditText)findViewById(R.id.amount)).setFilters(new InputFilter[] {new CurrencyFormatInputFilter()});
        ((CategoryView)findViewById(R.id.category)).setObserver(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        resetCategories();
    }

    public void onRadioButtonClicked(View view) {
        ((CategoryView)findViewById(R.id.category)).reset(view.getId() == R.id.btnExpenseType ? Category.CategoryType.EXPENSE : Category.CategoryType.INCOME, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CATEGORY_SELECTION_ACTIVITY_REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK){
                assert data.hasExtra(TransactionActivity.EXTRA_CATEGORY_ID) : "CategoryActivity should post back category id";
                mSelectedCategoryId = data.getLongExtra(TransactionActivity.EXTRA_CATEGORY_ID, -1);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    private void resetCategories() {
        Category selectedCategory = null;
        Category.CategoryType categoryType = Category.CategoryType.EXPENSE;
        if (mSelectedCategoryId != null) {
            selectedCategory = mCategoryViewModel.getCategoryById(mSelectedCategoryId);
            categoryType = selectedCategory.getCategoryType();
        }
        ((RadioButton)findViewById(R.id.btnExpenseType)).setChecked(categoryType == Category.CategoryType.EXPENSE);
        ((RadioButton)findViewById(R.id.btnIncomeType)).setChecked(categoryType == Category.CategoryType.INCOME);
        ((CategoryView)findViewById(R.id.category)).reset(categoryType, selectedCategory);
    }

    @Override
    public void onCategoryChanged(Category category) {
        ((TextView)findViewById(R.id.categoryLabel)).setText(category.getName());
        findViewById(R.id.categoryTypeStrip).setBackgroundColor(ContextCompat.getColor(this,
                ((RadioButton)findViewById(R.id.btnExpenseType)).isChecked() ? R.color.colorExpense : R.color.colorIncome));
    }
}
