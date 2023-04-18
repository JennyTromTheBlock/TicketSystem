package GUI.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

class DateConverterTest {

    @DisplayName("Combine local date and time")
    @Test
    void dateConverter() {

        int year = 1978;
        Month month = Month.DECEMBER;
        int dayOfMonth = 17;
        String stringTime = "15:33";

        LocalDate localDate = LocalDate.of(year, month, dayOfMonth);

        Calendar calendar = new GregorianCalendar(year, Calendar.DECEMBER, dayOfMonth, 15, 33);
        Date dateToMatch = calendar.getTime();
        Date date = DateConverter.dateConverter(localDate, stringTime);

        Assertions.assertEquals(dateToMatch.getTime(), date.getTime());
    }

    @Test
    void getTimeFromDate() {
        int year = 1978;
        Month month = Month.DECEMBER;
        int dayOfMonth = 17;
        String stringTimeToMatch = "15:33";

        Calendar calendar = new GregorianCalendar(year, Calendar.DECEMBER, dayOfMonth, 15, 33);
        Date dateToMatch = calendar.getTime();

        String time = DateConverter.getTimeFromDate(dateToMatch);

        Assertions.assertEquals(stringTimeToMatch, time);
    }
}