package com.selcukcihan.android.expensetracer.model;

import java.util.Arrays;
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
        private Float mIncome;
        private Float mExpense;

        public TransactionMonthly(String month) {
            Month = month;
            Items = new LinkedList<>();
            mIncome = 0f;
            mExpense = 0f;
        }

        public boolean belongsToYear(String year) {
            return Month.contains(year);
        }

        public void add(Transaction t) {
            boolean found = false;
            for (TransactionMonthlyItem item : Items) {
                if (item.Category.getId() == t.getCategoryId()) {
                    found = true;
                    item.add(t.getAmountReal());
                    break;
                }
            }
            if (!found) {
                Items.add(new TransactionMonthlyItem(t.getCategory(), t.getAmountReal()));
            }
            if (t.getCategory().getCategoryType() == Category.CategoryType.EXPENSE) {
                mExpense += t.getAmountReal();
            } else {
                mIncome += t.getAmountReal();
            }
        }

        public Float getExpense() {
            return mExpense;
        }

        public Float getIncome() {
            return mIncome;
        }
    }

    private final List<Transaction> mTransactions;
    private final TransactionMonthly[] mArrayOfMonths;
    private static final int MONTHS_IN_A_YEAR = 12;
    private final TransactionMonthly[][] mArrayOfYears;

    public TransactionReport(List<Transaction> transactions) {
        mTransactions = transactions;

        HashMap<String, TransactionMonthly> map = new HashMap<>();
        HashMap<String, String> years = new HashMap();
        for (Transaction t : transactions) {
            TransactionMonthly m = null;
            String yearMonth = t.getYearMonth();
            if (!map.containsKey(yearMonth)) {
                m = new TransactionMonthly(yearMonth);
                map.put(m.Month, m);
            } else {
                m = map.get(yearMonth);
            }
            m.add(t);
            years.put(t.getYear(), t.getYear());
        }

        mArrayOfMonths = new TransactionMonthly[map.size()];
        String [] keys = map.keySet().toArray(new String[1]);
        Arrays.sort(keys);
        for (int i = 0; i < keys.length; i++) {
            mArrayOfMonths[i] = map.get(keys[i]);
        }

        keys = years.keySet().toArray(new String[1]);
        Arrays.sort(keys);
        mArrayOfYears = new TransactionMonthly[keys.length][];
        for (int i = 0; i < keys.length; i++) {
            int begin = -1, end = -1; // indices to months that mark the beginning(inclusive) and the ending (exclusive) of the year keys[i]
            for (int j = 0; j < mArrayOfMonths.length; j++) {
                if (mArrayOfMonths[j].belongsToYear(keys[i]) && begin == -1) {
                    begin = j;
                }
                if (!mArrayOfMonths[j].belongsToYear(keys[i]) && begin != -1) {
                    end = j;
                    break;
                }
            }
            end = (end == -1 ? mArrayOfMonths.length : end);
            mArrayOfYears[i] = new TransactionMonthly[MONTHS_IN_A_YEAR];
            String year = "";
            for (int j = begin; j < end; j++) {
                mArrayOfYears[i][j - begin] = mArrayOfMonths[j];
                year = mArrayOfMonths[j].Month.substring(0, 4);
            }
            for (int j = 0; j < mArrayOfYears[i].length; j++) {
                if (mArrayOfYears[i][j] == null) {
                    mArrayOfYears[i][j] = new TransactionMonthly(year + "-" + String.format("%2d", j + 1));
                }
            }
        }
    }

    public int getMonthCount() {
        return mArrayOfMonths.length;
    }

    public int getYearCount() {
        return mArrayOfYears.length;
    }

    public CharSequence getMonthlyPageTitle(int position) {
        return mArrayOfMonths[position].Month;
    }

    public CharSequence getYearlyPageTitle(int position) {
        return mArrayOfYears[position][0].Month.substring(0, 4);
    }

    public TransactionMonthly getMonth(int position) {
        return mArrayOfMonths[position];
    }

    public TransactionMonthly[] getYear(int position) {
        return mArrayOfYears[position];
    }
}
