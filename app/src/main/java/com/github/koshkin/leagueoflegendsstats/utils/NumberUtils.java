package com.github.koshkin.leagueoflegendsstats.utils;

import java.text.DecimalFormat;

import static com.github.koshkin.leagueoflegendsstats.utils.Utils.NOT_AVAILABLE;

/**
 * Created by tehras on 1/10/16.
 */
public class NumberUtils {

    public static final String TWO_DEC = "#,###,###,##0.00";
    public static final String ONE_DEC = "#,###,###,##0.0";
    public static final String NO_DEC = "#,###,###,##0";

    public static String twoDecimals(double number) {
        DecimalFormat df2 = new DecimalFormat(TWO_DEC);

        return df2.format(number);
    }

    public static String twoDecimalsSafely(double number) {
        return decimalSafely(number, TWO_DEC);
    }

    public static String oneDecimalSafely(double number) {
        return decimalSafely(number, ONE_DEC);
    }

    public static String noDecimalSafely(double number) {
        return decimalSafely(number, NO_DEC);
    }

    public static String decimalSafely(double number, String format) {
        if (number < 0d)
            return NOT_AVAILABLE;

        return new DecimalFormat(format).format(number);
    }


}
