package com.selcukcihan.android.expensetracer;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.selcukcihan.android.expensetracer.model.Category;
import com.selcukcihan.android.expensetracer.ui.CategoryAdapter;
import com.selcukcihan.android.expensetracer.viewmodel.CategoryViewModel;

public class CategorySelectionActivity extends DrawerActivity {
    public final static String EXTRA_CATEGORY_TYPE = "com.selcukcihan.android.expensetracer.CATEGORY_SELECTION_EXTRA_CATEGORY_TYPE";
    public final static String EXTRA_CATEGORY_ID = "com.selcukcihan.android.expensetracer.CATEGORY_SELECTION_EXTRA_CATEGORY_ID";
    private final static int CATEGORY_ACTIVITY_NEW_REQUEST_CODE = 1;
    private final static int CATEGORY_ACTIVITY_EDIT_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selection);

        init();
    }

    private void init() {
        setTitle("Categories");

        GridView gridView = (GridView)findViewById(R.id.gridCategory);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Category category = (Category)v.getTag();
                Intent returnIntent = new Intent();
                returnIntent.putExtra(TransactionActivity.EXTRA_CATEGORY_ID, category.getId());
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });

        registerForContextMenu(gridView);

        FloatingActionButton fabNew = (FloatingActionButton) findViewById(R.id.fabNew);
        fabNew.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(CategorySelectionActivity.this, CategoryActivity.class);
                i.putExtra(CategorySelectionActivity.EXTRA_CATEGORY_TYPE, ((RadioButton)findViewById(R.id.btnExpenseType)).isChecked());
                startActivityForResult(i, CATEGORY_ACTIVITY_NEW_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        Category.CategoryType categorType = Category.CategoryType.fromInt(intent.getIntExtra(TransactionActivity.EXTRA_CATEGORY_TYPE, -1));
        ((RadioButton)findViewById(R.id.btnExpenseType)).setChecked(categorType == Category.CategoryType.EXPENSE);
        ((RadioButton)findViewById(R.id.btnIncomeType)).setChecked(categorType == Category.CategoryType.INCOME);

        ((GridView)findViewById(R.id.gridCategory)).setAdapter(new CategoryAdapter(this, categorType));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CATEGORY_ACTIVITY_EDIT_REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK){
                assert data.hasExtra(TransactionActivity.EXTRA_CATEGORY_ID) : "CategoryActivity should post back category id";
                long categoryId = data.getLongExtra(TransactionActivity.EXTRA_CATEGORY_ID, -1);
                Intent returnIntent = new Intent();
                returnIntent.putExtra(TransactionActivity.EXTRA_CATEGORY_ID, categoryId);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    public void onRadioButtonClicked(View view) {
        ((GridView)findViewById(R.id.gridCategory)).setAdapter(new CategoryAdapter(this,
                ((RadioButton)findViewById(R.id.btnExpenseType)).isChecked() ? Category.CategoryType.EXPENSE : Category.CategoryType.INCOME));
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.gridCategory) {
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
                Intent i = new Intent(CategorySelectionActivity.this, CategoryActivity.class);
                i.putExtra(CategorySelectionActivity.EXTRA_CATEGORY_ID, ((Category)view.getTag()).getId());
                startActivityForResult(i, CATEGORY_ACTIVITY_EDIT_REQUEST_CODE);
                return true;
            case R.id.delete:
                new CategoryViewModel(this).deleteCategory((Category)view.getTag());
                ((CategoryAdapter)((GridView)findViewById(R.id.gridCategory)).getAdapter()).notifyDataSetChanged();
                ((GridView)findViewById(R.id.gridCategory)).setAdapter((CategoryAdapter)((GridView)findViewById(R.id.gridCategory)).getAdapter());
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
