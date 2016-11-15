package com.selcukcihan.android.expensetracer;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.selcukcihan.android.expensetracer.model.Category;
import com.selcukcihan.android.expensetracer.model.Transaction;
import com.selcukcihan.android.expensetracer.ui.CategoryObserver;
import com.selcukcihan.android.expensetracer.ui.view.CategoryView;
import com.selcukcihan.android.expensetracer.ui.CurrencyFormatInputFilter;
import com.selcukcihan.android.expensetracer.viewmodel.CategoryViewModel;
import com.selcukcihan.android.expensetracer.viewmodel.TransactionViewModel;

import java.util.Calendar;

public class TransactionActivity extends DrawerActivity implements CategoryObserver {
    public final static String EXTRA_CATEGORY_TYPE = "com.selcukcihan.android.expensetracer.TRANSACTION_EXTRA_CATEGORY_TYPE";
    public final static String EXTRA_CATEGORY_ID = "com.selcukcihan.android.expensetracer.TRANSACTION_EXTRA_CATEGORY_ID";

    public final static int CATEGORY_SELECTION_ACTIVITY_REQUEST_CODE = 1;

    private final CategoryViewModel mCategoryViewModel = new CategoryViewModel(this);
    private Transaction mTransaction = null;

    private Long mSelectedCategoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        init();
    }

    private void init() {
        ((EditText) findViewById(R.id.amount)).setFilters(new InputFilter[]{new CurrencyFormatInputFilter()});
        ((CategoryView) findViewById(R.id.category)).setObserver(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabSave);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String amount = ((EditText) findViewById(R.id.amount)).getText().toString();
                String note = ((EditText) findViewById(R.id.note)).getText().toString();
                DatePicker dp = (DatePicker) findViewById(R.id.date);
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, dp.getYear());
                cal.set(Calendar.MONTH, dp.getMonth());
                cal.set(Calendar.DAY_OF_MONTH, dp.getDayOfMonth());
                Transaction transaction = new Transaction(amount, cal.getTime(), mSelectedCategoryId, note);
                if (mTransaction != null) {
                    transaction.setId(mTransaction.getId());
                    (new TransactionViewModel(TransactionActivity.this)).updateTransaction(transaction);
                } else {
                    (new TransactionViewModel(TransactionActivity.this)).putTransaction(transaction);
                }
                finish();
                Intent i = new Intent(TransactionActivity.this, ListActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        if (intent.hasExtra(ListActivity.EXTRA_TRANSACTION)) {
            mTransaction = intent.getParcelableExtra(ListActivity.EXTRA_TRANSACTION);
            mSelectedCategoryId = mTransaction.getCategory().getId();
            setTitle("Edit Transaction");

            ((EditText) findViewById(R.id.amount)).setText(mTransaction.getAmountText());
            ((EditText) findViewById(R.id.note)).setText(mTransaction.getNote());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(mTransaction.getDate());
            ((DatePicker) findViewById(R.id.date)).updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        } else {
            setTitle("New Transaction");
        }
        resetCategories();
    }

    public void onRadioButtonClicked(View view) {
        ((CategoryView) findViewById(R.id.category)).reset(view.getId() == R.id.btnExpenseType ? Category.CategoryType.EXPENSE : Category.CategoryType.INCOME, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CATEGORY_SELECTION_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
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
        ((RadioButton) findViewById(R.id.btnExpenseType)).setChecked(categoryType == Category.CategoryType.EXPENSE);
        ((RadioButton) findViewById(R.id.btnIncomeType)).setChecked(categoryType == Category.CategoryType.INCOME);
        ((CategoryView) findViewById(R.id.category)).reset(categoryType, selectedCategory);
    }

    @Override
    public void onCategoryChanged(Category category) {
        ((TextView) findViewById(R.id.categoryLabel)).setText(category.getName());
        findViewById(R.id.categoryTypeStrip).setBackgroundColor(ContextCompat.getColor(this,
                ((RadioButton) findViewById(R.id.btnExpenseType)).isChecked() ? R.color.colorExpense : R.color.colorIncome));

        mSelectedCategoryId = category.getId();
    }
}
