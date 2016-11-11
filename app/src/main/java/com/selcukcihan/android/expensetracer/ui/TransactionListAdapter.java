package com.selcukcihan.android.expensetracer.ui;

import android.content.Context;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.selcukcihan.android.expensetracer.R;
import com.selcukcihan.android.expensetracer.db.ExpenseContract;
import com.selcukcihan.android.expensetracer.model.Category;
import com.selcukcihan.android.expensetracer.model.Transaction;

import java.text.SimpleDateFormat;

import static android.R.attr.name;

/**
 * Created by SELCUKCI on 2.11.2016.
 */

public class TransactionListAdapter extends ResourceCursorAdapter {

    private final Context mContext;

    public TransactionListAdapter(Context context, int layout, Cursor cursor, int flags) {
        super(context, layout, cursor, flags);
        mContext = context;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Transaction transaction = new Transaction(cursor);

        ImageView icon = (ImageView) view.findViewById(R.id.transaction_list_item_icon);
        icon.setImageResource(transaction.getCategory().getResourceIdInteger(mContext));
        icon.setColorFilter(ContextCompat.getColor(mContext,
                transaction.getCategory().getCategoryType() == Category.CategoryType.EXPENSE ? R.color.colorExpense : R.color.colorIncome),
                PorterDuff.Mode.MULTIPLY);

        TextView amount = (TextView) view.findViewById(R.id.transaction_list_item_amount);
        amount.setText(transaction.getAmountText());

        TextView date = (TextView) view.findViewById(R.id.transaction_list_item_date);
        date.setText(transaction.getDateText());

        TextView big = (TextView) view.findViewById(R.id.transaction_list_item_text_big);
        if (!transaction.getNote().isEmpty()) {
            big.setText(transaction.getNote());
            TextView small = (TextView) view.findViewById(R.id.transaction_list_item_text_small);
            small.setText("{" + transaction.getCategory().getName() + "}");
        } else { // no notes, display only the category name
            view.findViewById(R.id.transaction_list_item_text_small).setVisibility(View.GONE);
            big.setText(transaction.getCategory().getName());
        }

        view.setTag(transaction);
    }
}
