package utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    /**
     * Private constructor to prevent instantiation.
     */
    private DateUtils() {
    }

    public static int getWeekNumber(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }
    
    public static boolean isWithinWeekOfYear(Date date, Date within) {
        return getWeekNumber(date) == getWeekNumber(within)
                && getYear(date) == getYear(within);
    }
    
    public static boolean isWithinRange(Date date, Date start, Date end) {
        return start.compareTo(date) * date.compareTo(end) >= 0;
    }
    
    public static Date today() {
        LocalDate localDate = LocalDate.now();
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return date;
    }
    
    public static Date getTimesheetStartDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            return date;
        } else {
            return getWeekSaturday(date);
        }
    }
    
    public static Date getTimesheetEndDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            cal.add(Calendar.DAY_OF_YEAR, 6);
            return cal.getTime();
        } else {
            return getWeekFriday(date);
        }
    }
    
    @SuppressWarnings("deprecation")
    private static Date getWeekSaturday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        if (cal.getTime().getDay() != Calendar.SATURDAY) {
            cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
            cal.add(Calendar.DAY_OF_YEAR, -7);
        }

        return cal.getTime();
    }
    
    @SuppressWarnings("deprecation")
    private static Date getWeekFriday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        if (cal.getTime().getDay() != Calendar.FRIDAY) {
            cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        }

        return cal.getTime();
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
