package seedu.address.model.lesson;

import java.time.Duration;
import java.time.LocalTime;

import static seedu.address.commons.util.AppUtil.checkArgument;

public class EndTime extends Time {

    private final LocalTime start;
    private final LocalTime end;

    private EndTime(LocalTime start, String strEnd) {
        super(strEnd);
        this.start = start;
        this.end = this.getValue();
    }

    public static EndTime ofEndTime(StartTime start, String strEnd) {
        checkArgument(isValidEndTimeFormat(start, strEnd), MESSAGE_CONSTRAINTS);
        LocalTime startTime = start.getValue();
        return new EndTime(startTime, strEnd);
    }

    /**
     * Checks if the LocalTime start is before the LocalTime end
     */
    public static boolean isValidEndTime(LocalTime start, LocalTime end) {
        return start.isBefore(end);
    }

    /**
     * Checks if the StartTime start is before the LocalTime end
     */
    public static boolean isValidEndTimeFormat(StartTime start, String testEnd) {
        if (isValidTimeFormat(testEnd)) {
            LocalTime end = parseTime(testEnd);
            return isValidEndTime(start.getValue(), end);
        }
        return false;
    }

    public Duration getDuration() {
        return Duration.between(start, end);
    }
}
