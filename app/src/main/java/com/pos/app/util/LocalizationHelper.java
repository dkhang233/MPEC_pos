package com.pos.app.util;

import java.text.NumberFormat;
import java.util.Locale;

public class LocalizationHelper {
    private static final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.of("vi", "VN"));

    public static String formatCurrency(double value) {
        return currencyFormatter.format(value);
    }
}
