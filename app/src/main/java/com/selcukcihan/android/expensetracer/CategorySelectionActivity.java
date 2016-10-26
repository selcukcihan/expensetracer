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

public class CategorySelectionActivity extends AppCompatActivity {
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
        GridView gridView = (GridView)findViewById(R.id.gridCategory);
        gridView.setAdapter(new CategoryAdapter(this));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(CategorySelectionActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
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
        boolean expense = intent.getBooleanExtra(TransactionActivity.EXTRA_CATEGORY_TYPE, true);
        ((RadioButton)findViewById(R.id.btnExpenseType)).setChecked(expense);
        ((RadioButton)findViewById(R.id.btnIncomeType)).setChecked(!expense);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    public void onRadioButtonClicked(View view) {
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
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
