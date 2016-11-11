package com.selcukcihan.android.expensetracer.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.selcukcihan.android.expensetracer.R;
import com.selcukcihan.android.expensetracer.ReportActivity;
import com.selcukcihan.android.expensetracer.model.ChartType;

/**
 * Created by SELCUKCI on 8.11.2016.
 */

public class ChartTypeFragment extends DialogFragment {
    public interface ChartTypeObserver {
        void onChartTypeChanged(ChartType chartType);
    }

    public static AlertDialog newDialog(final ReportActivity chartTypeListener) {
        final int selectedItemIndex;
        AlertDialog.Builder builder = new AlertDialog.Builder(chartTypeListener);
        // Set the dialog title
        builder.setTitle("Chart type:")
                .setSingleChoiceItems(getAdapter(chartTypeListener), -1,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                chartTypeListener.onChartTypeChanged(ChartType.CHART_TYPES[which]);
                                dialog.dismiss();
                            }
                        });
        return builder.create();
    }
/*
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final int selectedItemIndex;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Set the dialog title
        builder.setTitle("Chart type:")
                .setSingleChoiceItems(getAdapter(), -1,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    ChartTypeFragment.this.dismiss();
                                } else {
                                    ChartTypeFragment.this.dismiss();
                                }
                            }
                        });
        return builder.create();
    }*/


    private static ListAdapter getAdapter(Context context) {
        return new ArrayAdapter<ChartType>(context, android.R.layout.select_dialog_item, android.R.id.text1, ChartType.CHART_TYPES){
            public View getView(int position, View convertView, ViewGroup parent) {
                //Use super class to create the View
                View v = super.getView(position, convertView, parent);
                TextView tv = (TextView)v.findViewById(android.R.id.text1);

                //Put the image on the TextView
                tv.setCompoundDrawablesWithIntrinsicBounds(ChartType.CHART_TYPES[position].getIconResourceId(), 0, 0, 0);

                //Add margin between image and text (support various screen densities)
                int dp5 = (int) (5 * getContext().getResources().getDisplayMetrics().density + 0.5f);
                tv.setCompoundDrawablePadding(dp5);

                return v;
            }
        };
    }
}
