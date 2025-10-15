package seedu.address.model.lesson.exceptions;

public class LessonNotFoundException extends RuntimeException {
    public LessonNotFoundException() {
        super("Lesson not found");
    }
}
