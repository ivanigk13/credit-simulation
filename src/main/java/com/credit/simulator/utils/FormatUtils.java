package com.credit.simulator.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class FormatUtils {
    private FormatUtils() {}

    public static String moneyFormat(BigDecimal amount){
        return new DecimalFormat("#,###.00").format(amount);
    }
}
