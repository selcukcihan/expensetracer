package com.selcukcihan.android.expensetracer.ui;

import android.os.Message;
import android.text.InputFilter;
import android.text.Spanned;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by SELCUKCI on 19.10.2016.
 * http://stackoverflow.com/a/13716269/4281182
 */

public class CurrencyFormatInputFilter implements InputFilter {

    //private Pattern mPattern = Pattern.compile("(0|[1-9]+[0-9]*)?(\\.[0-9]{0,2})?");
    //private Pattern mPattern = Pattern.compile("(0|[1-9]+[0-9]*)?(\\.[0-9]{0,2})?");
    private static final String PATTERN_STRING = "(0|[1-9]+[0-9]*)?(\\{0}[0-9]{0,2})?";
    private final Pattern mPattern; //= Pattern.compile("(0|[1-9]+[0-9]*)?(\\.[0-9]{0,2})?");
    private final Pattern mDotPattern; //= Pattern.compile("(0|[1-9]+[0-9]*)?(\\.[0-9]{0,2})?");
    private final String mDecSeparator;

    public CurrencyFormatInputFilter() {
        NumberFormat nf = NumberFormat.getInstance();
        char decSeparator = '.';
        if(nf instanceof DecimalFormat) {
            DecimalFormatSymbols sym = ((DecimalFormat)nf).getDecimalFormatSymbols();
            decSeparator = sym.getDecimalSeparator();
        }
        mDecSeparator = "" + decSeparator;
        mPattern = Pattern.compile(PATTERN_STRING.replace("{0}", mDecSeparator));
        mDotPattern = Pattern.compile(PATTERN_STRING.replace("{0}", "."));
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        /*
        This method is called when the buffer is going to replace the range dstart … dend of dest with the new text from the range start … end of source.
        Return the CharSequence that you would like to have placed there instead, including an empty string if appropriate, or null to accept the original replacement.
        Be careful to not to reject 0-length replacements, as this is what happens when you delete text.
        Also beware that you should not attempt to make any changes to dest from this method; you may only examine it for context.
        Note: If source is an instance of Spanned or Spannable, the span objects in the source should be copied into the filtered result (i.e. the non-null return value).
        copySpansFrom(Spanned, int, int, Class, Spannable, int) can be used for convenience.
         */

        String result = dest.subSequence(0, dstart) + source.toString() + dest.subSequence(dend, dest.length());
        Matcher matcher = mPattern.matcher(result);
        if (!matcher.matches()) {
            if (mDecSeparator.charAt(0) == ',') {
                if (mDotPattern.matcher(result).matches()) {
                    return source.toString().replace(".", ",");
                }
            }
            return dest.subSequence(dstart, dend);
        }
        return null;
    }
}