package tutman.tuiniverse.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import tutman.tuiniverse.model.lesson.Day;
import tutman.tuiniverse.model.lesson.Lesson;
import tutman.tuiniverse.model.lesson.LessonTime;
import tutman.tuiniverse.model.lesson.Level;
import tutman.tuiniverse.model.lesson.Rate;
import tutman.tuiniverse.model.lesson.Subject;


/**
 * Jackson-friendly version of {@link Lesson}.
 */
public class JsonAdaptedLesson {

    private final String subject;
    private final String level;
    private final String day;
    private final String startTime;
    private final String endTime;
    private final String rate;
    private final String studentName;

    /**
     * Constructs a {@code JsonAdaptedLesson} with the given lesson details.
     */
    @JsonCreator
    public JsonAdaptedLesson(@JsonProperty("subject") String subject,
                             @JsonProperty("level") String level, @JsonProperty("day") String day,
                             @JsonProperty("startTime") String startTime, @JsonProperty("endTime") String endTime,
                             @JsonProperty("rate") String rate, @JsonProperty("studentName") String studentName) {
        this.subject = subject;
        this.level = level;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.rate = rate;
        this.studentName = studentName;
    }

    /**
     * Converts a given {@code Lesson} into this class for Jackson use.
     */
    public JsonAdaptedLesson(Lesson source) {
        subject = source.getSubject().toString();
        level = source.getLevel().toString();
        day = Integer.toString(source.getDay().getDayOfWeek().getValue());
        startTime = source.getLessonTime().getStart().toString();
        endTime = source.getLessonTime().getEnd().toString();
        rate = source.getRate().toString();
        studentName = source.getStudentName();
    }

    /**
     * Converts this Jackson-friendly adapted Lesson object into the model's {@code LessonList} object.
     *
     */
    public Lesson toModelType() {
        Lesson res = new Lesson(
                Subject.fromString(subject),
                Level.fromString(level),
                new Day(Integer.valueOf(day)),
                LessonTime.ofLessonTime(startTime, endTime),
                new Rate(rate),
                studentName);
        return res;
    }
}
