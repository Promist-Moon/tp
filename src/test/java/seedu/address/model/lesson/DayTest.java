package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.DayOfWeek;

import org.junit.jupiter.api.Test;

public class DayTest {

    @Test
    public void constructor_validDayString_success() {
        for (int i = 1; i <= 7; i++) {
            Day day = new Day(String.valueOf(i));
            assertEquals(DayOfWeek.of(i), day.getDayOfWeek());
        }
    }

    @Test
    public void constructor_invalidDayString_throwsIllegalArgumentException() {
        String[] invalidInputs = {"0", "8", "a", "", "10"};
        for (String input : invalidInputs) {
            assertThrows(IllegalArgumentException.class, ()-> new Day(input));
        }
    }

    @Test
    public void isValidDay_variousInputs() {
        for (int i = 1; i <= 7; i++) {
            assertTrue(Day.isValidDay(String.valueOf(i)));
        }
        String[] invalidInputs = {"0", "8", "x", "", "15"};
        for (String input : invalidInputs) {
            assertFalse(Day.isValidDay(input));
        }
    }

    @Test
    public void isValidDay_nullAndWhitespace() {
        assertThrows(NullPointerException.class, () -> Day.isValidDay(null));
        assertFalse(Day.isValidDay(""));
        assertFalse(Day.isValidDay(" "));
        assertFalse(Day.isValidDay(" 1 "));
        assertFalse(Day.isValidDay("01"));
    }

    @Test
    public void toString_returnsCorrectDayString() {
        Day day = new Day("3");
        assertEquals("WEDNESDAY", day.toString());
    }

    @Test
    public void validEquals_and_validHashcode() {
        Day day1 = new Day("4");
        Day day2 = new Day("4");
        Day day3 = new Day("3");

        assertEquals(day1, day1);
        assertEquals(day1, day2);
        assertNotEquals(day1, day3);
        assertNotEquals(null, day1);

        assertEquals(day1.hashCode(), day2.hashCode());
        assertNotEquals(day1.hashCode(), day3.hashCode());
    }

}
