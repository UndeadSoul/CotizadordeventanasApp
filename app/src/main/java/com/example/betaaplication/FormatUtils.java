package com.example.betaaplication;

import java.text.NumberFormat;
import java.util.Locale;

public class FormatUtils {

    public static String formatCurrency(String value) {
        try {
            double amount = Double.parseDouble(value);
            NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("es", "CL"));
            formatter.setMaximumFractionDigits(0); // No decimals
            return formatter.format(amount).replace(",", ".");
        } catch (NumberFormatException e) {
            return "$ 0"; // Default value in case of error
        }
    }

    public static String formatCurrency(double value) {
        try {
            NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("es", "CL"));
            formatter.setMaximumFractionDigits(0); // No decimals
            return formatter.format(value).replace(",", ".");
        } catch (Exception e) {
            return "$ 0"; // Default value in case of error
        }
    }
}
