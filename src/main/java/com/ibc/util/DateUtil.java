package com.ibc.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Get the current date
    public static LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    // Format a LocalDate to a String
    public static String formatDate(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }

    // Parse a String to LocalDate
    public static LocalDate parseDate(String dateStr) {
        return LocalDate.parse(dateStr, DATE_FORMATTER);
    }
}
