package seedu.address.model.util;

import java.time.LocalDate;
import java.time.YearMonth;

import seedu.address.model.lesson.Day;

/**
 * Contains utility methods for calculations on datetime functions
 */
public class DateTimeUtil {

    /**
     * Returns the number of occurrences of a given DayOfWeek in the specified YearMonth.
     */
    public static int countDaysOfWeekInMonth(YearMonth month, Day day) {
        LocalDate firstDay = month.atDay(1);
        LocalDate lastDay = month.atEndOfMonth();

        int count = 0;
        for (LocalDate date = firstDay; !date.isAfter(lastDay); date = date.plusDays(1)) {
            if (date.getDayOfWeek() == day.getDayOfWeek()) {
                count++;
            }
        }
        return count;
    }
}
