package com.selcukcihan.android.expensetracer.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by SELCUKCI on 9.11.2016.
 */

public class TransactionReport {

    public class TransactionMonthlyItem {
        public final Category Category;
        private Float mValue;

        public TransactionMonthlyItem(Category category, Float initialValue) {
            Category = category;
            mValue = initialValue;
        }

        public void add(Float value) {
            mValue += value;
        }

        public Float getValue() {
            return mValue;
        }
    }
    public class TransactionMonthly {
        public final String Month;
        public final List<TransactionMonthlyItem> Items;

        public TransactionMonthly(String month) {
            Month = month;
            Items = new LinkedList<>();
        }
    }

    private final List<Transaction> mTransactions;
    private final TransactionMonthly[] mArrayOfMonths;

    public TransactionReport(List<Transaction> transactions) {
        mTransactions = transactions;

        HashMap<String, TransactionMonthly> map = new HashMap<>();
        for (Transaction t : transactions) {
            TransactionMonthly m = null;
            String yearMonth = t.getYearMonth();
            if (!map.containsKey(yearMonth)) {
                m = new TransactionMonthly(yearMonth);
                map.put(m.Month, m);
            } else {
                m = map.get(yearMonth);
            }
            boolean found = false;
            for (TransactionMonthlyItem item : m.Items) {
                if (item.Category.getId() == t.getCategoryId()) {
                    found = true;
                    item.add(t.getAmountReal());
                    break;
                }
            }
            if (!found) {
                m.Items.add(new TransactionMonthlyItem(t.getCategory(), t.getAmountReal()));
            }
        }

        mArrayOfMonths = new TransactionMonthly[map.size()];
        int i = 0;
        for (TransactionMonthly m : map.values()) {
            mArrayOfMonths[i++] = m;
        }
    }

    public int getMonthCount() {
        return mArrayOfMonths.length;
    }

    public CharSequence getPageTitle(int position) {
        return mArrayOfMonths[position].Month;
    }

    public TransactionMonthly getMonth(int position) {
        return mArrayOfMonths[position];
    }
}
