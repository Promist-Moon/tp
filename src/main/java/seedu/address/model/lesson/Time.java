package seedu.address.model.lesson;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static seedu.address.commons.util.AppUtil.checkArgument;

public abstract class Time {

    public static final String MESSAGE_CONSTRAINTS = "Time must be given in the format HH:MM";

    /** Input time format for parsing user input. */
    public static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");
    public static final String VALIDATION_REGEX = "^([01]\\d|2[0-3]):([0-5]\\d)$";

    private final LocalTime value;

    protected Time(String str) {
        checkArgument(isValidTimeFormat(str), MESSAGE_CONSTRAINTS);
        LocalTime value = parseTime(str);
        this.value = value;
    }

    /**
     * Parses the given string str into a LocalTime according to TIME_FORMAT.
     */
    public static LocalTime parseTime(String str) {
        LocalTime time = LocalTime.parse(str, TIME_FORMAT);
        return time;
    }

    /**
     * Checks if the given str is a valid time in accordance to TIME_FORMAT.
     */
    public static boolean isValidTimeFormat(String test) {
        return test.matches(VALIDATION_REGEX);
        /*
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_FORMAT);
            formatter.parse(test);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }

         */
    }

    public LocalTime getValue() {
        return value;
    }

}
