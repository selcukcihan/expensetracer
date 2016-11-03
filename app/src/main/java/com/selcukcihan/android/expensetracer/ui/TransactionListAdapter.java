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
        icon.setImageResource(transaction.getCategory().getResourceId());
        icon.setColorFilter(ContextCompat.getColor(mContext,
                transaction.getCategory().getCategoryType() == Category.CategoryType.EXPENSE ? R.color.colorExpense : R.color.colorIncome),
                PorterDuff.Mode.MULTIPLY);

        TextView amount = (TextView) view.findViewById(R.id.transaction_list_item_amount);
        amount.setText(transaction.getAmountText());

        TextView note = (TextView) view.findViewById(R.id.transaction_list_item_note);
        note.setText(transaction.getNote());
    }
}
