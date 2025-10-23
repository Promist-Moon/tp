package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import seedu.address.model.lesson.Day;

class DateTimeUtilTest {

    @Test
    @DisplayName("currentYearMonth matches system YearMonth (system default zone)")
    void currentYearMonth_matchesSystemNow() {
        YearMonth expected = YearMonth.from(LocalDate.now(ZoneId.systemDefault()));
        assertEquals(expected, DateTimeUtil.currentYearMonth(),
                "currentYearMonth() should equal YearMonth.from(LocalDate.now(systemDefault))");
    }

    @Test
    @DisplayName("currentDay(): DayOfWeek equals system LocalDate.now() (system default zone)")
    void currentDay_matchesSystemDayOfWeek() {
        var expectedDow = LocalDate.now(ZoneId.systemDefault()).getDayOfWeek();
        assertEquals(expectedDow, DateTimeUtil.currentDay().getDayOfWeek(),
                "currentDay().getDayOfWeek() should equal LocalDate.now(systemDefault).getDayOfWeek()");
    }

    @Test
    @DisplayName("monthsBetweenInclusive: same month -> 0")
    void monthsBetween_sameMonth_zero() {
        YearMonth m = YearMonth.of(2025, 5);
        assertEquals(0, DateTimeUtil.monthsBetweenInclusive(m, m));
    }

    @Test
    @DisplayName("monthsBetweenInclusive: increasing months within year")
    void monthsBetween_increasingWithinYear() {
        assertEquals(1, DateTimeUtil.monthsBetweenInclusive(YearMonth.of(2025, 1),
                YearMonth.of(2025, 2)));
        assertEquals(2, DateTimeUtil.monthsBetweenInclusive(YearMonth.of(2026, 2),
                YearMonth.of(2026, 4)));
        assertEquals(11, DateTimeUtil.monthsBetweenInclusive(YearMonth.of(2025, 1),
                YearMonth.of(2025, 12)));
    }

    @Test
    @DisplayName("monthsBetweenInclusive: across year boundary")
    void monthsBetween_acrossYearBoundary() {
        assertEquals(1, DateTimeUtil.monthsBetweenInclusive(YearMonth.of(2024, 12),
                YearMonth.of(2025, 1)));
        assertEquals(14, DateTimeUtil.monthsBetweenInclusive(YearMonth.of(2024, 1),
                YearMonth.of(2025, 3)));
    }

    @Test
    @DisplayName("monthsBetweenInclusive: to before from -> clamped to 0")
    void monthsBetween_toBeforeFrom_zero() {
        assertEquals(0, DateTimeUtil.monthsBetweenInclusive(YearMonth.of(2025, 5),
                YearMonth.of(2025, 4)));
        assertEquals(0, DateTimeUtil.monthsBetweenInclusive(YearMonth.of(2025, 1),
                YearMonth.of(2024, 12)));
    }

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
