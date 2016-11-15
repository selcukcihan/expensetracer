package com.selcukcihan.android.expensetracer.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.selcukcihan.android.expensetracer.db.ExpenseContract;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by SELCUKCI on 20.10.2016.
 */

public class Transaction implements Parcelable {
    private static final SimpleDateFormat YEAR_FORMAT = new SimpleDateFormat("yyyy");
    private static final SimpleDateFormat YEAR_MONTH_FORMAT = new SimpleDateFormat("yyyy-MM");
    private static final SimpleDateFormat DAY_MONTH_FORMAT = new SimpleDateFormat("d MMM, E");
    private static final SimpleDateFormat DAY_MONTH_YEAR_FORMAT = new SimpleDateFormat("d MMM y");

    private Long mId;
    private Long mAmount;
    private Long mDate;
    private Category mCategory;
    private Long mCategoryId;
    private String mNote;

    public Transaction(Cursor cursor) {
        mId = cursor.getLong(cursor.getColumnIndex(ExpenseContract.TransactionTable._ID));
        mAmount = cursor.getLong(cursor.getColumnIndex(ExpenseContract.TransactionTable.COLUMN_NAME_AMOUNT));
        mNote = cursor.getString(cursor.getColumnIndex(ExpenseContract.TransactionTable.COLUMN_NAME_NOTE));
        mDate = cursor.getLong(cursor.getColumnIndex(ExpenseContract.TransactionTable.COLUMN_NAME_DATE));
        mCategory = new Category(cursor, true);
    }

    public Transaction(String amount, Date date, Long categoryId, String note) {
        mAmount = (long)(Float.parseFloat(amount.replaceAll(",",".")) * 100);
        mDate = date.getTime();
        mCategoryId = categoryId;
        mNote = note;
    }

    public void setId(long id) {
        mId = id;
    }

    public long getId() {
        if (mId == null) {
            throw new IllegalStateException("Transaction id not set.");
        }
        return mId;
    }

    public Category getCategory() {
        return mCategory;
    }

    public String getAmountText() {
        return String.format("%.2f", mAmount / 100.0f);
    }

    public Date getDate() {
        return new Date(mDate);
    }

    public String getNote() {
        return mNote;
    }

    public Long getAmountRaw() {
        return mAmount;
    }

    public Float getAmountReal() { return mAmount / 100.0f; }

    public Long getDateRaw() {
        return mDate;
    }

    public String getYearMonth() {
        return YEAR_MONTH_FORMAT.format(getDate());
    }

    public String getYear() {
        return YEAR_FORMAT.format(getDate());
    }

    public Long getCategoryId() {
        return (mCategory != null ? mCategory.getId() : mCategoryId);
    }

    public String getDateText() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDate());
        if (calendar.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR)) { // do not display year for the current year
            return DAY_MONTH_FORMAT.format(getDate());
        }
        return DAY_MONTH_YEAR_FORMAT.format(getDate());
    }

    // Parcelling part
    public Transaction(Parcel in){
        this.mId = in.readLong();
        this.mAmount = in.readLong();
        this.mDate = in.readLong();
        this.mCategory = in.readParcelable(Category.class.getClassLoader());
        this.mCategoryId = in.readLong();
        this.mNote = in.readString();
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mId);
        dest.writeLong(this.mAmount);
        dest.writeLong(this.mDate);
        dest.writeParcelable(this.mCategory, flags);
        dest.writeLong(this.mCategory.getId());
        dest.writeString(this.mNote);
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Transaction createFromParcel(Parcel in) {
            return new Transaction(in);
        }

        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };
}
