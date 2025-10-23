package seedu.address.model.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import seedu.address.model.lesson.Day;

/**
 * Contains utility methods for calculations on datetime functions.
 */
public class DateTimeUtil {

    /**
     * Returns the current {@link Day} based on the system's default time zone
     */
    public static Day currentDay() {
        DayOfWeek today = LocalDate.now(ZoneId.systemDefault()).getDayOfWeek();
        return new Day(today.toString());
    }

    /**
     * Returns the current {@code YearMonth} based on the system's default time zone.
     */
    public static YearMonth currentYearMonth() {
        return YearMonth.from(LocalDate.now(ZoneId.systemDefault()));
    }

    /**
     * Returns the number of full months between two {@code YearMonth}s.
     * Returns 0 if {@code toInclusive} is before {@code fromInclusive}.
     */
    public static long monthsBetweenInclusive(YearMonth fromInclusive, YearMonth toInclusive) {
        long m = ChronoUnit.MONTHS.between(fromInclusive, toInclusive);
        return Math.max(0, m);
    }

    /**
     * Returns the number of occurrences of a given {@code DayOfWeek} in the specified {@code YearMonth}.
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
