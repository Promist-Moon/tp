package seedu.address.model.lesson;

import java.time.DayOfWeek;

import static seedu.address.commons.util.AppUtil.checkArgument;

public class Day {

    public static final String MESSAGE_CONSTRAINTS = "Day is an integer which corresponds to: "
            + "[1: Monday], [2: Tuesday], [3: Wednesday], [4: Thursday], [5: Friday], [6: Saturday], [7: Sunday]";
    public static final String VALIDATION_REGEX = "\\d";

    private final DayOfWeek day;

    public Day(String str) {
        checkArgument(isValidDay(str), MESSAGE_CONSTRAINTS);
        int integerDay = Integer.parseInt(str);
        this.day = DayOfWeek.of(integerDay);
    }

    /**
     * Checks if the given string is a valid day.
     */
    public static boolean isValidDay(String test) {
        if (test.matches(VALIDATION_REGEX)) {
            int integerDay = Integer.parseInt(test);
            return integerDay >= 1 && integerDay <= 7;
        }
        return false;
    }

    public DayOfWeek getDayOfWeek() {
        return day;
    }

    @Override
    public String toString() {
        return day.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Day)) {
            return false;
        }

        Day otherDay = (Day) other;
        return day.equals(otherDay.day);
    }

    @Override
    public int hashCode() {
        return day.hashCode();
    }

}
