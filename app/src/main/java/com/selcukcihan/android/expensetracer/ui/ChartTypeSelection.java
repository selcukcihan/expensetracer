package com.selcukcihan.android.expensetracer.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.selcukcihan.android.expensetracer.R;
import com.selcukcihan.android.expensetracer.ReportActivity;
import com.selcukcihan.android.expensetracer.model.ChartType;

/**
 * Created by SELCUKCI on 8.11.2016.
 */

public class ChartTypeSelection {
    public interface ChartTypeObserver {
        void onChartTypeChanged(ChartType chartType);
    }

    public static void showDialog(final ReportActivity chartTypeListener, ChartType chartType){
        final Dialog dialog = new Dialog(chartTypeListener);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.chart_type_dialog);

        RadioGroup group = (RadioGroup)dialog.findViewById(R.id.radio_group);
        for (final ChartType c : ChartType.CHART_TYPES) {
            RadioButton button = new RadioButton(chartTypeListener);
            button.setText(c.toString());
            group.addView(button);
            Drawable drawable = ContextCompat.getDrawable(chartTypeListener, c.getIconResourceId());
            drawable.setColorFilter(ContextCompat.getColor(chartTypeListener, R.color.colorPrimaryDark), PorterDuff.Mode.MULTIPLY);
            button.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
            if (c.toString().compareTo(chartType.toString()) == 0) {
                button.setChecked(true);
            }
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chartTypeListener.onChartTypeChanged(c);
                    dialog.dismiss();
                }
            });
        }

        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }
}
