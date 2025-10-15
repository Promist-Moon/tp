package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.lesson.*;


public class JsonAdaptedLesson {

    private final String subject;
    private final String level;
    private final String day;
    private final String startTime;
    private final String endTime;
    private final String rate;
    private final String address;

    /**
     * Constructs a {@code JsonAdaptedLesson} with the given lesson details.
     */
    @JsonCreator
    public JsonAdaptedLesson(@JsonProperty("subject") String subject,
                             @JsonProperty("level") String level, @JsonProperty("day") String day,
                             @JsonProperty("startTime") String startTime, @JsonProperty("endTime") String endTime,
                             @JsonProperty("rate") String rate, @JsonProperty("address") String address) {
        this.subject = subject;
        this.level = level;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.rate = rate;
        this.address = address;
    }

    /**
     * Converts a given {@code Lesson} into this class for Jackson use.
     */
    public JsonAdaptedLesson(Lesson source) {
        subject = source.getSubject().toString();
        level = source.getLevel().toString();
        day = source.getDay().toString();
        startTime = source.getLessonTime().getStart().toString();
        endTime = source.getLessonTime().getEnd().toString();
        rate = source.getRate().toString();
        address = source.getAddress().toString();
    }

    /**
     * Converts this Jackson-friendly adapted Lesson object into the model's {@code LessonList} object.
     *
     */
    public Lesson toModelType() {
        return new Lesson(
                Subject.fromString(subject),
                Level.fromString(level),
                new Day(day),
                LessonTime.ofLessonTime(startTime, endTime),
                new Rate(rate));
    }
}
