package common.helper.java;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {
    public String getTodayWithFormat(String formatDate) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate);
            return simpleDateFormat.format(new Date());
        } catch (IllegalArgumentException e) {
            // Handle the case where the format is invalid
            System.err.println("Invalid date format: " + formatDate);
            return null; // or return a default date string
        }
    }
}