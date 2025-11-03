package tutman.tuiniverse.model.lesson;

import static tutman.tuiniverse.commons.util.AppUtil.checkArgument;

import java.time.DayOfWeek;


/**
 * Represents the day of a Lesson.
 * Guarantees: immutable; is valid as declared in {@link #isValidDay(String)}
 */
public class Day {

    public static final String MESSAGE_CONSTRAINTS = "Day is a string which can take values: "
            + "[Monday], [Tuesday], [Wednesday], [Thursday], [Friday], [Saturday], [Sunday]";
    public static final String VALIDATION_REGEX = "\\d";

    private final DayOfWeek day;

    /**
     * Constructs a {@code Day}.
     *
     * @param str A valid string representing an integer day.
     */
    public Day(String str) {
        try {
            str = str.trim().toUpperCase();
            this.day = DayOfWeek.valueOf(str);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid day format. Use day name like MONDAY.");
        }
    }

    /**
     * Creates a {@code Day} from an integer.
     *
     * @param day A valid integer corresponding to a day.
     */
    public Day(int day) {
        String str = Integer.toString(day);
        if (str.matches("\\d")) {
            checkArgument(isValidDay(str), MESSAGE_CONSTRAINTS);
            this.day = DayOfWeek.of(day);
        } else {
            throw new IllegalArgumentException("Invalid day format. Use integer 1–7.");
        }
    }



    /**
     * Checks if the given string is a valid day.
     */
    public static boolean isValidDay(String test) {
        if (test.matches(VALIDATION_REGEX)) {
            int integerDay = Integer.parseInt(test);
            return integerDay >= 1 && integerDay <= 7;
        } else {
            for (DayOfWeek day : DayOfWeek.values()) {
                if (day.name().equals(test.toUpperCase())) {
                    return true;
                }
            }
            return false;
        }
    }

    public DayOfWeek getDayOfWeek() {
        return day;
    }

    /**
     * Returns an array of the days of the week.
     * @return an array of Strings
     */
    public static Day[] values() {
        Day[] days = new Day[7];
        for (int i = 1; i <= 7; i++) {
            days[i - 1] = new Day(i);
        }
        return days;
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
