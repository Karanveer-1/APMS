package utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    /**
     * Private constructor to prevent instantiation.
     */
    private DateUtils() {
    }

    /**
     * Get the week number of the year of a date.
     * 
     * @param date
     *            date to get the week number from
     * @return week number of the year provided
     */
    public static int getWeekNumber(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * Get the year of a date.
     * 
     * @param date
     *            date to get the year from
     * @return year of the date provided
     */
    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * Checks to see if date 'a' is within the same week and year of date 'b'.
     * 
     * @param date
     *            date to check
     * @param within
     *            date used to check
     * @return true if date 'a' is within the same week and year of date 'b'
     */
    public static boolean isWithinWeekOfYear(Date date, Date within) {
        return getWeekNumber(date) == getWeekNumber(within)
                && getYear(date) == getYear(within);
    }
}
