package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.YearMonth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import seedu.address.model.lesson.Day;

class DateTimeUtilTest {

    @Test
    @DisplayName("Non-leap February (28 days): every weekday appears exactly 4 times")
    void countDaysOfWeekInMonth_februaryNonLeap_allFour() {
        YearMonth feb2023 = YearMonth.of(2023, 2); // 2023-02 has 28 days
        for (Day d : Day.values()) {
            assertEquals(4,
                    DateTimeUtil.countDaysOfWeekInMonth(feb2023, d), () ->
                            "Expected 4 for " + d + " in " + feb2023);
        }
    }

    @Test
    @DisplayName("Leap February (29 days): exactly one weekday appears 5 times (weekday of the 1st)")
    void countDaysOfWeekInMonth_februaryLeap_oneFiveRestFour() {
        YearMonth feb2024 = YearMonth.of(2024, 2); // 2024-02-01 is a Thursday
        // Thursday should be 5; others 4
        for (Day d : Day.values()) {
            int expected = (d.getDayOfWeek() == feb2024.atDay(1).getDayOfWeek()) ? 5 : 4;
            assertEquals(expected,
                    DateTimeUtil.countDaysOfWeekInMonth(feb2024, d), () ->
                            "Unexpected count for " + d + " in " + feb2024);
        }
    }

    @Test
    @DisplayName("30-day month: exactly two consecutive weekdays appear 5 times")
    void countDaysOfWeekInMonth_thirtyDayMonth_twoFives() {
        YearMonth apr2023 = YearMonth.of(2023, 4); // 30 days; starts on Saturday (2023-04-01)
        // For a 30-day month, the weekday of the 1st and the next weekday have 5 occurrences.
        var firstDow = apr2023.atDay(1).getDayOfWeek(); // Saturday
        var secondDow = firstDow.plus(1); // Sunday

        for (Day d : Day.values()) {
            int expected = (d.getDayOfWeek() == firstDow || d.getDayOfWeek() == secondDow) ? 5 : 4;
            assertEquals(expected,
                    DateTimeUtil.countDaysOfWeekInMonth(apr2023, d), () ->
                            "Unexpected count for " + d + " in " + apr2023);
        }
    }

    @Test
    @DisplayName("31-day month: three consecutive weekdays appear 5 times")
    void countDaysOfWeekInMonth_thirtyOneDayMonth_threeFives() {
        YearMonth oct2025 = YearMonth.of(2025, 10); // 31 days
        // For a 31-day month, the weekday of the 1st and the next two weekdays have 5 occurrences.
        var firstDow = oct2025.atDay(1).getDayOfWeek();
        var secondDow = firstDow.plus(1);
        var thirdDow = firstDow.plus(2);

        for (Day d : Day.values()) {
            boolean five = d.getDayOfWeek() == firstDow
                    || d.getDayOfWeek() == secondDow
                    || d.getDayOfWeek() == thirdDow;
            int expected = five ? 5 : 4;
            assertEquals(expected,
                    DateTimeUtil.countDaysOfWeekInMonth(oct2025, d), () ->
                            "Unexpected count for " + d + " in " + oct2025);
        }
    }
}
