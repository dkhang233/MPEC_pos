package com.pos.app.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class FormatHelper {
    private static final DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
    
    public static String formatDecimalNumber(Double value) {
        return decimalFormat.format(value);
    }
}
