package GUI.util;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class DateConverter {
    /**
     * takes the local date and time and combines them into one date
     * @param date
     * @param time
     * @return
     */
    public static Date dateConverter(LocalDate date, String time) {
        //creates a calendar and sets the date
        Date resultDate = java.sql.Date.valueOf(date);
        Calendar timeOfDay = Calendar.getInstance();
        timeOfDay.setTime(resultDate);
        //splits the time string into hours and minutes
        String hours = time.substring(0, 2);
        String minutes = time.substring(Math.max(time.length() - 2, 0));
        //sets the time of day in the calendar and converts it to a date and returns it
        timeOfDay.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hours));
        timeOfDay.set(Calendar.MINUTE, Integer.parseInt(minutes));
        resultDate = timeOfDay.getTime();
        return resultDate;
    }

    public static String getTimeFromDate(Date date){
        Date resultDate = date;
        Calendar timeOfDay = Calendar.getInstance();
        timeOfDay.setTime(resultDate);
        //splits the time string into hours and minutes
        return timeOfDay.HOUR + ":" + timeOfDay.MINUTE;
    }
}
